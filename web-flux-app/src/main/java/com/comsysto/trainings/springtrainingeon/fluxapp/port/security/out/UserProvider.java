package com.comsysto.trainings.springtrainingeon.fluxapp.port.security.out;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.user.User;
import reactor.core.publisher.Mono;

public interface UserProvider {
    Mono<User> currentUser();
}
