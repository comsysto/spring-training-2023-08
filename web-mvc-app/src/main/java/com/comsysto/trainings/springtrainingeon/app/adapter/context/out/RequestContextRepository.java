package com.comsysto.trainings.springtrainingeon.app.adapter.context.out;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.comsysto.trainings.springtrainingeon.app.port.context.out.Context;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextRepository;

@Component
@RequestScope
public class RequestContextRepository implements ContextRepository
{
	private Context context;

	@Override
	public Context getContext()
	{
		return context;
	}

	@Override
	public void setContext(Context context)
	{
		this.context = context;
	}

	@Override
	public void reset()
	{
		context = null;
	}
}
