package com.beaconfire.postandreplyservice.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequest {

    @NotNull(message = "Reply comment cannot be null")
    String comment;
}
