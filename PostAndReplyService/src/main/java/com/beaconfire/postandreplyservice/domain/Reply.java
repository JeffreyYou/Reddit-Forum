package com.beaconfire.postandreplyservice.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reply {

    private String replyId;

    private Long userId;

    private String postId;

    private String comment;

    private Boolean isActive;

    private Date dateCreated;

    private List<SubReply> subReplies;
}
