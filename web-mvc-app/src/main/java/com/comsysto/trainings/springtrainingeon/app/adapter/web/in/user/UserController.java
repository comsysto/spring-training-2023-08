package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.user;

import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;
import com.comsysto.trainings.springtrainingeon.app.port.security.out.UserProvider;

@RequestMapping("user")
@RestController
public class UserController
{

	private Resource applicationContext;
	private final UserProvider userProvider;

	public UserController(@Lazy UserProvider userProvider, @Value("classpath:application.yaml") Resource applicationContext)
	{
		this.userProvider = userProvider;
		this.applicationContext = applicationContext;
	}

	//    @HasAuthoritySpringA
	@GetMapping
	public User getCurrentUser()
	{
		return userProvider.currentUser();
	}

}
