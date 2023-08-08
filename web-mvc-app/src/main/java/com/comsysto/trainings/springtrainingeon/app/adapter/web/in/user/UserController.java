package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.user;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;
import com.comsysto.trainings.springtrainingeon.app.domain.user.UserId;
import com.comsysto.trainings.springtrainingeon.app.port.security.out.HasAuthoritySpringA;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {

    @HasAuthoritySpringA
    @GetMapping
    public Authentication getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;

    }

}
