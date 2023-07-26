package com.comsysto.trainings.springtrainingeon.app.adapter.security.out;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;
import com.comsysto.trainings.springtrainingeon.app.domain.user.UserId;
import com.comsysto.trainings.springtrainingeon.app.port.security.out.UserProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class SpringUserProvider implements UserProvider {
    @Override
    public User currentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (OidcUser) authentication.getPrincipal();
        return new User(
                new UserId(user.getPreferredUsername()),
                user.getGivenName(),
                user.getFamilyName()
        );

    }
}
