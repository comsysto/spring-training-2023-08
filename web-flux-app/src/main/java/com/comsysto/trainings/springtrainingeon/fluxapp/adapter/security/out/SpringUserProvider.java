package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.security.out;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.user.User;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.user.UserId;
import com.comsysto.trainings.springtrainingeon.fluxapp.port.security.out.UserProvider;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class SpringUserProvider implements UserProvider {
    @Override
    public Mono<User> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(auth -> {
                    var user = (OidcUser) auth.getPrincipal();
                    return new User(
                            new UserId(user.getPreferredUsername()),
                            user.getGivenName(),
                            user.getFamilyName()
                    );
                });
    }
}
