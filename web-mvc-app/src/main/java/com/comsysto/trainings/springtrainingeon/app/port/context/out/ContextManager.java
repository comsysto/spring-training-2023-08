package com.comsysto.trainings.springtrainingeon.app.port.context.out;

public interface ContextManager extends ContextProvider {

    void setContext(Context context);

    void reset();
}
