package com.olsttech.myalarm.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.utils.AlarmSharePrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adetunji on 06/09/2018.class AlarmPreference
 */

public class AlarmPreference implements AlarmPreferenceApi{

    private AlarmSharePrefs mAlarmSharePrefs;

    public AlarmPreference(Context context){
        mAlarmSharePrefs = new AlarmSharePrefs(context);
    }

    @Override
    public List<Alarm> prefGetAllAlarms(@NonNull LoadAlarmCallback callback) {

        List<Alarm> alarmList = new ArrayList<Alarm>();
        callback.onAlarmLoaded(alarmList);

        return allAlarmsFromPreference(alarmList);
    }

    @Override
    public void prefGetAlarm(@NonNull String alarmId, @NonNull GetAlarmCallback callback) {

    }

    @Override
    public void prefSaveAlarm(@NonNull Alarm alarm) {

    }

    @Override
    public void refreshData() {

    }

    private List<Alarm> allAlarmsFromPreference(List<Alarm> alarmList){
        Alarm alarm = new Alarm();
        alarm.setAlarmLabel(mAlarmSharePrefs.getString());
        alarm.setAlamTime(mAlarmSharePrefs.getLongDate());
        alarm.setAlarmDay(mAlarmSharePrefs.getString());
        alarm.setAlarmStatus(mAlarmSharePrefs.getBoolean());

        alarmList.add(alarm);

        return alarmList;
    }
}
