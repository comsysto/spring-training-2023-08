package com.comsysto.trainings.springtrainingeon.app.port.web.out.common.restclient;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.RestClientImpl;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.TargetSystem;

public interface RestClient {
    RestClientImpl.RestCallImpl get(TargetSystem targetSystem, String path);

    RestClientImpl.RestCallImpl post(TargetSystem targetSystem, String path, Object body);
}
