package com.comsysto.trainings.springtrainingeon.app.adapter.context.out;

import org.springframework.stereotype.Component;

import com.comsysto.trainings.springtrainingeon.app.port.context.out.Context;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextRepository;

@Component
public class ThreadContextRepository implements ContextRepository
{
	private final ThreadLocal<Context> threadLocal = new ThreadLocal<>();

	@Override
	public Context getContext()
	{
		return threadLocal.get();
	}

	@Override
	public void setContext(Context context)
	{
		threadLocal.set(context);
	}

	@Override
	public void reset()
	{
		threadLocal.remove();
	}
}
