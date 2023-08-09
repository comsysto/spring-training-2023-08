package com.comsysto.trainings.springtrainingeon.app.port.context.out;

public interface ContextRepository {

    Context getContext();
    void setContext(Context context);
    void reset();

}
