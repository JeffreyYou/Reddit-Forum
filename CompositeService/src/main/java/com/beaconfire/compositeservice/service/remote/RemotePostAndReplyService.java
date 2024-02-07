package com.beaconfire.compositeservice.service.remote;


import com.beaconfire.compositeservice.dto.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("post-reply-service")
public interface RemotePostAndReplyService {
    @GetMapping("post-reply-service/posts/{postId}")
    PostResponse getPostById(@PathVariable("postId")String postId, @RequestHeader("Authorization")  String token);
}
