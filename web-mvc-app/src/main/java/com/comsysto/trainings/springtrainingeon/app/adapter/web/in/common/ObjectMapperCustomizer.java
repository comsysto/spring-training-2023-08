package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Configuration
public class ObjectMapperCustomizer  {

    @Autowired
    ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        objectMapper.findAndRegisterModules();
    }

}
