package com.comsysto.trainings.springtrainingeon.app.port.context.out;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.common.BearerToken;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.common.RequestId;
import lombok.Value;

import java.util.Optional;
@Value
public class Context {
    RequestId requestId;
    Optional<BearerToken> token;
}
