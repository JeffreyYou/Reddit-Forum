package com.beaconfire.postandreplyservice.service;

import com.beaconfire.postandreplyservice.dao.PostRepository;
import com.beaconfire.postandreplyservice.domain.Post;
import com.beaconfire.postandreplyservice.domain.Reply;
import com.beaconfire.postandreplyservice.domain.SubReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ReplyService {

    @Autowired
    PostRepository postRepository;

    public boolean checkArchivedPost(String postId) {
        return postRepository.findById(postId).get().getIsArchived();
    }

    public void replyToPost(String postId, String firstLayerReplyId, String username, String comment) {
        Post post = postRepository.findById(postId).get();
        Reply reply;
        if (firstLayerReplyId == null) {
            List<Reply> postReplies = post.getPostReplies();
            reply = Reply.builder().replyId(String.valueOf(postReplies.size())).userId(Long.parseLong(username))
                    .postId(postId).comment(comment).isActive(true)
                    .dateCreated(new Timestamp(System.currentTimeMillis()))
                    .subReplies(new ArrayList<>()).build();
            post.getPostReplies().add(reply);
        }
        else {
            Reply postReply = post.getPostReplies().get(Integer.parseInt(firstLayerReplyId));
            String subReplyId = firstLayerReplyId + "," + postReply.getSubReplies().size();
            SubReply subReply = SubReply.builder().replyId(subReplyId).userId(Long.parseLong(username))
                    .postId(postId).comment(comment).isActive(true)
                    .dateCreated(new Timestamp(System.currentTimeMillis())).build();
            post.getPostReplies().get(Integer.parseInt(firstLayerReplyId)).getSubReplies().add(subReply);
        }
        postRepository.save(post);
    }

    public boolean checkReplyOwnershipAndDelete(String postId, String replyId, String username) {
        Post post = postRepository.findById(postId).get();
        if (!replyId.contains(",")) {
            Reply reply = post.getPostReplies().get(Integer.parseInt(replyId));
            if (!Objects.equals(reply.getUserId(), Long.parseLong(username))) {
                return false;
            }
            post.getPostReplies().get(Integer.parseInt(replyId)).setIsActive(false);
        }
        else {
            String[] replyIdToken = replyId.split(",");
            String parentReplyId = replyIdToken[0], subReplyId = replyIdToken[1];
            Reply parentReply = post.getPostReplies().get(Integer.parseInt(parentReplyId));
            SubReply subReply = parentReply.getSubReplies().get(Integer.parseInt(subReplyId));
            if (!Objects.equals(subReply.getUserId(), Long.parseLong(username))) {
                return false;
            }
            post.getPostReplies().get(Integer.parseInt(parentReplyId))
                    .getSubReplies().get(Integer.parseInt(subReplyId)).setIsActive(false);
        }
        postRepository.save(post);
        return true;
    }

    public List<Reply> filterActiveRepliesAndSubs(List<Reply> allReplies) {
        List<Reply> activeReplies = new ArrayList<>();
        for (Reply reply : allReplies) {
            if (!reply.getIsActive()) {
                continue;
            }
            List<SubReply> activeSubReplies = new ArrayList<>();
            for (SubReply subReply : reply.getSubReplies()) {
                if (!subReply.getIsActive()) {
                    continue;
                }
                activeSubReplies.add(subReply);
            }
            Reply activeReply = Reply.builder().replyId(reply.getReplyId()).userId(reply.getUserId()).postId(reply.getPostId())
                    .comment(reply.getComment()).isActive(reply.getIsActive()).dateCreated(reply.getDateCreated())
                    .subReplies(activeSubReplies).build();
            activeReplies.add(activeReply);
        }
        return activeReplies;
    }
}
