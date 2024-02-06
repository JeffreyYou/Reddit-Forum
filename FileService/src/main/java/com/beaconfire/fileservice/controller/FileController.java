package com.beaconfire.fileservice.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.beaconfire.fileservice.dto.response.StatusResponse;
import com.beaconfire.fileservice.security.JwtService;
import com.beaconfire.fileservice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("file-service")
@Slf4j
public class FileController {
    public FileService fileService;
    public JwtService jwtService;

    @Autowired
    public void setFileController(FileService fileService, JwtService jwtService){
        this.fileService = fileService;
        this.jwtService = jwtService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Bucket>> listBucket(){
        List<Bucket> buckets = fileService.listS3Buckets();
        return new ResponseEntity<>(buckets, HttpStatus.OK);
    }

    @GetMapping("/list/{bucketName}")
    public ResponseEntity<List<S3ObjectSummary>> listBucketObjects(@PathVariable String bucketName){
        List<S3ObjectSummary> res = fileService.listBucketObjects(bucketName);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/list/{bucketName}/public")
    public ResponseEntity<List<S3ObjectSummary>> listBucketPublicObjects(@PathVariable String bucketName){
        List<S3ObjectSummary> res = fileService.listBucketPublicObjects(bucketName);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/list/{bucketName}/private")
    public ResponseEntity<List<S3ObjectSummary>> listBucketPrivateObjects(@PathVariable String bucketName){
        List<S3ObjectSummary> res = fileService.listBucketPrivateObjects(bucketName);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{bucketName}/{directory}")
    public ResponseEntity<StatusResponse> putObject(@PathVariable String bucketName,
                                                    @PathVariable String directory,
                                                    @RequestParam(required = false) String contentType,
                                                    @RequestBody MultipartFile multipartFile,
                                                    HttpServletRequest request){
        BigInteger userId = jwtService.getUserId(request);
        String type = contentType == null ? "image/jpeg" : contentType;
        StatusResponse res = fileService.putPublicObject(bucketName, directory, userId, multipartFile, type);
        return new ResponseEntity<>(res, checkStatus(res));
    }

    @GetMapping("/{bucketName}/{directory}/{objectId}")
    public ResponseEntity<InputStreamResource> getObject(@PathVariable String bucketName,
                                                         @PathVariable String directory,
                                                         @PathVariable String objectId,
                                                         HttpServletRequest request) {
        BigInteger userId = jwtService.getUserId(request);
        if(!isOwner(userId, objectId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        String[] type = new String[1];
        HttpHeaders httpHeaders = new HttpHeaders();
        InputStreamResource inputStreamResource = new InputStreamResource(
                fileService.getObject(bucketName, directory, objectId, type)
        );
        httpHeaders.setContentType(MediaType.parseMediaType(type[0]));
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);

    }

    @DeleteMapping("/{bucketName}/{directory}/{objectId}")
    public ResponseEntity<StatusResponse> deleteObject(@PathVariable String bucketName,
                                                       @PathVariable String directory,
                                                       @PathVariable String objectId,
                                                       HttpServletRequest request){
        BigInteger userId = jwtService.getUserId(request);
        if(!isOwner(userId, objectId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        StatusResponse res = fileService.deleteObject(bucketName, directory, objectId);
        return new ResponseEntity<>(res, checkStatus(res));
    }

    @PatchMapping("/move/{bucketName}")
    public ResponseEntity<StatusResponse> moveObject(@PathVariable String bucketName,
                                                     @RequestParam String oldKey,
                                                     @RequestParam String newKey,
                                                     HttpServletRequest request){
        BigInteger userId = jwtService.getUserId(request);
        if(!isOwner(userId, oldKey.split("/")[1])) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        StatusResponse res = fileService.moveObject(bucketName, oldKey, newKey);
        return new ResponseEntity<>(res, checkStatus(res));
    }

    @PatchMapping("/make-public/{bucketName}/private/{objectId}")
    public ResponseEntity<StatusResponse> makeObjectPublic(@PathVariable String bucketName,
                                                           @PathVariable String objectId,
                                                           HttpServletRequest request){
        BigInteger userId = jwtService.getUserId(request);
        if(!isOwner(userId, objectId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        StatusResponse res = fileService.makeObjectPublic(bucketName, objectId);
        return new ResponseEntity<>(res, checkStatus(res));
    }

    @PatchMapping("/make-private/{bucketName}/public/{objectId}")
    public ResponseEntity<StatusResponse> makeObjectPrivate(@PathVariable String bucketName,
                                                            @PathVariable String objectId,
                                                            HttpServletRequest request){
        BigInteger userId = jwtService.getUserId(request);
        if(!isOwner(userId, objectId)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        StatusResponse res = fileService.makeObjectPrivate(bucketName, objectId);
        return new ResponseEntity<>(res, checkStatus(res));
    }

    private HttpStatus checkStatus(StatusResponse response){
        if(response.getStatus().equals("success")){
            return HttpStatus.OK;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private boolean isOwner(BigInteger userId, String objectId) {
        return userId.toString().equals(objectId.split("-")[0]);
    }
}
