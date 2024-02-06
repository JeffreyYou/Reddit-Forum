package com.beaconfire.postandreplyservice.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private Optional<String> postId;

    @NotNull(message = "Post title cannot be null")
    private String title;

    // @NotNull(message = "Post content cannot be null")
    private String content;

    private ArrayList<String> images;

    private ArrayList<String> attachments;
}
