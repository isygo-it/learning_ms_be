package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.LinkedFileApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.LinkedFileDto;
import eu.novobit.dto.LinkedFileResponseDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.exception.handler.DmsExceptionHandler;
import eu.novobit.mapper.LinkedFileMapper;
import eu.novobit.service.ILinkedFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The type Linked file controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(DmsExceptionHandler.class)
@RequestMapping(path = "/api/private/dms/document")
public class LinkedFileController extends AbstractController implements LinkedFileApi {

    @Autowired
    private ILinkedFileService linkedFileService;
    @Autowired
    private LinkedFileMapper linkedFileMapper;

    @Override
    public ResponseEntity<List<LinkedFileDto>> searchByTags(RequestContextDto requestContext,
                                                            String domain, String tags) {
        log.info("Search file by tags from domain {} : {}", domain, tags);
        try {
            return ResponseFactory.ResponseOk(linkedFileMapper.listEntityToDto(linkedFileService.searchByTags(domain, tags)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteFile(RequestContextDto requestContext,
                                              String domain,
                                              String code) {
        log.info("Delete file by from domain {} : {}", domain, code);
        try {
            linkedFileService.deleteFile(domain, code);
            return ResponseFactory.ResponseOk(true);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<LinkedFileDto> searchByOriginalName(RequestContextDto requestContext,
                                                              String domain,
                                                              String originalName) {
        log.info("Search file by original name from domain {} : {}", domain, originalName);
        try {
            return ResponseFactory.ResponseOk(linkedFileMapper.entityToDto(linkedFileService.searchByOriginalName(domain, originalName)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<LinkedFileDto> renameFile(RequestContextDto requestContext,
                                                    String domain,
                                                    String oldCode,
                                                    String newCode) {
        log.info("rename file of domain {} : from {} to {}", oldCode, newCode);
        try {
            return ResponseFactory.ResponseOk(linkedFileMapper.entityToDto(linkedFileService.renameFile(domain, oldCode, newCode)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<LinkedFileDto>> searchByCategories(RequestContextDto requestContext,
                                                                  String domain,
                                                                  String categories) {
        log.info("Search files by categories from domain {} : {}", domain, categories);
        try {
            String[] catArray = categories.split(",");
            if (catArray.length > 0) {
                // List<String> catList = Arrays.stream(catArray).toList();
                List<LinkedFileDto> list = linkedFileMapper.listEntityToDto(linkedFileService.searchByCategories(domain, Arrays.stream(categories.split(",")).toList()));
                if (CollectionUtils.isEmpty(list)) {
                    return ResponseFactory.ResponseNoContent();
                }
                return ResponseFactory.ResponseOk(list);
            }
            return ResponseFactory.ResponseBadRequest();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<LinkedFileResponseDto> upload(//RequestContextDto requestContext,
                                                        LinkedFileDto linkedFile) throws IOException {
        log.info("Uploading file from domain {} : {}", linkedFile.getDomain(), linkedFile.getFile().getOriginalFilename());
        if (linkedFile.getFile() == null) {
            return ResponseFactory.ResponseBadRequest();
        }
        try {
            return ResponseFactory.ResponseOk(LinkedFileResponseDto.builder()
                    .code(linkedFileService.upload(linkedFile, linkedFile.getFile()))
                    .build());
        } catch (Throwable e) {
            log.info(e.getMessage());
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Resource> download(RequestContextDto requestContext,
                                             String domain,
                                             String originalFileName,
                                             Long version) throws IOException {
        log.info("Downloading file from domain {} : {} ver({})", domain, originalFileName, version);
        try {
            Resource resource = linkedFileService.download(originalFileName, domain, version);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "multipart/form-data")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<String> getDirectoryTree(RequestContextDto requestContext) {
        return ResponseFactory.ResponseOk(linkedFileService.directoryTree());
    }
}



