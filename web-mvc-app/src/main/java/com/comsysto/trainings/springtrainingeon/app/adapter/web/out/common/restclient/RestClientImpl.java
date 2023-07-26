package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.common.RequestId;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.exceptions.DeserializationFailedRestClientException;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.exceptions.EncounteredRedirectsRestClientException;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.exceptions.InvalidResponseRestClientException;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Validator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
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

import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class RestClientImpl implements com.comsysto.trainings.springtrainingeon.app.port.web.out.common.restclient.RestClient {
    private final ObjectMapper objectMapper;
    private final Validator validator;

    private final RestErrorHandler restErrorMapper;
    private final RestClientConfiguration configuration;
    private Map<TargetSystem, WebClient> webClients;
    private final ContextProvider contextProvider;

    public RestClientImpl(RestClientConfiguration configuration, ObjectMapper objectMapper, Validator validator, RestErrorHandler restErrorHandler, ContextProvider contextProvider) {
        this.configuration = configuration;
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.restErrorMapper = restErrorHandler;
        this.contextProvider = contextProvider;
    }


    @PostConstruct
    public void initWebClients() {
        this.webClients = Arrays.stream(TargetSystem.values())
                .collect(
                        toMap(
                                Function.identity(),
                                targetSystem -> {
                                    var systemSettings = configuration.getSettingsFor(targetSystem);
                                    return WebClient.create(systemSettings.getBaseUri().toString());
                                }
                        )
                );
    }

    @Override
    public RestCallImpl get(TargetSystem targetSystem, String path) {
        var webClient = webClients.get(targetSystem);
        var systemSettings = this.configuration.getSettingsFor(targetSystem);
        return new RestCallImpl(webClient, systemSettings, targetSystem, HttpMethod.GET, path, null);
    }

    @Override
    public RestCallImpl post(TargetSystem targetSystem, String path, Object body) {
        var webClient = webClients.get(targetSystem);
        var systemSettings = this.configuration.getSettingsFor(targetSystem);
        return new RestCallImpl(webClient, systemSettings, targetSystem, HttpMethod.GET, path, body);
    }

    @RequiredArgsConstructor
    @ToString
    public class RestCallImpl implements RestCall {
        @NonNull
        private final WebClient webClient;
        @NonNull
        private final RestClientConfiguration.SystemSettings systemSettings;
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
        public <T> T exchangeJsonAndMapTo(Class<T> responseType) {
            return retrieve()
                    .bodyToMono(String.class)
                    .map(body -> parseAndValidateBody(responseType, body))
                    .onErrorMap(t -> restErrorMapper.mapErrors(toParams(), t))

                    .block();
        }

        @Override
        public <T> Optional<T> exchangeJsonAndMapToOptional(Class<T> responseType) {
            return retrieve().bodyToMono(String.class)
                    .map(body1 -> Optional.of(parseAndValidateBody(responseType, body1)))
                    .onErrorResume(WebClientResponseException.NotFound.class, (e) -> Mono.just(Optional.empty()))
                    .onErrorMap(t -> restErrorMapper.mapErrors(toParams(), t))
                    .block();
        }

        @Override
        public void exchange() {
            retrieve().toBodilessEntity()
                    .onErrorMap(t -> restErrorMapper.mapErrors(toParams(), t))
                    .mapNotNull(b -> null)
                    .block();
        }


        private WebClient.ResponseSpec retrieve() {
            var context = contextProvider.getContext();
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
                        basicAuth.ifPresent(auth ->
                            h.setBasicAuth(auth.getUser(), auth.getPassword())
                        );
                        context.getToken().ifPresent(token -> {
                            h.setBearerAuth(token.getValue());
                        });
                        RequestId requestId = context.getRequestId();
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


