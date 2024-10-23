package eu.novobit.service.impl;

import eu.novobit.dto.data.BucketDto;
import eu.novobit.dto.exception.MinIoObjectException;
import eu.novobit.enumerations.IEnumLogicalOpe;
import eu.novobit.model.FileStorage;
import eu.novobit.model.StorageConfig;
import eu.novobit.service.IMinIOApiService;
import eu.novobit.service.IObjectStorageService;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * The type Min io storage service.
 */
@Slf4j
@Service("MinIOStorageService")
@Transactional
public class MinIOStorageService implements IObjectStorageService {

    @Autowired
    private IMinIOApiService minioService;

    @Override
    public void upload(StorageConfig config, String bucketName, String path, Map<String, String> tags, MultipartFile multipartFile) {
        try {
            log.info(config.toString());
            minioService.uploadFile(config, bucketName.toLowerCase(), path, multipartFile.getOriginalFilename(), multipartFile, tags);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public byte[] download(StorageConfig config, String bucketName, String fileName, String versionID) {
        try {
            return minioService.getObject(config, bucketName.toLowerCase(), fileName, versionID);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void deleteFile(StorageConfig config, String bucketName, String fileName) {
        try {
            minioService.deleteObject(config, bucketName.toLowerCase(), fileName);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void updateTags(StorageConfig config, String bucketName, String objectName, Map<String, String> tags) {
        try {
            minioService.updateTags(config, bucketName.toLowerCase(), objectName, tags);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public List<FileStorage> getObjectByTags(StorageConfig config, String bucketName, Map<String, String> tags, IEnumLogicalOpe.Types condition) {
        try {
            return minioService.getObjectByTags(config, bucketName.toLowerCase(), tags, condition);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public List<FileStorage> getObjects(StorageConfig config, String bucketName) {
        try {
            return minioService.getObjects(config, bucketName.toLowerCase());
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void deleteObjects(StorageConfig config, String bucketName, List<DeleteObject> objects) {
        try {
            minioService.deleteObjects(config, bucketName.toLowerCase(), objects);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void saveBuckets(StorageConfig config, String bucketName) {
        try {
            minioService.makeBucket(config, bucketName.toLowerCase());
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void setVersioningBucket(StorageConfig config, String bucketName, boolean status) {
        try {
            minioService.setVersioningBucket(config, bucketName.toLowerCase(), status);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void deletebucket(StorageConfig config, String bucketName) {
        try {
            minioService.deletebucket(config, bucketName.toLowerCase());
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public List<BucketDto> getBuckets(StorageConfig config) {
        try {
            return minioService.getBuckets(config);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }
}
