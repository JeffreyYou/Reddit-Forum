package com.beaconfire.postandreplyservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterThrowing(value = "com.beaconfire.postandreplyservice.aop.PointCuts.inControllerLayer()", throwing = "ex")
    public void logThrownException(JoinPoint joinPoint, Throwable ex) {
        logger.error("From LoggingAspect.logThrownException: " + ex.getMessage() + ": " + joinPoint.getSignature());
    }
}
