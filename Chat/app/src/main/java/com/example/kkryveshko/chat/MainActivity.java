package com.example.kkryveshko.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private Model model = Model.getInstance();
    private Controller controller = Controller.getInstance();
    private AppView appView = AppView.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

        }
        setViewElements();
        startProgram();
        initController();
    }

    private void startProgram() {
        Model.getInstance().setIsRunning(true);
        new Thread(model).start();
        new Thread(appView).start();
    }

    private void setViewElements() {
        appView.setEvenLogOutput((TextView) findViewById(R.id.textView));
        appView.setButtons((Button) findViewById(R.id.buttonServer), (Button) findViewById(R.id.buttonClient));
        appView.setActivity(this);
    }

    private void initController() {
        controller.init();
        controller.createServerButton((Button) findViewById(R.id.buttonServer));
        controller.createClientButton((Button) findViewById(R.id.buttonClient));
    }

    @Override
    protected void onStop() {
        model.setIsRunning(false);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up buttonServer, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
