package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import lombok.Value;

public interface RestClientConfiguration {

    SystemSettings getSettingsFor(TargetSystem targetSystem);

    @Value
    class SystemSettings {
        URI baseUri;
        Optional<BasicAuth> basicAuth;
        boolean forwardToken;
    }

    @Value
    class BasicAuth {
        String user;
        String password;
    }

}
