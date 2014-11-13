package com.example.kkryveshko.chat;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Konstantin on 13.11.2014.
 */
public class AppView {

    private static AppView appView;

    private TextView eventLog;
    private Button buttonServer;
    private Button buttonClient;

    public static AppView getInstance() {
        if (appView == null) {
            return appView = new AppView();
        }
        return appView;
    }

    public void setEvenLogOutput(TextView view) {
        eventLog = view;
        eventLog.setMovementMethod(new ScrollingMovementMethod());
    }

    public void setButtons(Button server, Button client) {
        buttonServer = server;
        buttonClient = client;
    }

    public void print(String text) {
        eventLog.append("\n" + text);
    }
}
