package com.kieronquinn.app.amazfitinternetcompanion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.kieronquinn.library.amazfitcommunication.Transporter;
import com.kieronquinn.library.amazfitcommunication.internet.HTTPRequestResponder;
import com.kieronquinn.library.amazfitcommunication.location.LocationResponder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Kieron on 08/04/2018.
 */

public class ForegroundService extends Service {
    private Transporter transporter;
    private HTTPRequestResponder internetResponder;

    @Override
    public void onCreate() {
        super.onCreate();
        int notificationId = 1;
        String channelId = "foreground_service";
        CharSequence name = getString(R.string.channel_name);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notif))
                .setSmallIcon(R.drawable.ic_stat_watch_internet)
                .setChannelId(channelId)
                .build();
        notificationManager.notify(notificationId, notification);
        startForeground(notificationId, notification);

        transporter = Transporter.get(getApplicationContext(), getPackageName());

        internetResponder = new HTTPRequestResponder(transporter);
        transporter.addDataListener(internetResponder);

        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        LocationResponder locationResponder = new LocationResponder(transporter, locationManager, Looper.myLooper());
        transporter.addChannelListener(locationResponder);
        transporter.addDataListener(locationResponder);

        transporter.connectTransportService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
