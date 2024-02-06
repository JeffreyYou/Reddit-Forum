package com.beaconfire.postandreplyservice.controller;

import com.beaconfire.postandreplyservice.dto.request.ReplyRequest;
import com.beaconfire.postandreplyservice.dto.response.ReplyResponse;
import com.beaconfire.postandreplyservice.dto.common.ResponseStatus;
import com.beaconfire.postandreplyservice.exception.NonOwnerReplyException;
import com.beaconfire.postandreplyservice.exception.ReplyToArchivedPostException;
import com.beaconfire.postandreplyservice.exception.UnverifiedUserException;
import com.beaconfire.postandreplyservice.service.ReplyService;
 import com.beaconfire.postandreplyservice.service.remote.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.beaconfire.postandreplyservice.dto.response.GetUserVerifiedResponse;

@RestController
@RequestMapping("reply")
@CrossOrigin(origins = "*")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @Autowired
    UserClient userClient;

    @PatchMapping("{postId}/reply")
    public ResponseEntity<ReplyResponse> replyToPost(@PathVariable String postId, @RequestParam(required = false) String firstLayerReplyId,
                                                     @RequestBody ReplyRequest replyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
//        GetUserVerifiedResponse response = userClient.getUserVerified();
//        boolean isVerifiedUser = response.isVerified();
        boolean isVerifiedUser = true;
        if (!isVerifiedUser) {
            throw new UnverifiedUserException("User without email verification cannot reply to any post");
        }
        if (replyService.checkArchivedPost(postId)) {
            throw new ReplyToArchivedPostException("User cannot reply to archived post");
        }
        String comment = replyRequest.getComment();
        replyService.replyToPost(postId, firstLayerReplyId, username, comment);
        return new ResponseEntity<>(
                ReplyResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully reply to the post")
                                .build()
                ).comment(comment).build(), HttpStatus.OK);
    }

    @PatchMapping("{postId}/delete-reply/{replyId}")
    public ResponseEntity<ReplyResponse> deleteReply(@PathVariable String postId, @PathVariable String replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean successfullyDeleted = replyService.checkReplyOwnershipAndDelete(postId, replyId, username);
        if (!successfullyDeleted) {
            throw new NonOwnerReplyException("Non-owner user cannot delete reply");
        }
        return new ResponseEntity<>(
                ReplyResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully delete the reply")
                                .build()
                ).isActive(false).build(), HttpStatus.OK);
    }
}
