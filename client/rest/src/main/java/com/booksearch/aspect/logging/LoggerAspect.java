package com.booksearch.aspect.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggerAspect {
    private final Logger log = LoggerFactory.getLogger(LoggerAspect.class);

    @Around("execution(* com.booksearch.controller..*Controller.*(..))")
    public Object timeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        final double MAX_AFFORDABLE_TIME = 5;

        long start = System.currentTimeMillis();
        long finish = System.currentTimeMillis();
        long timeMs = finish - start;

        Object proceed = joinPoint.proceed();

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        if (timeMs > MAX_AFFORDABLE_TIME) {
            log.warn(
                    "method={}, url={}, call: {} - {} - timeMs : {}",
                    getMethod(),
                    getRequestURL(),
                    className,
                    methodName,
                    timeMs / 1000.0 + " MS"
            );
            return proceed;
        }

        log.info("method={}, url={}, call: {} - {} - timeMs : {}",
                getMethod(),
                getRequestURL(),
                className,
                methodName,
                timeMs / 1000.0 + " MS"
        );
        return proceed;
    }

    private String getMethod() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getMethod();
    }

    private String getRequestURL() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        StringBuilder sb = new StringBuilder(128);

        sb.append(request.getRequestURL());
        if (null != request.getQueryString()) {
            sb.append("?");
            sb.append(request.getQueryString());
        }
        return sb.toString();
    }
}
