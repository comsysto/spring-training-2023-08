package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.Map;

@Data
@ConfigurationProperties("rest-client")
public class RestClientConfigurationProperties implements TargetSystemConfigProvider{

    @Data
    public static class TargetSystemConfigProperties {
        URI uri;
    }

    Map<TargetSystem, TargetSystemConfigProperties> targetSystems;

    @Override
    public URI getUri(TargetSystem targetSystem) {
        TargetSystemConfigProperties configProperties = getTargetSystemConfigProperties(targetSystem);
        return configProperties.uri;
    }

    @NonNull
    private TargetSystemConfigProperties getTargetSystemConfigProperties(TargetSystem targetSystem) {
        var configProperties = targetSystems.get(targetSystem);
        if(configProperties == null){
            throw new IllegalStateException("TargetSystem " + targetSystem +" not configured");
        }
        return configProperties;
    }
}
