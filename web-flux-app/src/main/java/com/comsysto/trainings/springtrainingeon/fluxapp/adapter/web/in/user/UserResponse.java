package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.in.user;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.user.User;
import lombok.Value;

@Value
public class UserResponse {

    public UserResponse(User user){
        this.id = user.getId().getValue();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    String id;
    String firstName;
    String lastName;
}
