package com.example.kkryveshko.chat;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Konstantin on 12.11.2014.
 */
public class Core implements Runnable{

    private static Core core;
    private EditText console;

    public static Core getInstance() {
        if (core == null) {
            return core = new Core();
        }
        return core;
    }

    @Override
    public void run() {

    }

    public boolean createServer() {
        console.setText("Server");
        return false;
    }

    public boolean createClient() {
        console.setText("Client");
        return false;
    }

    public void setOutputConsole(EditText editText) {
        this.console = editText;
    }
}
