package com.comsysto.trainings.springtrainingeon.app.port.security.out;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;
import reactor.core.publisher.Mono;

public interface UserProvider {
    User currentUser();
}
