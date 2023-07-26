package com.comsysto.trainings.springtrainingeon.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
@EnableWebMvc
public class MvcSpringTrainingEonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcSpringTrainingEonApplication.class, args);
    }

}
