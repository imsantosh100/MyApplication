package com.example.roopa.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    Button b1, b2, b3;
    TextView textView;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this is a commit

        b1 = (Button) findViewById(R.id.btn_start);
        b2 = (Button) findViewById(R.id.btn_stop);
        b3 = (Button) findViewById(R.id.button2);

        textView = (TextView)findViewById(R.id.text1);

        i = new Intent(getBaseContext(),MyService.class);

    }

    public void start_service(View v) {

        startService(i);

   }

    public void stop_service(View v) {
        stopService(i);
    }

    public void start_bound(View v)
    {
       // startActivity(new Intent(this,TestBound.class));
        Intent i = new Intent();
        i.setAction("implicit.intent.example");
        i.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(i);
    }

    private void test()
    {

    }



}


