package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient;

import lombok.NonNull;
import lombok.Value;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

@Value
public class RestCallParams {

    @NonNull
    TargetSystem targetSystem;

    @NonNull
    HttpMethod method;

    @NonNull
    String path;

    Object body;

    @NonNull
    Map<String, Object> pathVariables;

    @NonNull
    Map<String, Object> queryParameter;

    @NonNull
    Map<String, List<String>> header;
}
