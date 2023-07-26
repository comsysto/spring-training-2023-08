package com.comsysto.trainings.springtrainingeon.app.adapter.context.out;

import com.comsysto.trainings.springtrainingeon.app.port.context.out.Context;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextManager;
import org.springframework.stereotype.Component;

@Component
public class DefaultContextManager implements ContextManager {

    private final ThreadLocal<Context> threadLocal = new ThreadLocal<>();
    @Override
    public void setContext(Context context) {
        threadLocal.set(context);
    }

    @Override
    public void reset() {
        threadLocal.remove();
    }

    @Override
    public Context getContext() {
        var context = threadLocal.get();
        if(context == null){
            throw new IllegalStateException("no context set");
        }
        return context;
    }
}
