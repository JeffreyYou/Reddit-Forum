package com.beaconfire.postandreplyservice.dao;

import com.beaconfire.postandreplyservice.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findByPostIdAndUserId(String postId, Long userId);

    List<Post> findAllByStatusOrderByDateCreatedDesc(String status);

    List<Post> findAllByStatusAndUserId(String status, Long userId);

    List<Post> findAllByUserId(Long userId);
}
