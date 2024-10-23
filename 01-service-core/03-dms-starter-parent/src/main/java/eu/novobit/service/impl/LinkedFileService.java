package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.config.AppProperties;
import eu.novobit.dto.LinkedFileDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.MenuDto;
import eu.novobit.dto.data.MenuTreeDto;
import eu.novobit.encrypt.helper.CRC16;
import eu.novobit.encrypt.helper.CRC32;
import eu.novobit.exception.FileAlreadyExistsException;
import eu.novobit.exception.FileNotFoundException;
import eu.novobit.exception.ResourceNotFoundException;
import eu.novobit.helper.FileHelper;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Category;
import eu.novobit.model.LinkedFile;
import eu.novobit.model.extendable.NextCodeModel;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.remote.sms.SmsStorageLinkedFileService;
import eu.novobit.repository.CategoryRepository;
import eu.novobit.repository.LinkedFileRepository;
import eu.novobit.service.ILinkedFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Linked file service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = LinkedFileRepository.class)
public class LinkedFileService extends AbstractCrudCodifiableService<LinkedFile, LinkedFileRepository> implements ILinkedFileService {

    private final AppProperties appProperties;

    @Autowired
    private LinkedFileRepository linkedFileRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SmsStorageLinkedFileService smsStorageLinkedFileService;

    /**
     * Instantiates a new Linked file service.
     *
     * @param appProperties the app properties
     */
    public LinkedFileService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    private void processChild(MenuDto menu, JSONObject childsId) {
        // log.info(menu.getPath());
        menu.getChildren().forEach(menu1 -> {
            List<String> childId = new ArrayList<>();
            if (menu1.getChildren().size() > 0) {
                menu1.getChildren().forEach(child -> {
                    childId.add(child.getPath());
                });
                childsId.put(menu1.getPath(),
                        new JSONObject() {{
                            put("id", menu1.getPath());
                            put("name", menu1.getPath());
                            put("isDir", true);
                            put("childrenIds", childId);
                            put("childrenCount", childId.size());
                            put("parentId", menu.getPath());
                        }}
                );
            } else {

                Optional<LinkedFile> linkedFileByDomain = linkedFileRepository.findByDomainIgnoreCaseAndOriginalFileName(menu.getPath(), menu1.getPath());
                LinkedFile linkedFile = null;
                if (linkedFileByDomain.isPresent()) {
                    linkedFile = linkedFileByDomain.get();
                } else {
                    Optional<LinkedFile> linkedFileByPath = linkedFileRepository.findByPathAndOriginalFileName(menu.getPath(), menu1.getPath());
                    if (linkedFileByPath.isPresent()) {
                        linkedFile = linkedFileByPath.get();
                    }
                }
                if (linkedFile != null) {
                    LinkedFile finalLinkedFile = linkedFile;
                    childsId.put(menu1.getPath(), new JSONObject() {{
                                put("id", menu1.getPath());
                                put("modDate", finalLinkedFile.getCreateDate());
                                put("name", menu1.getPath());
                                put("size", finalLinkedFile.getSize());
                                put("parentId", menu.getPath());
                            }}
                    );
                }

            }

            processChild(menu1, childsId);
        });
    }

