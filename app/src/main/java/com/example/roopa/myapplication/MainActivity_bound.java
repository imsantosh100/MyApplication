        package com.example.roopa.myapplication;

        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.content.ServiceConnection;
        import android.os.Bundle;
        import android.os.IBinder;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.TextView;



public class MainActivity_bound extends AppCompatActivity {
    BoundService mBoundService;
    boolean mServiceBound = false;

    private static String LOG_TAG = "MainActivity_bound";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bound);
        final TextView timestampText = (TextView) findViewById(R.id.timestamp_text);
        Button printTimestampButton = (Button) findViewById(R.id.print_timestamp);
        Button stopServiceButon = (Button) findViewById(R.id.stop_service);

        printTimestampButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound) {
                    timestampText.setText(mBoundService.getTimestamp());
                }
            }
        });

        stopServiceButon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                }
                Intent intent = new Intent(MainActivity_bound.this,
                        BoundService.class);
                stopService(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG,"onStart()");
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;

            Log.d(LOG_TAG,"onStop()");
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
            Log.d(LOG_TAG,"onServiceDisconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(LOG_TAG,"onServiceConnected");
            BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }
    };
}
