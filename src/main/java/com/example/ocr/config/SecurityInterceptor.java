package com.example.ocr.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor for Security header validation.
 */
@Configuration
public class SecurityInterceptor implements HandlerInterceptor {
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Get the security Jwt token header and validate the token.

        if(request.getRequestURI().contains("/authenticate") || !StringUtils.hasText(request.getParameter("jwt-token"))) {
            // validate token.
            return true;

        } else {
            return false;
        }
      }
}
