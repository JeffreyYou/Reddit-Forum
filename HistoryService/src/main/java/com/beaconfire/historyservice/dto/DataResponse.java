package com.beaconfire.historyservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataResponse {
        private Boolean success;
        private String message;

}
