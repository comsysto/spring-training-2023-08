package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.user;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;
import com.comsysto.trainings.springtrainingeon.app.domain.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public UserResponse getCurrentUser() {
        return new UserResponse(new User(new UserId("some-id"), "some-first-name", "some-last-name"));

    }

}
