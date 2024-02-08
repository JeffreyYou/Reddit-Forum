package com.beaconfire.postandreplyservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Document(collection = "Post")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String postId;

    private Long userId;

    private String title;

    private String content;

    private Boolean isArchived;

    private String status;

    private Date dateCreated;

    private Date dateModified;

    private List<String> images;

    private List<String> attachments;

    private List<Reply> postReplies;
}
