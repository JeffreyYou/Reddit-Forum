package com.beaconfire.userservice.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Users") // Assuming default schema, adjust as needed
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 512)
    private String password;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private Timestamp dateJoined;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, length = 512)
    private String profileImageURL;

    @Column(nullable = false)
    private boolean verified;

    /******* token + expired time newly added ******/
    @Column
    private String emailToken;

    @Column
    private Timestamp emailTokenExpiredTime;
}

