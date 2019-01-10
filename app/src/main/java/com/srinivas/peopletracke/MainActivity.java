package com.srinivas.peopletracke;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.srinivas.peopletracke.Helper.DBHelper;
import com.srinivas.peopletracke.TrackLog.TrackLog;

public class MainActivity extends AppCompatActivity {
    Intent mServiceIntent;
    SensorService mSensorService;

    Button notification_btn;
    SharedPreferences ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  ActivityCompat.finishAffinity(MainActivity.this);
        notification_btn = findViewById(R.id.notification_btn);
        mSensorService = new SensorService(MainActivity.this);
        mServiceIntent = new Intent(MainActivity.this, mSensorService.getClass());
        // mServiceIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        ss = getSharedPreferences("status", MODE_PRIVATE);
        givepermissionaccess();
       /* if (ss.getString("status", "").equals("destroy")) {
            startService(mServiceIntent);
        }*/
      /*  if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }*/
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent track = new Intent(MainActivity.this, TrackLog.class);
                startActivity(track);
            }
        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    @Override
    protected void onDestroy() {
        // stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        /*Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);*/
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            //resume tasks needing this permission
            // givepermissionaccess();
            //  Toast.makeText(getBaseContext(), "Resume Task needing this permission", Toast.LENGTH_SHORT).show();
            if (!isMyServiceRunning(mSensorService.getClass())) {
                startService(mServiceIntent);
            }
        } else {
            //finish();
            Toast.makeText(getBaseContext(), "you can not use this application without givivng access to ur location Thanks!!", Toast.LENGTH_SHORT).show();

           /* if (ss.getString("status", "").equals("start")) {
                startService(mServiceIntent);
            }*/
        }
    }

    public void givepermissionaccess() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WAKE_LOCK}, 0);
        } else {
            Toast.makeText(getBaseContext(), "All permissions granted.", Toast.LENGTH_SHORT).show();
//            givepermissionaccess();
           /* if (ss.getString("status", "").equals("start")) {
                startService(mServiceIntent);
            }*/
            if (!isMyServiceRunning(mSensorService.getClass())) {
                startService(mServiceIntent);
            }
        }

    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        super.onPause();
    }
}
