package com.beaconfire.postandreplyservice.util;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JwtTokenRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String token = JwtTokenHolder.getToken();
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}

