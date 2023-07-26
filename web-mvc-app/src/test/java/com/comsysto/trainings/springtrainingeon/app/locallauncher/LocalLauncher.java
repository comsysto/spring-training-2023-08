package com.comsysto.trainings.springtrainingeon.app.locallauncher;

import com.comsysto.trainings.springtrainingeon.app.MvcSpringTrainingEonApplication;

public class LocalLauncher {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local");
        MvcSpringTrainingEonApplication.main(args);
    }
}
