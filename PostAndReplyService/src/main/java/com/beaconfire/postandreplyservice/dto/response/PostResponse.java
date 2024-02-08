package com.beaconfire.postandreplyservice.dto.response;

import com.beaconfire.postandreplyservice.domain.Reply;
import com.beaconfire.postandreplyservice.dto.common.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse {
    private ResponseStatus responseStatus;
    private String postId;
    private Long userId;
    private String title;
    private Date dateCreated;

    private String content;
    private Boolean isArchived;
    private String status;
    private Date dateModified;
    private List<String> images;
    private List<String> attachments;
    private List<Reply> postReplies;
}
