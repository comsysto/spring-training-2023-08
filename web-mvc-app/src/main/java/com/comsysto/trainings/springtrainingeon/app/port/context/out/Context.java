package com.comsysto.trainings.springtrainingeon.app.port.context.out;

import lombok.Value;

@Value
public class Context {
    RequestId requestId;
    BearerToken bearerToken;
}
