package com.studyroom.filter;

import com.studyroom.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (isPublicPath(requestURI, method)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authHeader.substring(7);

            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
                String username = jwtUtil.extractUsername(jwt);
                String role = jwtUtil.extractRole(jwt);

                String roleName = "ADMIN".equals(role) ? "ROLE_ADMIN" : "ROLE_STUDENT";

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(roleName))
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String uri, String method) {
        if (uri.equals("/") || uri.equals("/index.html")) {
            return true;
        }

        if (uri.startsWith("/assets/") || uri.startsWith("/favicon.ico")) {
            return true;
        }

        if (uri.startsWith("/student") || uri.startsWith("/admin")) {
            return true;
        }

        if (uri.startsWith("/api/auth/login") || uri.startsWith("/auth/login")) {
            return true;
        }

        if (uri.startsWith("/api/auth/register") || uri.startsWith("/auth/register")) {
            return true;
        }

        if (uri.startsWith("/api/public/")) {
            return true;
        }

        if (uri.startsWith("/doc.html") || uri.startsWith("/swagger-ui") || 
            uri.startsWith("/api-docs") || uri.startsWith("/v3/api-docs") || 
            uri.startsWith("/webjars")) {
            return true;
        }

        if ("GET".equals(method)) {
            if (uri.startsWith("/api/campus/") || uri.startsWith("/campus/")) {
                return true;
            }
        }

        return false;
    }

}