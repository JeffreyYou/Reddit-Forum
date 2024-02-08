package com.beaconfire.postandreplyservice.security;

import com.beaconfire.postandreplyservice.util.JwtTokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized User");
            return; // Exit the filter chain early if there's no valid Authorization header
        }

        // At this point, we know the Authorization header is present and well-formed
        String jwtToken = authorizationHeader.substring(7);
        JwtTokenHolder.setToken(jwtToken);

        Optional<AuthUserDetail> authUserDetailOptional = jwtProvider.resolveToken(request); // extract jwt from request, generate a userdetails object

        if (authUserDetailOptional.isPresent()) {
            AuthUserDetail authUserDetail = authUserDetailOptional.get();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    authUserDetail.getUsername(),
                    null,
                    authUserDetail.getAuthorities()
            ); // generate authentication object

            SecurityContextHolder.getContext().setAuthentication(authentication); // put authentication object in the security context
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // Ensure that the JWT token is cleared after the request has been processed
            JwtTokenHolder.clear();
        }
    }
}
