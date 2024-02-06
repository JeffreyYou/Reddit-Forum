package com.beaconfire.authservice.security;

import com.beaconfire.userservice.security.AuthUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${security.jwt.token.key}")
    private String key;


    // create jwt from a UserDetail
    public String createToken(AuthUserDetail authUserDetails){
        //Claims is essentially a key-value pair, where the key is a string and the value is an object
        Claims claims = Jwts.claims().setSubject(authUserDetails.getId().toString()); // user identifier
        claims.put("permissions", authUserDetails.getAuthorities()); // user permission
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key) // algorithm and key to sign the token
                .compact();
    }
}
