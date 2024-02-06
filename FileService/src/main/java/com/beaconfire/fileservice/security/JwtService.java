package com.beaconfire.fileservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class JwtService {
    JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    public BigInteger getUserId(HttpServletRequest request){
        Optional<AuthUserDetail> authUserDetailOptional = jwtProvider.resolveToken(request);
        if(authUserDetailOptional.isPresent()) return authUserDetailOptional.get().getUserId();
        else return null;
    }
}
