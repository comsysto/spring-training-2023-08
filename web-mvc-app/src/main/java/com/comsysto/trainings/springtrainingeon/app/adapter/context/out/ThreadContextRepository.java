package com.comsysto.trainings.springtrainingeon.app.adapter.context.out;

import com.comsysto.trainings.springtrainingeon.app.port.context.out.Context;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextRepository;

public class ThreadContextRepository implements ContextRepository {

    private final ThreadLocal<Context> threadLocal = new ThreadLocal<Context>();
    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void setContext(Context context) {
    }

    @Override
    public void reset() {
    }
}
