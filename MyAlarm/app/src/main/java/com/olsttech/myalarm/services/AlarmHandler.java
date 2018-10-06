package com.olsttech.myalarm.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.olsttech.myalarm.utils.AlarmConstants;

import java.util.Calendar;


public class AlarmHandler extends Handler implements AlarmHandlerApi{

    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private Context mContext;
    private OnRegisterAlarmListener mListener;

    public AlarmHandler(Context context, OnRegisterAlarmListener listener){
        mAlarmManager = (AlarmManager) context. getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(context,100,intent, 0);
        mContext = context;
        mListener = listener;
    }

    //public static AlarmHandler getInstance(Context context){
    //    return new AlarmHandler(context);
   // }

    /**
     * Subclasses must implement this to receive messages.
     *
     * @param msg
     */
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Bundle bundle = msg.getData();
        int timeHour = bundle.getInt(AlarmConstants.HOUR);
        int timeMinute = bundle.getInt(AlarmConstants.MIN);

        setAlarm(timeHour, timeMinute);
    }

    private void setAlarm(int timeHour, int timeMinute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), mPendingIntent);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), returnTimeInMillis(1), mPendingIntent);
        
        mListener.onRegister(true);
    }
    
    public void cancelAlarm(OnCancelAlarmListener listener) {
        if (mAlarmManager!= null) {
            mAlarmManager.cancel(mPendingIntent);
        }
    }

    private long returnTimeInMillis(int dayinterval){
        if(dayinterval > 7){
            dayinterval = 7;
        }else if(dayinterval < 0){
            dayinterval = 1;
        }
        //long time1 = dayinterval* AlarmManager.INTERVAL_DAY;
        long time = dayinterval * 24 * 60 *60 * 1000;
        return time;
    }
    //TODO: implement TimePicker to use Handler to send the time message(handler.sendMessage(msg))

}
