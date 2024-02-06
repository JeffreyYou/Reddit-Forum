package com.beaconfire.fileservice.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    public AWSCredentials credentials(){
        return new BasicAWSCredentials(
                "AKIA47CRWQDK3UK6T35C",
                "Be+AkQLKhrHozkiDAt77IvUF4B1uBGLxUrHOOKOb"
        );
    }

    @Bean
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}
