package com.beaconfire.postandreplyservice.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.beaconfire.postandreplyservice.dto.response.GetUserVerifiedResponse;

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/user-service/user/verified")
    GetUserVerifiedResponse getUserVerified();
}
