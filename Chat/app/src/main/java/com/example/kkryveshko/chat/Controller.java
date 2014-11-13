package com.example.kkryveshko.chat;

import android.view.View;
import android.widget.Button;

/**
 * Created by Konstantin on 13.11.2014.
 */
public class Controller {

    private static Controller controller;
    private Button buttonServer;
    private Button buttonClient;

    public static Controller getInstance() {
        if (controller == null) {
            return controller = new Controller();
        }
        return controller;
    }

    public void createServerButton(Button buttonServer) {
        this.buttonServer = buttonServer;
        this.buttonServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppView.getInstance().print("Server button");
            }
        });
    }


    public void createClientButton(Button buttonClient) {
        this.buttonClient = buttonClient;
        this.buttonClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppView.getInstance().print("Client button");
            }
        });
    }
}
