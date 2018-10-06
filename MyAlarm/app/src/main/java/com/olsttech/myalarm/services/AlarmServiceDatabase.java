package com.olsttech.myalarm.services;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.olsttech.myalarm.utils.AlarmConstants;
import com.olsttech.myalarm.models.TimePicker;

/**
 * Created by adetunji on 06/09/2018.
 */

public class AlarmServiceDatabase {

/*
    public static AlarmServiceDatabase getInstance(Context context, TimePicker timePicker) {
        return new AlarmServiceDatabase(context, timePicker);
    }
/
    private AlarmServiceDatabase(Context context, TimePicker timePicker) {
        Handler handler = AlarmHandler.getInstance(context);

        int timeHour = timePicker.getHour();
        int timeMinute = timePicker.getMinute();
        Bundle bundle = new Bundle();
        bundle.putInt(AlarmConstants.HOUR, timeHour);
        bundle.putInt(AlarmConstants.MIN, timeMinute);

        Message msg = new Message();
        msg.setData(bundle);
        handler.sendMessage(msg);
    }*/
}


