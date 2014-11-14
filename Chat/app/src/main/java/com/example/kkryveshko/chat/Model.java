package com.example.kkryveshko.chat;

/**
 * Created by Konstantin on 12.11.2014.
 */
public class Model implements Runnable {

    private static Model model;
    private boolean isRunning;
    private AppView view;
    private ProgramState state;

    public static Model getInstance() {
        if (model == null) {
            return model = new Model();
        }
        return model;
    }

    @Override
    public void run() {
        view = AppView.getInstance();
        state = ProgramState.START;

        while (isRunning) {
            try {
                Thread.sleep(500);
                process();
            } catch (Exception e) {

            }
        }
    }

    public ProgramState getState() {
        return state;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void createServer() {
        state = ProgramState.CREATE_SERVER;
    }

    public void createClient() {
        state = ProgramState.CREATE_CLIENT;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void resetState() {
        state = ProgramState.START;
    }

    private void process() {
        switch (state) {
            case START:
                view.print("ready");
                break;
            case CREATE_SERVER:
                view.print("create server start");
                state = ProgramState.WAIT_CLIENT;
                break;
            case CREATE_CLIENT:
                view.print("create client start");
                state = ProgramState.FIND_SERVER;
                break;
            case WAIT_CLIENT:
                view.print("wait client");
                break;
            case FIND_SERVER:
                view.print("find server");
                break;
        }
    }
}
