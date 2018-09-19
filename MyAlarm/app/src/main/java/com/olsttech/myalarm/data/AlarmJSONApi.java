package com.olsttech.myalarm.data;


import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

import java.util.List;

public interface AlarmJSONApi{

    void saveAlarmToJSON(@NonNull Alarm alarm, String Alarm_id, AlarmJsonSuccess success);
    
    void getAlarmsFromJSON(AlarmJsonCallBack callbacks);
    
    void getAlarmInfoJSON(String alarmId, String param );
    
    interface AlarmJsonCallBack{
        void onAlarmLoadedCallBack(List<Alarm> alarmList);
    }
    
    interface AlarmJsonSuccess{
        void onSuccess(String message);
        void onFailure(String message);
    }
}