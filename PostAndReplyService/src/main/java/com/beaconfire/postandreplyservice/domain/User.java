package com.beaconfire.postandreplyservice.domain;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
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
