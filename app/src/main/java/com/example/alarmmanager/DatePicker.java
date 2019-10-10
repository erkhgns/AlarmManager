package com.example.alarmmanager;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePicker extends DialogFragment {


TimePickerDialog.OnTimeSetListener onTimeSetListener;


public static DatePicker getInstance(TimePickerDialog.OnTimeSetListener onTimeSetListener){
    DatePicker fragment = new DatePicker();

    fragment. onTimeSetListener = onTimeSetListener;
    return fragment;
}
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute, true);
    }
}
