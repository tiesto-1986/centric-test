package com.centric.aspects;

import com.centric.exception.CentricException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class CnyInboundRequestInterceptorAspect {

    private static final String API_KEY_HEADER = "apikey";

    @Value("${security.api.key}")
    private String apiKey;

    @Before("@annotation(com.centric.annotations.SecurityInterceptor)")
    public void interceptor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String secureHeaderValue = request.getHeader(API_KEY_HEADER);
        if (!apiKey.equals(secureHeaderValue)) {
            log.error("Api key value is missing or invalid: {}", secureHeaderValue);
            throw new CentricException("Api key value is missing or invalid", HttpStatus.FORBIDDEN);
        }
    }
}
