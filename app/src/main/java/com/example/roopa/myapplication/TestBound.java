package com.example.roopa.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by roopa on 5/4/17.
 */

public class TestBound extends AppCompatActivity {


    boolean mBound_flag = false;
    BoundService boundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bound);

        final TextView timestampText = (TextView) findViewById(R.id.timestamp_text);
        Button printTimestampButton = (Button) findViewById(R.id.print_timestamp);
        Button stopServiceButon = (Button) findViewById(R.id.stop_service);

        printTimestampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBound_flag)
                {
                    timestampText.setText(boundService.getTimestamp());
                }
              //  mBound_flag = true;
            }
        });

        stopServiceButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound_flag)
                {
                    unbindService(serviceConnection);
                    mBound_flag = false;
                }
                Intent i = new Intent(TestBound.this,BoundService.class);
                stopService(i);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent i = new Intent(this,BoundService.class);
        startService(i);
        bindService(i,serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mBound_flag)
            unbindService(serviceConnection);
        mBound_flag = false;

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            BoundService.MyBinder conn_boundService = (BoundService.MyBinder) service;
            boundService = conn_boundService.getService();
            mBound_flag = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            mBound_flag = false;

        }
    };
}
