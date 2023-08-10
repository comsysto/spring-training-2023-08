package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class ContextInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    Optional<List<HandlerInterceptor>> interceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.get().forEach(registry::addInterceptor);
    }

}

