package com.olsttech.myalarm.data;

import android.content.Context;
import android.support.annotation.NonNull;

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

    private AlarmSharePrefs mAlarmSharePrefs;

    public AlarmPreference(Context context){
        mAlarmSharePrefs = new AlarmSharePrefs(context);
    }

    @Override
    public void prefGetAllAlarms(@NonNull LoadAlarmCallback callback) {

        callback.onAlarmLoaded(allAlarmsFromPreference());

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

    private List<Alarm> allAlarmsFromPreference(){
        List<Alarm> alarmList = new ArrayList<Alarm>();
        Alarm alarm = new Alarm();
        alarm.setAlarmLabel(mAlarmSharePrefs.getString(AlarmConstants.ALARM_LABEL));
        alarm.setAlamTime(mAlarmSharePrefs.getLongDate(AlarmConstants.ALARM_TIME));
        alarm.setAlarmDay(mAlarmSharePrefs.getString(AlarmConstants.ALARM_DAY));
        alarm.setAlarmStatus(mAlarmSharePrefs.getBoolean(AlarmConstants.ALARM_STATUS,false));

        alarmList.add(alarm);

        return alarmList;
    }
}
