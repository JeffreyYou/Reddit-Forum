package com.beaconfire.compositeservice.controller;

import com.beaconfire.compositeservice.dto.HistoryResponse;
import com.beaconfire.compositeservice.dto.PostResponse;
import com.beaconfire.compositeservice.service.CompositeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("composite")
@Tag(name = "Composite Service", description = "API for retrieving history posts using History and Post services")
public class CompositeController {

    private CompositeService compositeService;
    @Autowired
    public CompositeController(CompositeService compositeService) {
        this.compositeService = compositeService;
    }

    @GetMapping("/all/{userid}")
    @Operation(summary = "Get posts by user ID",
            description = "Returns all posts associated with a given user ID in descending order of view date",
            responses={
                    @ApiResponse(responseCode = "200", description = "Successful retrieval",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public List<PostResponse> getHistoryPostsByUserId(@Parameter(description = "ID of the user to retrieve posts for", required = true)
            @PathVariable("userid") Long userId, HttpServletRequest request){
        String prefixedToken = request.getHeader("Authorization"); // extract token value by key "Authorization"
        String token = prefixedToken.substring(7); // remove the prefix "Bearer "
        return compositeService.getHistoryByUserId(userId, token);
    }
}
