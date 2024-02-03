package com.beaconfire.historyservice.controller;

import com.beaconfire.historyservice.entity.History;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name="History Service")
@RequestMapping("/history")
public class HistoryController {

    //create a new history record
    @PostMapping("/new")
    @Operation(summary="Create new history record")
    public void create(){}

    //get history posts by user id
    @GetMapping("/{userid}")
    @Operation(summary="Get history records by user id")
    public List<History> getByUser(){
        return null;
    }


}
