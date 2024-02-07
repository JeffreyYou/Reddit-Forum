package com.beaconfire.historyservice.controller;

import com.beaconfire.historyservice.domain.History;
import com.beaconfire.historyservice.dto.DataResponse;
import com.beaconfire.historyservice.dto.HistoryRequest;
import com.beaconfire.historyservice.security.AuthUserDetail;
import com.beaconfire.historyservice.security.JwtProvider;
import com.beaconfire.historyservice.service.HistoryService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "History Service", description = "API for managing history records")
@RequestMapping("/history")
@CrossOrigin(origins = "*")
public class HistoryController {

    private final HistoryService historyService;
    private final JwtProvider jwtProvider;
    @Value("${security.jwt.token.key}")
    private String key;
    @Autowired
    public HistoryController(HistoryService historyService,
    JwtProvider jwtProvider) {
        this.historyService = historyService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/all/{userid}")
    @Operation(summary = "Get history by user ID",
            description = "Returns all history records associated with a given user ID in descending order of view date",
            responses={
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = History.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public List<History> getMostRecentPosts(@Parameter(description = "ID of the user to retrieve recent posts for", required = true)
            @PathVariable Long userid){
        return historyService.findHistoriesByUserId(userid);
    }

    @PostMapping("/new")
    @Operation(summary="Create a new history record",
    description = "Create a record when user view a post",
    responses = {@ApiResponse(responseCode = "200", description = "Successfully created",
            content = @Content(mediaType = "application/json"))})
    public DataResponse create(@Parameter(description="Bearer token for authentication", required = true)
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                       @Parameter(description = "ID of the post viewed by the user", required = true)
                       @RequestBody HistoryRequest historyRequest){

        String postid = historyRequest.getPostId();
        String token = authorizationHeader.replace("Bearer ", "");
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Long userId = Long.valueOf(claims.getSubject());
        historyService.create(History.builder().postId(postid).userId(userId).viewDate(new Date()).build());
        return DataResponse.builder()
                .success(true)
                .message("new history created")
                .build();
    }
}
