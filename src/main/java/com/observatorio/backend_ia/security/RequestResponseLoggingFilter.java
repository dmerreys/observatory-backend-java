package com.observatorio.backend_ia.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    private static final int MAX_BODY_LENGTH = 10_000;

    private static final Set<String> SENSITIVE_HEADERS = Set.of("authorization", "cookie", "set-cookie");

    private static final Pattern SENSITIVE_FIELDS = Pattern.compile(
            "(\\\"(?:password|oldPassword|newPassword|token|refreshToken|accessToken|authorization|api_key|apikey)\\\"\\s*:\\s*\\\")[^\\\"]*(\\\")",
            Pattern.CASE_INSENSITIVE);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logRequest(wrappedRequest);
            logResponse(wrappedRequest, wrappedResponse, duration);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return;
        }
        String body = readBody(request.getContentAsByteArray());
        log.info("--> {} {} | headers={} | body={}",
                request.getMethod(),
                request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""),
                redactHeaders(request),
                body);
    }

    private void logResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long duration) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return;
        }
        String body = readBody(response.getContentAsByteArray());
        log.info("<-- {} {} | status={} | duration={}ms | body={}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration,
                body);
    }

    private String readBody(byte[] content) {
        if (content.length == 0) {
            return "[no body]";
        }
        String raw = new String(content, StandardCharsets.UTF_8);
        if (raw.length() > MAX_BODY_LENGTH) {
            raw = raw.substring(0, MAX_BODY_LENGTH) + "... (truncated)";
        }
        return redactSensitiveFields(raw);
    }

    private List<String> redactHeaders(HttpServletRequest request) {
        if (request.getHeaderNames() == null) {
            return Collections.emptyList();
        }
        return Collections.list(request.getHeaderNames()).stream()
                .map(header -> header + "=" + (SENSITIVE_HEADERS.contains(header.toLowerCase()) ? "***" : request.getHeader(header)))
                .toList();
    }

    private String redactSensitiveFields(String json) {
        return SENSITIVE_FIELDS.matcher(json).replaceAll("$1***$2");
    }
}
