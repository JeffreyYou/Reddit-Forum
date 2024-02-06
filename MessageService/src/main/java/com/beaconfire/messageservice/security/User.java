package com.beaconfire.messageservice.security;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private boolean active;

    private Timestamp dateJoined;

    private String type;

    private String profileImageURL;

    private boolean verified;

}


