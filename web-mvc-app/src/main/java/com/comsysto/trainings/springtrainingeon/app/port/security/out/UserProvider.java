package com.comsysto.trainings.springtrainingeon.app.port.security.out;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;

public interface UserProvider {
    User currentUser();
}
