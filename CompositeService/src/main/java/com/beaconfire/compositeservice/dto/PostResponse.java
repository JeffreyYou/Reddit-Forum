package com.beaconfire.compositeservice.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostResponse {


    String postId;
    Long userid;
    String title;
    String  content;
    String status;
    Date dateCreated;
    Boolean isArchived;
    Date dateModified;
    List<String> images;
    List<String> attachments;
}
