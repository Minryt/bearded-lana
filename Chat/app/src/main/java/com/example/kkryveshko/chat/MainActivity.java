package com.example.kkryveshko.chat;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private TextView console;
    private Button createServer;
    private Button createClient;
    private Core core = Core.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

        }

        console = (TextView) findViewById(R.id.textView);
        createServer = (Button) findViewById(R.id.buttonServer);
        createClient = (Button) findViewById(R.id.buttonClient);

        console.setMovementMethod(new ScrollingMovementMethod());
        createServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                console.append("\nServer");
            }
        });

        createClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                console.append("\nClient");
            }
        });

        Thread mainThread = new Thread(core);
        mainThread.start();
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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
