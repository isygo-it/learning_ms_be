package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.ObjectStorageControllerApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.BucketDto;
import eu.novobit.dto.data.FileTagsDto;
import eu.novobit.dto.exception.MinIoObjectException;
import eu.novobit.enumerations.IEnumLogicalOpe;
import eu.novobit.exception.handler.SmsExceptionHandler;
import eu.novobit.factory.StorageFactoryService;
import eu.novobit.model.FileStorage;
import eu.novobit.model.StorageConfig;
import eu.novobit.service.IStorageConfigService;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Object storage controller.
 */
@Slf4j
@RestController
@CtrlHandler(SmsExceptionHandler.class)
@RequestMapping(path = "/api/private/storage")
public class ObjectStorageController extends AbstractController implements ObjectStorageControllerApi {

    @Autowired
    private StorageFactoryService storageFactoryService;

    @Autowired
    private IStorageConfigService storageConfigService;

    @Override
    public ResponseEntity<Object> upload(RequestContextDto requestContext,
                                         String domain,
                                         String bucketName,
                                         String path,
                                         String fileName,
                                         List<String> tags,
                                         MultipartFile file) {
        try {
            Map<String, String> tagMap = null;
            if (!CollectionUtils.isEmpty(tags)) {
                tagMap = tags.stream().distinct().collect(Collectors.toMap(s -> s, s -> s));
            }
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            storageFactoryService.getService(config.getType()).upload(config,
                    bucketName.toLowerCase(),
                    path.replace("#", "/").toLowerCase(),
                    tagMap,
                    file);
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public Resource download(RequestContextDto requestContext,
                             String domain,
                             String bucketName,
                             String path,
                             String fileName,
                             String versionID) {
        try {
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            byte[] data = storageFactoryService
                    .getService(config.getType())
                    .download(config,
                            bucketName.toLowerCase(),
                            path.replace("#", "/").toLowerCase() + "/" + fileName,
                            versionID);
            ByteArrayResource resource = new ByteArrayResource(data);
            return resource;
        } catch (Throwable e) {

            throw new MinIoObjectException(e);
        }
    }

    @Override
    public ResponseEntity<Object> delete(RequestContextDto requestContext,
                                         String domain,
                                         String bucketName,
                                         String path,
                                         String fileName) {
        log.info("delete request received");
        try {
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            storageFactoryService.getService(config.getType())
                    .deleteFile(config,
                            bucketName.toLowerCase(),
                            path.replace("#", "/").toLowerCase() + "/" + fileName
                    );
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Object> getObjects(RequestContextDto requestContext,
                                             String domain, String bucketName) {
        log.info("get objects request received");
        try {
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            return ResponseFactory.ResponseOk(storageFactoryService.getService(config.getType())
                    .getObjects(config,
                            bucketName.toLowerCase()));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Object> filterObjects(RequestContextDto requestContext,
                                                String domain,
                                                String bucketName,
                                                String tags,
                                                IEnumLogicalOpe.Types condition) {
        log.info("getObjectByTags request received");
        try {
            Map<String, String> tagMap = null;
            List<String> tagList = Arrays.stream(tags.split("[,:]")).toList();
            if (!CollectionUtils.isEmpty(tagList)) {
                tagMap = tagList.stream().distinct()
                        .collect(Collectors.toMap(s -> s, s -> s));
            }
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            List<FileStorage> results = storageFactoryService
                    .getService(config.getType())
                    .getObjectByTags(config,
                            bucketName.toLowerCase(),
                            tagMap,
                            condition);

            return ResponseFactory.ResponseOk(results);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Object> updateTags(//RequestContextDto requestContext,
                                             FileTagsDto fileTags) {
        log.info("updateTags request received");
        try {
            Map<String, String> tagMap = null;
            if (!CollectionUtils.isEmpty(fileTags.getTags())) {
                tagMap = fileTags.getTags().stream().distinct()
                        .collect(Collectors.toMap(s -> s, s -> s));
            }
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(fileTags.getDomain());
            storageFactoryService.getService(config.getType())
                    .updateTags(config, fileTags.getBucketName(), fileTags.getFiletName(), tagMap);

            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Object> deleteObjects(RequestContextDto requestContext,
                                                String domain,
                                                String bucketName,
                                                String files) {
        log.info("deleteObjects request received");
        try {
            List<DeleteObject> objectsMap = new LinkedList<>();
            List<String> objectsList = Arrays.stream(files.split("[,:]")).toList();
            if (!CollectionUtils.isEmpty(objectsList)) {
                objectsList.forEach(d -> {
                    objectsMap.add(new DeleteObject(d));
                });
            }
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            storageFactoryService.getService(config.getType()).deleteObjects(config,
                    bucketName.toLowerCase(),
                    objectsMap);
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Object> saveBucket(RequestContextDto requestContext,
                                             String domain,
                                             String bucketName) {
        log.info("getBucketList request received");
        try {
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            storageFactoryService.getService(config.getType()).saveBuckets(config,
                    bucketName.toLowerCase());
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Object> setVersioningBucket(RequestContextDto requestContext,
                                                      String domain,
                                                      String bucketName,
                                                      boolean status) {
        log.info("getBucketList request received");
        try {
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            storageFactoryService.getService(config.getType()).setVersioningBucket(config,
                    bucketName.toLowerCase(),
                    status);
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<BucketDto>> getBuckets(RequestContextDto requestContext,
                                                      String domain) {
        log.info("getBucketList request received");
        try {
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            return ResponseFactory.ResponseOk(storageFactoryService.getService(config.getType()).getBuckets(config));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Object> deleteBucket(RequestContextDto requestContext,
                                               String domain,
                                               String bucketName) {
        log.info("deleteBucket request received");
        try {
            StorageConfig config = storageConfigService.findByDomainIgnoreCase(domain);
            storageFactoryService.getService(config.getType()).deletebucket(config,
                    bucketName.toLowerCase());
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