    @Override
    public String upload(LinkedFileDto linkedFileDto, MultipartFile file) throws IOException {
        byte[] buffer = file.getInputStream().readAllBytes();
        int crc16 = CRC16.calculate(buffer);
        int crc32 = CRC32.calculate(buffer);

        List<LinkedFile> linkedFileList = linkedFileRepository.findByDomainIgnoreCaseAndOriginalFileNameAndCheckCancelFalse(linkedFileDto.getDomain(), file.getOriginalFilename());
        if (!CollectionUtils.isEmpty(linkedFileList)) {  //if file not exists
            LinkedFile linkedFile = linkedFileList.get(linkedFileList.size() - 1);
            if (crc32 == linkedFile.getCrc32() && crc16 == linkedFile.getCrc16()) {
                throw new FileAlreadyExistsException(file.getOriginalFilename());
            }
        }

        //Collect and create categories
        final List<Category> categories = new ArrayList<>();
        if (!CollectionUtils.isEmpty(linkedFileDto.getCategoryNames())) {
            linkedFileDto.getCategoryNames().forEach(category -> {
                Optional<Category> optional = categoryRepository.findByName(category);
                if (!optional.isPresent()) {
                    categories.add(categoryRepository.save(Category.builder()
                            .name(category)
                            .description(category)
                            .build()));
                } else {
                    categories.add(optional.get());
                }
            });
        }

        String code = null;
        List<LinkedFile> optionalLinkedFile = linkedFileRepository.findByDomainIgnoreCaseAndOriginalFileNameAndCheckCancelFalse(linkedFileDto.getDomain(), file.getOriginalFilename());
        if (CollectionUtils.isEmpty(optionalLinkedFile)) {
            code = this.getNextCode();
            if (appProperties.getLocalStorageActive()) {
                log.info("appProperties.getLocalStorageActive()");
                copyFile(linkedFileDto, file, code);
            } else {
                log.info("Storage in minio enabled file:{} , tags:{}", file.getOriginalFilename(), linkedFileDto.getTags());
                ResponseEntity<Object> result = smsStorageLinkedFileService.upload(
                        RequestContextDto.builder().build(),
                        linkedFileDto.getDomain(),
                        linkedFileDto.getDomain(),
                        linkedFileDto.getPath().replace(File.separator, "#"),
                        file.getOriginalFilename(),
                        linkedFileDto.getTags(),
                        file
                );
                if (result.getStatusCode().is2xxSuccessful()) {
                    log.info("File uploaded successfully {}", file.getOriginalFilename());
                }
            }

            linkedFileRepository.save(LinkedFile.builder()
                    .code(code)
                    .originalFileName(file.getOriginalFilename())
                    .extension(FilenameUtils.getExtension(file.getOriginalFilename()))
                    .domain(linkedFileDto.getDomain())
                    .tags(linkedFileDto.getTags())
                    .crc16(crc16)
                    .crc32(crc32)
                    .size(file.getSize())
                    .categories(categories)
                    .mimetype(file.getContentType())
                    .path(linkedFileDto.getPath())
                    .version(1L)
                    .build());
            return code;
        } else {
            LinkedFile linkedFile = optionalLinkedFile.get(optionalLinkedFile.size() - 1);
            if (crc32 == linkedFile.getCrc32() && crc16 == linkedFile.getCrc16()) {
                throw new FileAlreadyExistsException(file.getOriginalFilename());
            }
            code = this.getNextCode();
            if (appProperties.getLocalStorageActive()) {
                copyFile(linkedFileDto, file, code);
            } else {
                log.info("Storage in minio enabled file:{} , tags:{}", file.getOriginalFilename(), linkedFileDto.getTags());
                ResponseEntity<Object> result = smsStorageLinkedFileService.upload(
                        RequestContextDto.builder().build(),
                        linkedFileDto.getDomain(),
                        linkedFileDto.getDomain(),
                        linkedFileDto.getPath().replace(File.separator, "#"),
                        file.getOriginalFilename(),
                        linkedFileDto.getTags(),
                        file
                );
                if (result.getStatusCode().is2xxSuccessful()) {
                    log.info("File uploaded successfully {}", linkedFileDto.getFile().getOriginalFilename());
                }
            }

            linkedFileRepository.save(LinkedFile.builder()
                    .code(code)
                    .originalFileName(file.getOriginalFilename())
                    .extension(FilenameUtils.getExtension(file.getOriginalFilename()))
                    .domain(linkedFileDto.getDomain())
                    .tags(linkedFileDto.getTags())
                    .crc16(crc16)
                    .crc32(crc32)
                    .size(file.getSize())
                    .path(linkedFileDto.getPath())
                    .categories(categories)
                    .mimetype(file.getContentType())
                    .version(linkedFile.getVersion() + 1)
                    .build());
        }
        return code;
    }

    private void copyFile(LinkedFileDto linkedFile, MultipartFile file, String code) throws IOException {
        FileHelper.storeMultipartFile(appProperties.getUploadDirectory() + File.separator + linkedFile.getDomain() + File.separator + linkedFile.getPath(), code, file, "");
    }

    @Override
    public List<LinkedFile> searchByTags(String domain, String tags) throws IOException {
        return linkedFileRepository.findByDomainIgnoreCaseAndTagsContainingAndCheckCancelFalse(domain, tags);
    }

    @Override
    public void deleteFile(String domain, String code) throws IOException {
        Optional<LinkedFile> optional = linkedFileRepository.findByDomainIgnoreCaseAndCodeIgnoreCase(domain, code);
        if (optional.isPresent()) {
            this.delete(optional.get().getId());
        } else {
            throw new FileNotFoundException("File not found with");
        }
    }

    @Override
    public LinkedFile searchByOriginalName(String domain, String originalName) throws IOException {
        List<LinkedFile> linkedFile = linkedFileRepository.findByDomainIgnoreCaseAndOriginalFileNameAndCheckCancelFalse(domain, originalName);
        if (!CollectionUtils.isEmpty(linkedFile)) {
            LinkedFile linkedFile1 = linkedFile.get(0);
            return linkedFile1;
        } else {
            throw new FileNotFoundException("File not found with");
        }
    }

