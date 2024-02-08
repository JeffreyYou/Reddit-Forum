package com.beaconfire.postandreplyservice.service;

import com.beaconfire.postandreplyservice.dao.PostRepository;
import com.beaconfire.postandreplyservice.domain.Post;
import com.beaconfire.postandreplyservice.domain.Reply;
import com.beaconfire.postandreplyservice.enums.PostStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Post publishPost(String username, Optional<String> possiblePostId, String title, String content,
                            ArrayList<String> images, ArrayList<String> attachments) {
        Post post;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (possiblePostId.isPresent()) {
            String postId = possiblePostId.get();
            post = postRepository.findById(postId).get();
            post.setTitle(title); post.setContent(content); post.setStatus("Published");
            post.setDateCreated(currentTime); post.setDateModified(currentTime);
            post.setImages(images); post.setAttachments(attachments);
        }
        else {
            post = Post.builder().userId(Long.parseLong(username)).title(title).content(content)
                    .dateCreated(currentTime).dateModified(currentTime)
                    .status("Published").isArchived(false).postReplies(new ArrayList<>())
                    .images(images).attachments(attachments).build();
        }
        Post upsertedPost = postRepository.save(post);
        return upsertedPost;
    }

    public Post savePost(String username, Optional<String> possiblePostId, String title, String content,
                         ArrayList<String> images, ArrayList<String> attachments) {
        Post post;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (possiblePostId.isPresent()) {
            String postId = possiblePostId.get();
            post = postRepository.findById(postId).get();
            post.setTitle(title); post.setContent(content);
            post.setDateCreated(currentTime); post.setDateModified(currentTime);
            post.setImages(images); post.setAttachments(attachments);
        }
        else {
            post = Post.builder().userId(Long.parseLong(username)).title(title).content(content)
                    .dateCreated(currentTime).dateModified(currentTime)
                    .status("Unpublished").isArchived(false).postReplies(new ArrayList<>())
                    .images(images).attachments(attachments).build();
        }
        Post upsertedPost = postRepository.save(post);
        return upsertedPost;
    }

    public Post editPost(String postId, String title, String content,
                         ArrayList<String> images, ArrayList<String> attachments) {
        Post post = postRepository.findById(postId).get();
        post.setTitle(title); post.setContent(content);
        post.setDateModified(new Timestamp(System.currentTimeMillis()));
        post.setImages(images); post.setAttachments(attachments);
        postRepository.save(post);
        return post;
    }

    public List<Post> getAllPostsByStatus(PostStatus postStatus) {
        return postRepository.findAllByStatusOrderByDateCreatedDesc(postStatus.name());
    }

    public List<Post> getAllPostsByStatusAndOwner(PostStatus postStatus, String username) {
        return postRepository.findAllByStatusAndUserId(postStatus.name(), Long.parseLong(username));
    }

    public Optional<Post> getPostById(String postId) {
        return postRepository.findById(postId);
    }

    public String checkPostStatus(String postId) {
        Post post = postRepository.findById(postId).get();
        return post.getStatus();
    }

    public String updateStatus(String postId, PostStatus updateStatus) {
        Post post = postRepository.findById(postId).get();
        post.setStatus(updateStatus.name());
        postRepository.save(post);
        return post.getStatus();
    }

    public boolean checkPostOwnership(String postId, String username) {
        Long userId = Long.parseLong(username);
        System.out.println("postId" + postId);
        System.out.println("username" + userId);
        Optional<Post> possiblePost = postRepository.findByPostIdAndUserId(postId, userId);
        return possiblePost.isPresent();
    }

    public boolean archivePost(String postId) {
        Post post = postRepository.findById(postId).get();
        post.setIsArchived(true);
        postRepository.save(post);
        return post.getIsArchived();
    }

    public List<Post> getTop3PostsByReplies(String username) {
        List<Post> allPostsByUser = postRepository.findAllByUserId(Long.parseLong(username));
        HashMap<String, Integer> postIdToNumReplies = new HashMap<>();
        for (Post post : allPostsByUser) {
            int numReplies = post.getPostReplies().size();
            for (Reply reply : post.getPostReplies()) {
                numReplies += reply.getSubReplies().size();
            }
            postIdToNumReplies.put(post.getPostId(), numReplies);
        }
        allPostsByUser.sort(new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return postIdToNumReplies.get(p2.getPostId()).compareTo(postIdToNumReplies.get(p1.getPostId()));
            }
        });
        return allPostsByUser.subList(0, Math.min(allPostsByUser.size(), 3));
    }
}
