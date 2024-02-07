package com.beaconfire.userservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


class JwtProviderTest {

    private static final String SECRET_KEY = "your-256-bit-secret"; // Ensure this matches the length required for the key

    @InjectMocks
    private JwtProvider jwtProvider;

    private Key key;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate a secure key
        this.key = key;
        jwtProvider.setKey(Base64.getEncoder().encodeToString(key.getEncoded()));
    }

    @Test
    void shouldCorrectlyResolveToken() {
        // Create a token
        List<LinkedHashMap<String, String>> permissions = Collections.singletonList(
                new LinkedHashMap<String, String>() {{
                    put("authority", "ROLE_USER");
                }}
        );
        String token = Jwts.builder()
                .setSubject("user")
                .claim("permissions", permissions)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) // 5 minutes expiry
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Mock HttpServletRequest to include the token
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        // Invoke resolveToken and assert results
        Optional<AuthUserDetail> authUserDetailOptional = jwtProvider.resolveToken(request);

        assertThat(authUserDetailOptional).isPresent();
        AuthUserDetail authUserDetail = authUserDetailOptional.get();
        assertThat(authUserDetail.getUsername()).isEqualTo("user");
        assertThat(authUserDetail.getAuthorities()).hasSize(1);
        assertThat(authUserDetail.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_USER");
    }

    // Additional tests can simulate various error conditions, such as expired tokens or incorrect signing keys

}
