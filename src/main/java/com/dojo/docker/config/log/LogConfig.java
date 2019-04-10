package com.dojo.docker.config.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
public class LogConfig extends OncePerRequestFilter {
    private static final String X_REQUEST_ID = "x-request-id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        insertIntoMDC(request);
        try {
            filterChain.doFilter(request, response);
        } finally {
            clearMDC();
        }
    }

    private void insertIntoMDC(final HttpServletRequest request) {

        final String xRequestIdValue = getXRequestId(request);
        MDC.put(X_REQUEST_ID, xRequestIdValue);

    }

    private void clearMDC() {
        MDC.remove(X_REQUEST_ID);
    }

    private String getXRequestId(final HttpServletRequest request) {
        String xRequestIdValue = request.getHeader(X_REQUEST_ID);
        if (!hasText(xRequestIdValue)) {
            xRequestIdValue = UUID.randomUUID().toString();
        }
        return xRequestIdValue;
    }
}
