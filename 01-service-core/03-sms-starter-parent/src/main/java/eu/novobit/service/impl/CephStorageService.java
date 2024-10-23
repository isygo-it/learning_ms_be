package eu.novobit.service.impl;

import eu.novobit.dto.data.BucketDto;
import eu.novobit.dto.exception.MinIoObjectException;
import eu.novobit.enumerations.IEnumLogicalOpe;
import eu.novobit.model.FileStorage;
import eu.novobit.model.StorageConfig;
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
 * The type Ceph storage service.
 */
@Slf4j
@Service("CephStorageService")
@Transactional
public class CephStorageService implements IObjectStorageService {

    @Autowired
    private CephApiService cephApiService;

    @Override
    public void upload(StorageConfig config, String bucketName, String path, Map<String, String> tags, MultipartFile multipartFile) {
        try {

            cephApiService.uploadFile(config, bucketName, multipartFile.getOriginalFilename(), multipartFile, tags);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public List<FileStorage> getObjects(StorageConfig config, String bucketName) {
        try {
            return cephApiService.getObjects(config, bucketName);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public byte[] download(StorageConfig config, String bucketName, String fileName, String versionID) {
        try {
            return cephApiService.getObject(config, bucketName, fileName);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void deleteFile(StorageConfig config, String bucketName, String fileName) {
        try {
            cephApiService.deleteObject(config, bucketName, fileName);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void updateTags(StorageConfig config, String bucketName, String objectName, Map<String, String> tags) {
        try {
            cephApiService.updateTags(config, bucketName, objectName, tags);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public List<FileStorage> getObjectByTags(StorageConfig config, String bucketName, Map<String, String> tags, IEnumLogicalOpe.Types condition) {
        try {
            return cephApiService.getObjectByTags(config, bucketName, tags, condition);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void deleteObjects(StorageConfig config, String bucketName, List<DeleteObject> objects) {
        try {
            cephApiService.deleteObjects(config, bucketName, objects);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void saveBuckets(StorageConfig config, String bucketName) {
        try {
            cephApiService.makeBucket(config, bucketName);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void setVersioningBucket(StorageConfig config, String bucketName, boolean status) {
        try {
            cephApiService.makeBucket(config, bucketName);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public void deletebucket(StorageConfig config, String bucketName) {
        try {
            cephApiService.deletebucket(config, bucketName);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }

    @Override
    public List<BucketDto> getBuckets(StorageConfig config) {
        try {
            return cephApiService.getBuckets(config);
        } catch (Exception e) {
            throw new MinIoObjectException(e);
        }
    }
}
