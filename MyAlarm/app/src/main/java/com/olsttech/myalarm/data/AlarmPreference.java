package com.olsttech.myalarm.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.olsttech.myalarm.alarms.AlarmContract;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.utils.AlarmConstants;
import com.olsttech.myalarm.utils.AlarmSharePrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adetunji on 06/09/2018.class AlarmPreference
 */

public class AlarmPreference implements AlarmPreferenceApi{

    private static Context mContext;
    private List<Alarm> alarmList = new ArrayList<Alarm>();
    private AlarmSharePrefs mAlarmSharePrefs;


    private static AlarmPreference instance = new AlarmPreference();

    private AlarmPreference(){
        mAlarmSharePrefs = new AlarmSharePrefs(mContext);
        alarmList = allAlarmsFromPreference();
    }

    public static AlarmPreference getInstance(Context context){
        mContext = context;
        return instance;
    }

    @Override
    public void prefGetAllAlarms(@NonNull LoadAlarmCallback callback) {
        callback.onAlarmLoaded(alarmList);
    }



    @Override
    public void prefSaveAlarm(@NonNull Alarm alarm, SaveAlarmCallback saveAlarmCallback) {

         saveAlarmCallback.onAlarmSaved(savedAlarmOk(alarm));// return the whole alarm list after saving the alarm
    }

    @Override
    public void refreshData() {

    }

    private List<Alarm> allAlarmsFromPreference(){
        Alarm alarm = new Alarm(0, "22","00", "Alarm Test",
                "Monday", "Bell", true, true);
        //alarm.setAlarmLabel(mAlarmSharePrefs.getString(AlarmConstants.ALARM_LABEL));
        //alarm.setAlamTime(mAlarmSharePrefs.getLongDate(AlarmConstants.ALARM_TIME));
        //alarm.setAlarmDay(mAlarmSharePrefs.getString(AlarmConstants.ALARM_DAY));
        //alarm.setAlarmStatus(mAlarmSharePrefs.getBoolean(AlarmConstants.ALARM_STATUS,false));

        alarmList.add(alarm);

        return alarmList;
    }

    private List<Alarm> savedAlarmOk(Alarm alarm){
        alarmList.add(alarm);
        Log.e("AlarmPreference listsiz", String.valueOf(alarmList.size()));

        return alarmList;
    }
}
