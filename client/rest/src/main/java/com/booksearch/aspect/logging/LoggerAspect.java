package com.booksearch.aspect.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LoggerAspect {


    @Around("execution(* com.booksearch.controller..*Controller.*(..))")
    public Object timeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        final int MAX_AFFORDABLE_TIME = 5000;

        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long finish = System.currentTimeMillis();

        long proceedTime = finish - start;

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        if (proceedTime > MAX_AFFORDABLE_TIME) {
            log.warn(
                    "method: {}, url: {}, call: {}.{}, proceed time: {}ms",
                    getMethod(),
                    getRequestURL(),
                    className,
                    methodName,
                    proceedTime / 1000.0
            );
            return proceed;
        }

        log.info("method: {}, url: {}, call: {}.{}, proceed time: {}ms",
                getMethod(),
                getRequestURL(),
                className,
                methodName,
                proceedTime / 1000.0
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
