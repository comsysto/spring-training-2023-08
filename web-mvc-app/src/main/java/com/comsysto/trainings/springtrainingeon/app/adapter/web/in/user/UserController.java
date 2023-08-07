package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.user;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;
import com.comsysto.trainings.springtrainingeon.app.domain.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public Authentication getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;

    }

}
