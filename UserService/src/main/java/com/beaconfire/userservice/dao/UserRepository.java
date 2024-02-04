package com.beaconfire.userservice.dao;

import com.beaconfire.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email (useful for login, email verification processes)
    Optional<User> findByEmail(String email);

    // Custom method to find active users
    List<User> findByActive(boolean active);

    // Custom method to update user's activation status
    @Modifying
    @Query("update User u set u.active = :active where u.id = :userId")
    int updateUserActivationStatus(@Param("userId") Long userId, @Param("active") boolean active);

}
