package com.beaconfire.historyservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class History {

    @Schema
    private long historyId;
    private long userId;
    private String postId; //one-to-one
    private Date viewDate;
}
