package com.beaconfire.fileservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private String key = "JavaTrainingMasterRedditHubJavaTrainingMasterRedditHubJavaTrainingMasterRedditHub";

    public Optional<AuthUserDetail> resolveToken(HttpServletRequest request){
        // token stored in header parameter Authorization
        String prefixedToken = request.getHeader("Authorization");
        if(prefixedToken == null) return Optional.empty();
        // get token string
        String token = prefixedToken.substring(7);

        Claims claims;
        try{
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (Exception e){
            return Optional.empty();
        }

        String userIdStr = claims.get("sub", String.class);
        BigInteger userId = new BigInteger(userIdStr);

        List<LinkedHashMap<String, String>> permissions = (List<LinkedHashMap<String, String>>) claims.get("permissions");
        List<GrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.get("authority")))
                .collect(Collectors.toList());

        return Optional.of(AuthUserDetail.builder()
                        .authorities(authorities)
                        .userId(userId)
                        .build());
    }
}
