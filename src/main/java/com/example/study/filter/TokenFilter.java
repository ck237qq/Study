package com.example.study.filter;

import com.example.study.security.BaseUser;
import com.example.study.security.BaseUserAuthenticationToken;
import com.example.study.service.AccessService;
import com.example.study.utils.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AccessService accessService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }


        String authorizationHeader = request.getHeader("Authorization");
        System.out.println(authorizationHeader);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader.substring(7);
        String userId = jwtUtil.extractUserId(jwt);
        String userType = jwtUtil.extractUserType(jwt);

        log.info("username : {} userType : {}", userId, userType);

        handleAuthentication(userId, jwt);

        // 調用過濾器鏈中的下一個過濾器
        filterChain.doFilter(request, response);
    }

    private void handleAuthentication(String userId, String jwt) {
        BaseUser user = accessService.loadUserByUsername(userId);

        if (Boolean.FALSE.equals(jwtUtil.validateToken(jwt, userId))) {
            log.info("validateToken false");
            return;
        }

        user.setToken(jwt);

        Authentication authentication = new BaseUserAuthenticationToken(user, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}