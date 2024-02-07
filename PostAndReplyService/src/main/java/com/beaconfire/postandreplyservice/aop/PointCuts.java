package com.beaconfire.postandreplyservice.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {
    @Pointcut("within(com.beaconfire.postandreplyservice.controller.*)")
    public void inControllerLayer() {}
}
