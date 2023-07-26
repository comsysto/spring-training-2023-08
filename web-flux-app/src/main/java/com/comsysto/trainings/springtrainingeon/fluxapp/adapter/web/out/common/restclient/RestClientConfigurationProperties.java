package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Value()
@AllArgsConstructor(onConstructor_ = @ConstructorBinding)
@ConfigurationProperties("rest-client")
@Validated
public class RestClientConfigurationProperties {
    @Valid @NotNull
    Map<TargetSystem, SystemSettings> targetSystems;

    @Value
    public static class SystemSettings {
        @NotNull
        URI baseUri;
        BasicAuth basicAuth;
        Boolean forwardToken;
    }

    @Value
    public static class BasicAuth {
        @NotNull @NotBlank
        String user;

        @NotNull @NotBlank
        String password;
    }

    @Size(max = 0)
    public List<TargetSystem> getMissingTargetSystems() {
        if(targetSystems == null){
            return Collections.emptyList();
        }
        return Arrays.stream(TargetSystem.values())
                .filter(t -> !targetSystems.containsKey(t))
                .toList();

    }
}


