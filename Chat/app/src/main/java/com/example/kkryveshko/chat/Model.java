package com.example.kkryveshko.chat;

import android.widget.EditText;

/**
 * Created by Konstantin on 12.11.2014.
 */
public class Model implements Runnable{

    private static Model model;

    public boolean isRunning() {
        return isRunning;
    }

    private boolean isRunning;
    private AppView view;

    public static Model getInstance() {
        if (model == null) {
            return model = new Model();
        }
        return model;
    }

    @Override
    public void run() {
        view = AppView.getInstance();

        while(isRunning) {
            try {
                Thread.sleep(1000);
                view.print("tik");
            } catch (Exception e) {

            }
        }
    }

    public boolean createServer() {
        //console.setText("Server");
        return false;
    }

    public boolean createClient() {
        //console.setText("Client");
        return false;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
