package com.example.alarmmanager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final int REQUEST_CODE = 1;
    AlarmManager alarmManager;
    Button button;
    Intent intent;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);


         intent = new Intent(this, MyReceiver.class);

         pendingIntent = PendingIntent.getBroadcast(this,
                REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        button = findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.example.alarmmanager.DatePicker datePicker = com.example.alarmmanager.DatePicker.getInstance(MainActivity.this);

                datePicker.show(getSupportFragmentManager(), "Calendar");
            }
        });

        /**
         1st Param : Context
         2nd Param : Integer request code
         3rd Param : Wrapped Intent
         4th Intent: Flag
         */

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 06);



        //setInexactRepeatingAlarm(calendar,pendingIntent);




    }
    /**
     *  Alarm will be triggered once exactly at given time
     *  This is same as set(context,timeMillis,pendingIntent) except for the fact that OS is not
     *  allowed to adjust the delivery time for these type of alarms.
     *  This shouldn’t be used unless there is a strong demand for alarm to be triggered at a precise time
     */
    public void setExactAlarm(Calendar calendar){
        alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

    }


    /**
     *This will schedule a one time alarm which will be triggered approximately at the scheduled time.
     *  OS is allowed to adjust delivery time for these alarms. This alarm will be triggered only once.
     *
     */
    public void setAlarm(Calendar calendar){

        //This alarm will trigger once approximately after 1 hour
        Log.e("time", String.valueOf(System.currentTimeMillis()));
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    /**
     * setRepeating
     *
     * This is same as setInExactRepeating except for the fact that the alarm is triggered exactly at the scheduled time.
     * Android suggests us to use this only when it is necessary as this puts unnecessary burden on the system since it wont
     * be able adjust delivery time to bundle multiple alarms together. Like setInExactRepeating this alarm will repeat itself after a scheduled time
     * @param calendar
     *
     */
    public void setRepeatingAlarm(Calendar calendar){
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    /**
     * This schedules a repeating alarm which is inexact in its trigger time i.e.
     * if you schedule the alarm to trigger at 8:00am it might not trigger exactly at the same time.
     * These alarms are very power efficient as they adjust delivery times to fire multiple alarms simultaneously. This alarm will be repeated after a scheduled time
     *
     * @param calendar
     * @param
     */
    public void setInexactRepeatingAlarm(Calendar calendar){
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
    }

    /**
     * You don’t even need  access to the same PendingIntent object for cancelling.
     * You can create a new pending intent with the same request code and FLAG_NO_CREATE and it will return the same PendingIntent object
     * @param intent
     */
    public void cancelAlarm(Intent intent){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null)
            alarmManager.cancel(pendingIntent);
    }





    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        setExactAlarm(calendar);

        Toast.makeText(this, "hour: " +hourOfDay+ ": " +minute, Toast.LENGTH_SHORT).show();
    }
}
