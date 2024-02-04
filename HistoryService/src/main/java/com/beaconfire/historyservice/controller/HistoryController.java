package com.beaconfire.historyservice.controller;

import com.beaconfire.historyservice.domain.History;
import com.beaconfire.historyservice.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "History Service", description = "API for managing history records")
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping("/new")
    @Operation( summary = "Create new history record")
    public void create(){}

    @GetMapping("/recent/{userid}")
    @Operation(summary = "Get most recent posts by user ID",
            description = "Returns all posts associated with a given user ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = History.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public List<History> getMostRecentPosts(@Parameter(description = "ID of the user to retrieve recent posts for", required = true)
            @PathVariable Long userid){
        return historyService.findMostRecentHistoriesByUserId(userid);
    }

    @GetMapping("/all/{userid}")
    @Operation(summary = "Get all posts by user ID",
            description = "Returns a list of the most recent posts for a given user ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = History.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public List<History> getAllPostsByUserId(@Parameter(description = "ID of the user to retrieve all posts for", required = true)
            @PathVariable Long userid){
        return historyService.findAllHistoriesByUserId(userid);
    }


}
