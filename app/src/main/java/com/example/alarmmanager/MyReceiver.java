package com.example.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm triggered", Toast.LENGTH_SHORT).show();

        Log.e("alarm", "alarm triggered");
    }
}