    @Override
    public LinkedFile renameFile(String domain, String old_originalName, String new_originalName) throws IOException {
        List<LinkedFile> linkedFile = linkedFileRepository.findByDomainIgnoreCaseAndOriginalFileNameAndCheckCancelFalse(domain, old_originalName);
        if (!CollectionUtils.isEmpty(linkedFile)) {
            LinkedFile linkedFile1 = linkedFile.get(linkedFile.size() - 1);
            linkedFile1.setOriginalFileName(new_originalName);
            return linkedFileRepository.save(linkedFile1);
        } else {
            throw new FileNotFoundException("File not found with");
        }
    }

    @Override
    public List<LinkedFile> searchByCategories(String domain, List<String> categories) throws IOException {
        return linkedFileRepository.findByDomainIgnoreCaseAndCategoriesInAndCheckCancelFalse(domain, categories);
    }

    @Override
    public Resource download(String filename, String domain, Long version) throws IOException {
        Optional<LinkedFile> linkedFile = linkedFileRepository.findByDomainIgnoreCaseAndOriginalFileNameAndCheckCancelFalseAndVersion(domain, filename, version);
        log.info(linkedFile.get().getPath());

        if (!linkedFile.isPresent()) {
            throw new RuntimeException("Could not read the file!");
        } else {
            if (appProperties.getLocalStorageActive()) {
                if (StringUtils.hasText(linkedFile.get().getPath())) {
                    Resource resource = new UrlResource(Path.of(appProperties.getUploadDirectory() + File.separator + domain + linkedFile.get().getPath() + File.separator
                            + linkedFile.get().getCode() + '.' + linkedFile.get().getExtension()).toUri());
                    if (!resource.exists()) {
                        throw new ResourceNotFoundException("for file " + domain + "/" + filename);
                    }
                    return resource;
                } else {
                    Resource resource = new UrlResource(Path.of(appProperties.getUploadDirectory() + File.separator + domain + File.separator
                            + linkedFile.get().getCode() + '.' + linkedFile.get().getExtension()).toUri());
                    if (!resource.exists()) {
                        throw new ResourceNotFoundException("for file " + domain + "/" + filename);
                    }
                    return resource;
                }
            } else {
                log.info("Storage in minio enabled domain:{}, file:{} - {}", domain, filename, version);
                Resource resource = smsStorageLinkedFileService.download(
                        RequestContextDto.builder().build(),
                        linkedFile.get().getDomain(),
                        linkedFile.get().getDomain(),
                        linkedFile.get().getPath().replace(File.separator, "#"),
                        linkedFile.get().getOriginalFileName(),
                        "");
                log.info("response " + resource);
                return resource;
            }
        }
    }

    @Override
    public List<LinkedFile> getAll() {
        return linkedFileRepository.findAll();
    }

    @Override
    public String directoryTree() {
        //object json final
        JSONObject result = new JSONObject();
        //object json for parent
        JSONObject racine = new JSONObject();
        //object json for children's
        JSONObject childsId = new JSONObject();
        List<LinkedFile> fileList = linkedFileRepository.findByCheckCancelFalse();
        if (!CollectionUtils.isEmpty(fileList)) {
            List<String> paths = new ArrayList<>();
            fileList.stream().forEach(linkedFile -> {
                String path = "uploads" + File.separator + linkedFile.getDomain() + File.separator + linkedFile.getPath() + File.separator + linkedFile.getOriginalFileName();
                paths.add(path);
            });
            MenuTreeDto t = new MenuTreeDto();
            for (String s : paths) {
                t.add(s);
            }
            processChild(t.getRoot(), childsId);
            t.getRoot().getChildren().forEach(menu -> {
                result.put("rootFolderId", menu.getPath());
                processChild(menu, childsId);
            });
        }
        result.put("fileMap", childsId);
        return result.toString();
    }

    @Override
    public LinkedFile getLinkedFileDetails(String path, String domain) {
        return null;
    }

    private void copyFile(String code, LinkedFileDto linkedFile, MultipartFile file) throws IOException {
        Path filePath = Path.of(appProperties.getUploadDirectory() + File.separator + linkedFile.getDomain() + File.separator + linkedFile.getPath());
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }

        Files.copy(file.getInputStream(), filePath.resolve(code + "." + FilenameUtils.getExtension(file.getOriginalFilename())));
    }

    @Override
    public NextCodeModel initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(LinkedFile.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RLFILE")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}
