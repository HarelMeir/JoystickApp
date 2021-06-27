package com.example.joystickapp.viewModel;
import com.example.joystickapp.model.Model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VM {
    private Model model;

    public VM() {
        this.model = new Model();
    }

    public void connect(String ip, int port) {
        model.connect(ip, port);
    }

    public void updateRudder(float currentValue) {
        model.updateRudder(currentValue);
    }

    public void updateThrottle(float currentValue) {
        model.updateThrottle(currentValue);
    }

    public void updateAileron(float currentValue) {
        model.updateAileron(currentValue);
    }

    public void updateElevator(float currentValue) {
        model.updateElevator(currentValue);

    }
}
