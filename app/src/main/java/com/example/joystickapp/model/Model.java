package com.example.joystickapp.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    private boolean isConnected = false;
    private Socket fg;
    private PrintWriter out;
    private final String throttle = "set /controls/engines/current-engine/throttle ";
    private final String att = "set /controls/flight/";

    public Model() {}

    public void connect(String ip, int port) {
        Thread t = new Thread(()-> {
            try {
                fg = new Socket(ip, port);
                out = new PrintWriter(fg.getOutputStream(), true);
                isConnected = true;
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
        try {
            t.join();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void sendCurrentValue(String message) {
        Thread sender = new Thread(()-> {
           try {
               out.print(message);
               out.flush();
           } catch (Exception e) {
               e.printStackTrace();
           }
        });
        sender.start();
        try {
            sender.join();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void updateRudder(float currentValue) {
        sendCurrentValue(this.att + "rudder " + currentValue + "\r\n");
    }

    public void updateThrottle(float currentValue) {
        sendCurrentValue(this.throttle + currentValue + "\r\n");
    }
    public void updateAileron(float currentValue) {
        sendCurrentValue(this.att + "aileron " + currentValue + "\r\n");
    }

    public void updateElevator(float currentValue) {
        sendCurrentValue(this.att + "elevator" + currentValue + "\r\n");
    }



}
