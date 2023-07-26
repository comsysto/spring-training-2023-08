package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.common.BearerToken;
import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.common.RequestId;
import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.exceptions.DeserializationFailedRestClientException;
import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.exceptions.EncounteredRedirectsRestClientException;
import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.exceptions.InvalidResponseRestClientException;
import com.comsysto.trainings.springtrainingeon.fluxapp.port.web.out.common.restclient.RestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Validator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class RestClientImpl implements RestClient {
    private final ObjectMapper objectMapper;
    private final Validator validator;

    private final RestErrorHandler restErrorMapper;
    private Map<TargetSystem, WebClient> webClients;
    private final Map<TargetSystem, RestClientConfigurationProperties.SystemSettings> systemSettings;

    public RestClientImpl(RestClientConfigurationProperties configurationProperties, ObjectMapper objectMapper, Validator validator, RestErrorHandler restErrorHandler) {
        this.systemSettings = configurationProperties.getTargetSystems();
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.restErrorMapper = restErrorHandler;
    }

    @PostConstruct
    public void initWebClients() {
        this.webClients = systemSettings
                .entrySet()
                .stream()
                .collect(
                        toMap(
                                Map.Entry::getKey,
                                settings ->
                                        WebClient.create(settings.getValue().getBaseUri().toString())
                        )
                );
    }

    @Override
    public RestCallImpl get(TargetSystem targetSystem, String path) {
        var webClient = webClients.get(targetSystem);
        var systemSettings = this.systemSettings.get(targetSystem);
        return new RestCallImpl(webClient, systemSettings, targetSystem, HttpMethod.GET, path, null);
    }

    @Override
    public RestCallImpl post(TargetSystem targetSystem, String path, Object body) {
        var webClient = webClients.get(targetSystem);
        var systemSettings = this.systemSettings.get(targetSystem);
        return new RestCallImpl(webClient, systemSettings, targetSystem, HttpMethod.GET, path, body);
    }

    @RequiredArgsConstructor
    @ToString
    public class RestCallImpl implements RestCall {
        @NonNull
        private final WebClient webClient;
        @NonNull
        private final RestClientConfigurationProperties.SystemSettings systemSettings;
        @NonNull
        private final TargetSystem targetSystem;
        @NonNull
        private final HttpMethod method;
        @NonNull
        private final String path;
        private final Object body;

        final Map<String, Object> pathVariables = new HashMap<>();
        final Map<String, Object> queryParameter = new HashMap<>();
        final HttpHeaders headers = new HttpHeaders();

        public RestCallImpl headers(Consumer<HttpHeaders> httpConfigurer) {
            httpConfigurer.accept(headers);
            return this;
        }

        @Override
        public RestCallImpl pathVariable(String name, Object value) {
            pathVariables.put(name, value);
            return this;
        }

        @Override
        public RestCallImpl queryParam(String name, Object value) {
            queryParameter.put(name, value);
            return this;
        }


        @Override
        public <T> Mono<T> exchangeJsonAndMapTo(Class<T> responseType) {
            return retrieve().flatMap(responseSpec ->
                    responseSpec
                            .bodyToMono(String.class)
                            .map(body -> parseAndValidateBody(responseType, body))
                            .onErrorMap(t -> restErrorMapper.mapErrors(toParams(), t))

            );
        }

        @Override
        public <T> Mono<Optional<T>> exchangeJsonAndMapToOptional(Class<T> responseType) {
            return retrieve().flatMap(responseSpec ->
                    responseSpec
                            .bodyToMono(String.class)
                            .map(body1 -> Optional.of(parseAndValidateBody(responseType, body1)))
                            .onErrorResume(WebClientResponseException.NotFound.class, (e) -> Mono.just(Optional.empty()))
                            .onErrorMap(t -> restErrorMapper.mapErrors(toParams(), t))
            );
        }

        @Override
        public Mono<Void> exchange() {
            return retrieve().flatMap(responseSpec ->
                    responseSpec.toBodilessEntity()
                            .onErrorMap(t -> restErrorMapper.mapErrors(toParams(), t))
                            .mapNotNull(b -> null)
            );
        }

        private Mono<WebClient.ResponseSpec> retrieve() {
            return Mono.deferContextual(context ->
                    Mono.just(retrieveWithContext(context))
            );
        }

        private WebClient.ResponseSpec retrieveWithContext(ContextView context) {
            var requestBodySpec = webClient
                    .method(method)
                    .uri(path, pathVariables)
                    .headers(h -> {
                        h.addAll(headers);
                        if (h.getContentType() == null) {
                            h.setContentType(MediaType.APPLICATION_JSON);
                        }
                        if (h.getAccept().isEmpty()) {
                            h.setAccept(List.of(MediaType.APPLICATION_JSON));
                        }
                        var basicAuth = systemSettings.getBasicAuth();
                        if (basicAuth != null) {
                            h.setBasicAuth(basicAuth.getUser(), basicAuth.getPassword());
                        }
                        if (context.hasKey(BearerToken.class) && systemSettings.getForwardToken() == Boolean.TRUE) {
                            h.setBearerAuth(context.get(BearerToken.class).getValue());
                        }
                        RequestId requestId = context.get(RequestId.class);
                        h.set(RequestId.HEADER_NAME, requestId.getValue());
                    });

            if (body != null) {
                requestBodySpec.bodyValue(body);
            }

            return requestBodySpec.retrieve()
                    .onStatus(HttpStatusCode::is3xxRedirection, (c) ->
                            Mono.error(
                                    new EncounteredRedirectsRestClientException(
                                            toParams(),
                                            c.statusCode(),
                                            c.headers().asHttpHeaders().getFirst("Location"),
                                            "")
                            )
                    );
        }

        private <T> T parseAndValidateBody(Class<T> responseType, String body) {
            T result;
            try {
                result = objectMapper.readValue(body, responseType);
            } catch (JsonProcessingException e) {
                throw new DeserializationFailedRestClientException(toParams(), responseType, body, e);
            }

            var violations = validator.validate(result);
            if (!violations.isEmpty()) {
                throw new InvalidResponseRestClientException(toParams(), result, violations, body);
            }
            return result;
        }


        private RestCallParams toParams() {
            return new RestCallParams(
                    targetSystem,
                    method,
                    path,
                    body,
                    Map.copyOf(pathVariables),
                    Map.copyOf(queryParameter),
                    Map.copyOf(headers)
            );
        }

    }


}


