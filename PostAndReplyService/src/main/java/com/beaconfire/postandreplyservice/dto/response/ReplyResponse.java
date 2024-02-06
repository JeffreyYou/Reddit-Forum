package com.beaconfire.postandreplyservice.dto.response;

import com.beaconfire.postandreplyservice.dto.common.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyResponse {
    private ResponseStatus responseStatus;
    private String comment;
    private Boolean isActive;
}
