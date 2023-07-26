package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.in.user;

import com.comsysto.trainings.springtrainingeon.fluxapp.port.security.out.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserProvider userProvider;

    @GetMapping
    public Mono<UserResponse> getCurrentUser() {
        return userProvider.currentUser()
                .map(UserResponse::new);
    }

}
