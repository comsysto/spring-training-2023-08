package com.comsysto.trainings.springtrainingeon.fluxapp.locallauncher;

import com.comsysto.trainings.springtrainingeon.fluxapp.FluxSpringTrainingEonApplication;

public class LocalLauncher {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local");
        FluxSpringTrainingEonApplication.main(args);
    }
}
