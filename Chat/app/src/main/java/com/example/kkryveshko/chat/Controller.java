package com.example.kkryveshko.chat;

import android.view.View;
import android.widget.Button;

/**
 * Created by Konstantin on 13.11.2014.
 */
public class Controller {

    private static Controller controller;
    private AppView appView;
    private Model model;
    private Button buttonServer;
    private Button buttonClient;

    public static Controller getInstance() {
        if (controller == null) {
            return controller = new Controller();
        }
        return controller;
    }

    public void init() {
        appView = AppView.getInstance();
        model = Model.getInstance();
    }

    public void createServerButton(Button buttonServer) {
        this.buttonServer = buttonServer;
        this.buttonServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (model.getState()) {
                    case START:
                        model.createServer();
                        appView.createServer();
                        break;
//                    case WAIT_CLIENT:
//                        break;
                    case FIND_SERVER:
                        model.resetState();
                        appView.resetButtons();
                        break;
                }
            }
        });
    }


    public void createClientButton(Button buttonClient) {
        this.buttonClient = buttonClient;
        this.buttonClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (model.getState()) {
                    case START:
                        model.createClient();
                        appView.createClient();
                        break;
                    case WAIT_CLIENT:
                        model.resetState();
                        appView.resetButtons();
                        break;
//                    case FIND_SERVER:
//                        break;
                }
            }
        });
    }
}
