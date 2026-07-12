package com.studyroom.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Order(1)
public class ApiPathRewriteFilter implements Filter {

    private static final List<String> API_PATHS = Arrays.asList(
            "/auth/login",
            "/auth/register",
            "/auth/userinfo",
            "/campus/buildings",
            "/campus/classrooms",
            "/campus/seats",
            "/booking/my",
            "/booking/create",
            "/booking/cancel",
            "/booking/signin",
            "/booking/release",
            "/booking/violations"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        for (String apiPath : API_PATHS) {
            if (requestURI.equals(apiPath)) {
                String newURI = "/api" + requestURI;
                log.debug("Rewriting path: {} -> {}", requestURI, newURI);
                
                HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
                    @Override
                    public String getRequestURI() {
                        return newURI;
                    }
                    
                    @Override
                    public String getServletPath() {
                        return newURI;
                    }
                };
                
                chain.doFilter(wrappedRequest, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}