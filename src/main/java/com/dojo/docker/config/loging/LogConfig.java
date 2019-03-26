package com.dojo.docker.config.loging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class LogConfig extends CommonsRequestLoggingFilter {

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isInfoEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        request.getHeaderNames()
                .asIterator()
                .forEachRemaining(headerName -> MDC.put(headerName, request.getHeader(headerName)));
        log.info("starting");
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        MDC.clear();
    }
}
