package com.example.infinite.widgettask;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by inci-work-pc-4 on 7/2/18.
 */

public class MyService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        buildUpdate();

        return super.onStartCommand(intent, flags, startId);
    }

    private void buildUpdate() {

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.collection_widget);

        String day = "", hours = "", minutes = "";

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        String times = pref.getString("dateTime", null);

        String lastUpdated = DateFormat.format("yyyy-MM-dd HH:mm", new Date()).toString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date1 = null;
        Date date2 = null;

        try {

            date1 = format.parse(lastUpdated);
            date2 = format.parse(times);

            long diff = date2.getTime() - date1.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);


            if (diffDays > 0) {
                day = String.valueOf(diffDays) + " : ";
            }

            if (diffHours > 0) {
                hours = String.valueOf(diffHours) + " : ";
            }

            if (diffMinutes > 0) {
                minutes = String.valueOf(diffMinutes);
            }

            if (!minutes.equals("")) {

                String lefttime = day + " " + hours + " " + minutes + " mins remaining";
                view.setTextViewText(R.id.minutes_ago, " :" + lefttime);

            } else {

                view.setTextViewText(R.id.minutes_ago, " : time passed");
            }


            ComponentName thisWidget = new ComponentName(this, CollectionWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, view);

            /*Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
            ringtone.play();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}




