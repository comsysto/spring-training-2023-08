package com.comsysto.trainings.springtrainingeon.app.domain.user;

import lombok.Value;
import lombok.With;

@Value
@With
public class User {
    UserId id;
    String firstName;
    String lastName;
}
