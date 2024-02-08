package com.beaconfire.postandreplyservice.domain;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubReply {

    private String replyId;

    private Long userId;

    private String postId;

    private String comment;

    private Boolean isActive;

    private Date dateCreated;
}
