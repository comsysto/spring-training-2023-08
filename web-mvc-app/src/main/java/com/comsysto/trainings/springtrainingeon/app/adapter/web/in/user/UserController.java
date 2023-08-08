package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;
import com.comsysto.trainings.springtrainingeon.app.port.security.out.UserProvider;

import lombok.RequiredArgsConstructor;


@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserProvider userProvider;

//    @HasAuthoritySpringA
    @GetMapping
    public User getCurrentUser() {
        return userProvider.currentUser();
    }

}
