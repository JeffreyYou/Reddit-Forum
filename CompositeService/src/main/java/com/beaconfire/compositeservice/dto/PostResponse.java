package com.beaconfire.compositeservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostResponse {

    String username;
    String title;
    String  content;
    String status;
    Date dateCreated;
    Date dateModified;
    List<String> images;
    List<String> attachments;
}
