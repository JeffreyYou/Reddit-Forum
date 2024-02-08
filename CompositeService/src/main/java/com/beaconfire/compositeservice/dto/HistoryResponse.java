package com.beaconfire.compositeservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class HistoryResponse {

    Long historyId;
    Long userId;
    String postId;
    Date viewDate;
}
