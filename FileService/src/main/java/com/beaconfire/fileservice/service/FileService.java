package com.beaconfire.fileservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.beaconfire.fileservice.dto.response.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    AmazonS3 amazonS3Client;

    @Autowired
    public void setFileService(AmazonS3 amazonS3Client){
        this.amazonS3Client = amazonS3Client;
    }

    public List<Bucket> listS3Buckets(){
        log.info(amazonS3Client.listBuckets().toString());
        return amazonS3Client.listBuckets();
    }

    public List<S3ObjectSummary> listBucketObjects(String bucketName){
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries();
    }

    public List<S3ObjectSummary> listBucketPublicObjects(String bucketName){
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName, "public");
        return objectListing.getObjectSummaries();
    }

    public List<S3ObjectSummary> listBucketPrivateObjects(String bucketName){
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName, "private");
        return objectListing.getObjectSummaries();
    }

    public StatusResponse putPublicObject(String bucketName, String directory, BigInteger userId,
                                          MultipartFile multipartFile, String contentType){
        String key = directory + "/" + objectIdGen(userId, toSuffix(contentType));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        try{
            PutObjectRequest request = new PutObjectRequest(
                    bucketName,
                    key,
                    multipartFile.getInputStream(),
                    metadata
            );
            amazonS3Client.putObject(request);
        }catch (Exception e){
            log.info("put object filed: " + e);
        }
        return StatusResponse.builder()
                .status("success")
                .message(null)
                .key(key)
                .url("https://reddit-forum-s3.s3.amazonaws.com/" + key)
                .build();
    }

    public S3ObjectInputStream getObject(String bucketName, String directory, String objectId,
                                         String[] contentType) {

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, directory + "/" + objectId);
        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
        contentType[0] = s3Object.getObjectMetadata().getContentType();
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        log.info("object type:" + contentType);
        return inputStream;
    }

    public void deleteMultipleObjects(String bucketName, List<String> keys){
        DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName)
                .withKeys(keys.toArray(new String[0]));
        amazonS3Client.deleteObjects(request);
    }

    public StatusResponse deleteObject(String bucketName, String directory, String objectName) {
        List<String> keys = new ArrayList<>();
        keys.add(directory + "/" + objectName);
        deleteMultipleObjects(bucketName, keys);
        return StatusResponse.builder()
                .status("success")
                .message(null)
                .build();
    }

    public StatusResponse moveObject(String bucketName, String oldKey, String newKey){
        try{
            amazonS3Client.copyObject(
                    bucketName,
                    oldKey,
                    bucketName,
                    newKey
            );
            amazonS3Client.deleteObject(bucketName, oldKey);
        } catch (Exception e){
            return StatusResponse.builder()
                    .status("failed")
                    .message("move key failed")
                    .key(oldKey)
                    .url("https://reddit-forum-s3.s3.amazonaws.com/" + oldKey)
                    .build();
        }

        return StatusResponse.builder()
                .status("success")
                .message(null)
                .key(newKey)
                .url("https://reddit-forum-s3.s3.amazonaws.com/" + newKey)
                .build();
    }

    public StatusResponse makeObjectPublic(String bucketName, String key) {
        return moveObject(bucketName, "private/" + key, "public/" + key);
    }

    public StatusResponse makeObjectPrivate(String bucketName, String key) {
        return moveObject(bucketName, "public/" + key, "private/" + key);
    }

    private String objectIdGen(BigInteger userId, String suffix){
        return userId + "-" + UUID.randomUUID() + "." + suffix;
    }

    private String toSuffix(String contentType){
        switch (contentType){
            default: return "jpg";
        }
    }
}

