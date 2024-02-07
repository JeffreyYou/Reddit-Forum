package com.beaconfire.messageservice.security;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private boolean active;

    private Timestamp dateJoined;

    @NotBlank
    private String type;

    private String profileImageURL;

    private boolean verified;
}
