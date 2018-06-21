package com.kieronquinn.app.amazfitinternetcompanion;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                // TODO: Consider calling
//                //    Activity#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for Activity#requestPermissions for more details.
//                return;
//            }
        }

        stopService(new Intent(this, ForegroundService.class));
        startService(new Intent(this, ForegroundService.class));
    }

    public void hide(View view) {
        PackageManager pm = getApplicationContext().getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(getPackageName(), "com.kieronquinn.app.amazfitinternetcompanion.LaunchActivity"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        Toast.makeText(this, getString(R.string.hidden), Toast.LENGTH_LONG).show();
    }

    public void show(View view) {
        PackageManager pm = getApplicationContext().getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(getPackageName(), "com.kieronquinn.app.amazfitinternetcompanion.LaunchActivity"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Toast.makeText(this, getString(R.string.shown), Toast.LENGTH_LONG).show();
    }

    public void restart(View view) {
        stopService(new Intent(this, ForegroundService.class));
        startService(new Intent(this, ForegroundService.class));
        Toast.makeText(this, getString(R.string.done), Toast.LENGTH_LONG).show();
    }
}
