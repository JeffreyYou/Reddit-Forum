package com.beaconfire.userservice.domain;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class UserPage extends PageImpl<User> {
    public UserPage(List<User> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public UserPage(List<User> content) {
        super(content);
    }
}
