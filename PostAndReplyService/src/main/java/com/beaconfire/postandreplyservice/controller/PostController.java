package com.beaconfire.postandreplyservice.controller;

import com.beaconfire.postandreplyservice.domain.Post;
import com.beaconfire.postandreplyservice.domain.Reply;
import com.beaconfire.postandreplyservice.dto.request.PostRequest;
import com.beaconfire.postandreplyservice.dto.response.PostResponse;
import com.beaconfire.postandreplyservice.dto.common.ResponseStatus;
import com.beaconfire.postandreplyservice.dto.response.GetUserVerifiedResponse;
import com.beaconfire.postandreplyservice.exception.NonOwnerPostException;
import com.beaconfire.postandreplyservice.exception.PostNotFoundException;
import com.beaconfire.postandreplyservice.exception.StateChangeException;
import com.beaconfire.postandreplyservice.exception.UnverifiedUserException;
import com.beaconfire.postandreplyservice.service.PostService;
import com.beaconfire.postandreplyservice.service.ReplyService;
import com.beaconfire.postandreplyservice.enums.PostStatus;
import com.beaconfire.postandreplyservice.service.remote.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    ReplyService replyService;

    @Autowired
    UserClient userClient;

    @PutMapping("publish")
    public ResponseEntity<PostResponse> publishNewPost(@RequestBody PostRequest newPostRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        GetUserVerifiedResponse response = userClient.getUserVerified();
        boolean isVerifiedUser = response.isVerified();

        if (!isVerifiedUser) {
            throw new UnverifiedUserException("User without email verification cannot reply to any post");
        }
        Optional<String> possiblePostId = newPostRequest.getPostId();
        String title = newPostRequest.getTitle(), content = newPostRequest.getContent();
        ArrayList<String> images = newPostRequest.getImages(), attachments = newPostRequest.getAttachments();
        Post post = postService.publishPost(username, possiblePostId, title, content, images, attachments);
        return new ResponseEntity<>(
                PostResponse.builder()
                        .userId(Long.parseLong(username))
                        .title(post.getTitle())
                        .dateCreated(new Date(post.getDateCreated().getTime()))
                        .build(), HttpStatus.CREATED
        );
    }

    @PutMapping("save")
    public ResponseEntity<PostResponse> savePost(@RequestBody PostRequest newPostRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        GetUserVerifiedResponse response = userClient.getUserVerified();
        boolean isVerifiedUser = response.isVerified();

        if (!isVerifiedUser) {
            throw new UnverifiedUserException("User without email verification cannot reply to any post");
        }
        Optional<String> possiblePostId = newPostRequest.getPostId();
        String title = newPostRequest.getTitle(), content = newPostRequest.getContent();
        ArrayList<String> images = newPostRequest.getImages(), attachments = newPostRequest.getAttachments();
        Post post = postService.savePost(username, possiblePostId, title, content, images, attachments);
        return new ResponseEntity<>(
                PostResponse.builder()
                        .userId(Long.parseLong(username))
                        .title(post.getTitle())
                        .dateCreated(new Date(post.getDateCreated().getTime()))
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping("published/all")
    public ResponseEntity<List<PostResponse>> getPublishedPosts() {
        List<Post> posts = postService.getAllPostsByStatus(PostStatus.Published);
        return new ResponseEntity<>(
                posts.stream().map(
                        post -> PostResponse.builder().responseStatus(
                                    ResponseStatus.builder()
                                            .success(true).message("Successfully get all published posts")
                                            .build()
                                )
                                .postId(post.getPostId())
                                .userId(post.getUserId())
                                .title(post.getTitle())
                                .dateCreated(new Date(post.getDateCreated().getTime()))
                                .build()
                ).collect(Collectors.toList()), HttpStatus.OK
        );
    }

    @GetMapping("banned/all")
    public ResponseEntity<List<PostResponse>> getBannedPosts() {
        List<Post> posts = postService.getAllPostsByStatus(PostStatus.Banned);
        return new ResponseEntity<>(
                posts.stream().map(
                        post -> PostResponse.builder().responseStatus(
                                    ResponseStatus.builder()
                                            .success(true).message("Successfully get all banned posts")
                                            .build()
                                )
                                .postId(post.getPostId())
                                .userId(post.getUserId())
                                .title(post.getTitle())
                                .dateCreated(new Date(post.getDateCreated().getTime()))
                                .build()
                ).collect(Collectors.toList()), HttpStatus.OK
        );
    }

    @GetMapping("deleted/all")
    public ResponseEntity<List<PostResponse>> getDeletedPosts() {
        List<Post> posts = postService.getAllPostsByStatus(PostStatus.Deleted);
        return new ResponseEntity<>(
                posts.stream().map(
                        post -> PostResponse.builder().responseStatus(
                                    ResponseStatus.builder()
                                            .success(true).message("Successfully get all deleted posts")
                                            .build()
                                )
                                .postId(post.getPostId())
                                .userId(post.getUserId())
                                .title(post.getTitle())
                                .dateCreated(new Date(post.getDateCreated().getTime()))
                                .build()
                ).collect(Collectors.toList()), HttpStatus.OK
        );
    }

    @GetMapping("unpublished/all")
    public ResponseEntity<List<PostResponse>> getUnpublishedPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Post> posts = postService.getAllPostsByStatusAndOwner(PostStatus.Unpublished, username);
        return new ResponseEntity<>(
                posts.stream().map(
                        post -> PostResponse.builder().responseStatus(
                                        ResponseStatus.builder()
                                                .success(true).message("Successfully get all unpublished posts")
                                                .build()
                                )
                                .postId(post.getPostId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .dateCreated(new Date(post.getDateCreated().getTime()))
                                .dateModified(new Date(post.getDateModified().getTime()))
                                .status(post.getStatus())
                                .isArchived(post.getIsArchived())
                                .build()
                ).collect(Collectors.toList()), HttpStatus.OK
        );
    }

    @GetMapping("hidden/all")
    public ResponseEntity<List<PostResponse>> getHiddenPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Post> posts = postService.getAllPostsByStatusAndOwner(PostStatus.Hidden, username);
        return new ResponseEntity<>(
                posts.stream().map(
                        post -> PostResponse.builder().responseStatus(
                                        ResponseStatus.builder()
                                                .success(true).message("Successfully get all hidden posts")
                                                .build()
                                )
                                .postId(post.getPostId())
                                .title(post.getTitle())
                                .dateCreated(new Date(post.getDateCreated().getTime()))
                                .build()
                ).collect(Collectors.toList()), HttpStatus.OK
        );
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable String postId) {
        Optional<Post> possiblePost = postService.getPostById(postId);
        if (!possiblePost.isPresent()) {
            throw new PostNotFoundException("Post " + postId + " not found");
        }
        Post post = possiblePost.get();
        List<Reply> activeRepliesAndSubs = replyService.filterActiveRepliesAndSubs(post.getPostReplies());
        return new ResponseEntity<>(
                PostResponse.builder()
                        .postId(post.getPostId())
                        .userId(post.getUserId())
                        .title(post.getTitle())
                        .dateCreated(new Date(post.getDateCreated().getTime()))
                        .content(post.getContent())
                        .dateModified(new Date(post.getDateModified().getTime()))
                        .status(post.getStatus())
                        .isArchived(post.getIsArchived())
                        .images(post.getImages())
                        .attachments(post.getAttachments())
                        .postReplies(activeRepliesAndSubs)
                        .build(), HttpStatus.OK
        );
    }

    @PatchMapping("{postId}/ban")
    public ResponseEntity<PostResponse> banPost(@PathVariable String postId) {
        String currentStatus = postService.checkPostStatus(postId);
        if (!currentStatus.equals(PostStatus.Published.name())) {
            throw new StateChangeException("Only published post can be banned");
        }
        String updatedStatus = postService.updateStatus(postId, PostStatus.Banned);
        return new ResponseEntity<>(
                PostResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully ban the post")
                                .build()
                ).status(updatedStatus).build(), HttpStatus.OK);
    }

    @PatchMapping("{postId}/unban")
    public ResponseEntity<PostResponse> unbanPost(@PathVariable String postId) {
        String currentStatus = postService.checkPostStatus(postId);
        if (!currentStatus.equals(PostStatus.Banned.name())) {
            throw new StateChangeException("Only banned post can be unbanned");
        }
        String updatedStatus = postService.updateStatus(postId, PostStatus.Published);
        return new ResponseEntity<>(
                PostResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully unban the post")
                                .build()
                ).status(updatedStatus).build(), HttpStatus.OK);
    }

    @PatchMapping("{postId}/hide")
    public ResponseEntity<PostResponse> hidePost(@PathVariable String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isOwner = postService.checkPostOwnership(postId, username);
        if (!isOwner) {
            throw new NonOwnerPostException("Non-owner user cannot hide post");
        }
        String currentStatus = postService.checkPostStatus(postId);
        if (!currentStatus.equals(PostStatus.Published.name())) {
            throw new StateChangeException("Only published post can be hidden");
        }
        String updatedStatus = postService.updateStatus(postId, PostStatus.Hidden);
        return new ResponseEntity<>(
                PostResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully hide the post")
                                .build()
                ).status(updatedStatus).build(), HttpStatus.OK);
    }

    @PatchMapping("{postId}/unhide")
    public ResponseEntity<PostResponse> unhidePost(@PathVariable String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isOwner = postService.checkPostOwnership(postId, username);
        if (!isOwner) {
            throw new NonOwnerPostException("Non-owner user cannot unhide post");
        }
        String currentStatus = postService.checkPostStatus(postId);
        if (!currentStatus.equals(PostStatus.Hidden.name())) {
            throw new StateChangeException("Only hidden post can be unhidden");
        }
        String updatedStatus = postService.updateStatus(postId, PostStatus.Published);
        return new ResponseEntity<>(
                PostResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully unhide the post")
                                .build()
                ).status(updatedStatus).build(), HttpStatus.OK);
    }

    @PatchMapping("{postId}/delete")
    public ResponseEntity<PostResponse> deletePost(@PathVariable String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isOwner = postService.checkPostOwnership(postId, username);
        if (!isOwner) {
            throw new NonOwnerPostException("Non-owner user cannot delete post");
        }
        String currentStatus = postService.checkPostStatus(postId);
        if (!currentStatus.equals(PostStatus.Published.name())) {
            throw new StateChangeException("Only published post can be deleted");
        }
        String updatedStatus = postService.updateStatus(postId, PostStatus.Deleted);
        return new ResponseEntity<>(
                PostResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully delete the post")
                                .build()
                ).status(updatedStatus).build(), HttpStatus.OK);
    }

    @PatchMapping("{postId}/recover")
    public ResponseEntity<PostResponse> recoverPost(@PathVariable String postId) {
        String currentStatus = postService.checkPostStatus(postId);
        if (!currentStatus.equals(PostStatus.Deleted.name())) {
            throw new StateChangeException("Only deleted post can be recovered");
        }
        String updatedStatus = postService.updateStatus(postId, PostStatus.Published);
        return new ResponseEntity<>(
                PostResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully recover the post")
                                .build()
                ).status(updatedStatus).build(), HttpStatus.OK);
    }

    @PatchMapping("{postId}/modify")
    public ResponseEntity<PostResponse> editPost(@RequestBody PostRequest postRequest, @PathVariable String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(authentication.getAuthorities());
        boolean isOwner = postService.checkPostOwnership(postId, username);
        if (!isOwner) {
            throw new NonOwnerPostException("Non-owner user cannot edit post");
        }
        String currentStatus = postService.checkPostStatus(postId);
        if (!(currentStatus.equals(PostStatus.Published.name()) || currentStatus.equals(PostStatus.Hidden.name()))) {
            throw new StateChangeException("Only published or hidden post can be modified");
        }
        String title = postRequest.getTitle(), content = postRequest.getContent();
        ArrayList<String> images = postRequest.getImages(), attachments = postRequest.getAttachments();
        Post post = postService.editPost(postId, title, content, images, attachments);
        return new ResponseEntity<>(
                PostResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully edit the post")
                                .build()
                ).title(post.getTitle())
                        .content(post.getContent())
                        .dateModified(new Date(post.getDateModified().getTime()))
                        .images(images)
                        .attachments(attachments)
                        .build(), HttpStatus.OK
        );
    }

    @PatchMapping("{postId}/archive")
    public ResponseEntity<PostResponse> archivePost(@PathVariable String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isOwner = postService.checkPostOwnership(postId, username);
        if (!isOwner) {
            throw new NonOwnerPostException("Non-owner user cannot archive post");
        }
        boolean isArchived = postService.archivePost(postId);
        return new ResponseEntity<>(
                PostResponse.builder().responseStatus(
                        ResponseStatus.builder()
                                .success(true).message("Successfully archive the post")
                                .build()
                ).isArchived(isArchived).build(), HttpStatus.OK
        );
    }

    @GetMapping("top3")
    public ResponseEntity<List<PostResponse>> getTop3PostsByReplies() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Post> top3PostsByReplies = postService.getTop3PostsByReplies(username);
        return new ResponseEntity<>(
                top3PostsByReplies.stream().map(
                        post -> PostResponse.builder().responseStatus(
                                        ResponseStatus.builder()
                                                .success(true).message("Successfully get all published posts")
                                                .build()
                                )
                                .postId(post.getPostId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .dateCreated(new Date(post.getDateCreated().getTime()))
                                .dateModified(new Date(post.getDateModified().getTime()))
                                .status(post.getStatus())
                                .isArchived(post.getIsArchived())
                                .build()
                ).collect(Collectors.toList()), HttpStatus.OK
        );
    }
}
