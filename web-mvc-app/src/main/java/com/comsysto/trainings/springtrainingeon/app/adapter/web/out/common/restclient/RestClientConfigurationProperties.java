package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Value()
@AllArgsConstructor(onConstructor_ = @ConstructorBinding)
@ConfigurationProperties("rest-client")
@Validated
public class RestClientConfigurationProperties implements RestClientConfiguration {
    @Valid @NotNull
    Map<TargetSystem, SystemSettings> targetSystems;

    @Override
    public RestClientConfiguration.SystemSettings getSettingsFor(TargetSystem targetSystem) {
        return Optional.ofNullable(targetSystems.get(targetSystem))
                .map(systemSettings ->
                        new RestClientConfiguration.SystemSettings(
                                systemSettings.baseUri,
                                Optional.ofNullable(systemSettings.basicAuth).map(auth ->
                                        new RestClientConfiguration.BasicAuth(auth.user, auth.password)
                                ),
                                systemSettings.forwardToken == Boolean.TRUE
                        )
                )
                .orElseThrow(() -> new IllegalStateException("No configuration for target system found! targetSystem=" + targetSystem));
    }

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
        if (targetSystems == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(TargetSystem.values())
                .filter(t -> !targetSystems.containsKey(t))
                .toList();

    }
}


