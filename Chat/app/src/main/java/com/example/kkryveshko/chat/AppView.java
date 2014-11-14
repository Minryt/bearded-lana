package com.example.kkryveshko.chat;

import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Konstantin on 13.11.2014.
 */
public class AppView implements Runnable {

    private static final int LOG_LINES = 10;
    private static AppView appView;
    private TextView eventLog;
    private Button buttonServer;
    private Button buttonClient;
    private String logText = "...\n";
    private MainActivity activity;

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

    @Override
    public void run() {
        Model model = Model.getInstance();
        while (model.isRunning()) {
            try {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        eventLog.setText(logText);
                    }
                });
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setActivity(MainActivity mainActivity) {
        activity = mainActivity;
    }

    public void createServer() {
        buttonServer.setEnabled(false);
        buttonServer.setText("Wait for client");
        buttonClient.setText("Cancel");
    }

    public void createClient() {
        buttonClient.setEnabled(false);
        buttonClient.setText("Find server");
        buttonServer.setText("Cancel");
    }

    public void resetButtons() {
        buttonClient.setEnabled(true);
        buttonServer.setEnabled(true);
        buttonClient.setText("Create Client");
        buttonServer.setText("Create Server");
    }

    public void print(String text) {
        logText = prepareToPrint(logText + text + "\n");
    }

    private String prepareToPrint(String text) {
        String[] lines = text.split("\r\n|\r|\n");
        String outputText = text;
        if (lines.length > LOG_LINES) {
            outputText = "";
            for (int i = 1; i < lines.length; i++) {
                outputText += lines[i] + "\n";
            }
        }
        return outputText;
    }
}