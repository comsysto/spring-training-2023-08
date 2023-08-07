package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
//@Configuration
public class ContextInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor(){
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                log.info("auth token: {}", authHeader);

                response.setContentType("text/plain");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().println("Booom");
                response.getWriter().flush();
                return false;

            }
        });
    }

}

