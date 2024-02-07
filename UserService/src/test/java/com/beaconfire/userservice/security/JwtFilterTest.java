package com.beaconfire.userservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JwtFilterTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Clear the security context before each test
    }

    @Test
    void shouldSkipJwtProcessingForConfiguredPaths() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/user-service/user/authenticate");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, never()).resolveToken(any(HttpServletRequest.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldProcessJwtAndSetAuthenticationForValidToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/some/protected/resource");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtProvider.resolveToken(request)).thenReturn(Optional.of(AuthUserDetail.builder()
                .id(1L)
                .username("aw2@gnau.com")
                .password("password")
                .enabled(true)
                .authorities(Collections.singletonList(() -> "USER"))
                .verified(true)
                .build()));

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, times(1)).resolveToken(request);
        verify(filterChain, times(1)).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken;
    }

    @Test
    void shouldContinueFilterChainWhenNoTokenPresent() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/some/other/resource");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtProvider.resolveToken(request)).thenReturn(Optional.empty());

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, times(1)).resolveToken(request);
        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldHandleInvalidTokenGracefully() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/some/protected/resource");
        request.addHeader("Authorization", "Bearer invalidToken");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtProvider.resolveToken(request)).thenReturn(Optional.empty());

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, times(1)).resolveToken(request);
        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set for invalid tokens.");
    }

    @Test
    void shouldSkipJwtProcessingForUserCreate() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/user-service/user/create");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, never()).resolveToken(any(HttpServletRequest.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldSkipJwtProcessingForSwaggerUi() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/user-service/swagger-ui");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, never()).resolveToken(any(HttpServletRequest.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldSkipJwtProcessingForV3() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/user-service/v3");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, never()).resolveToken(any(HttpServletRequest.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
