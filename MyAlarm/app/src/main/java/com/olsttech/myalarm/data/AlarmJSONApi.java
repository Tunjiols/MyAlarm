package com.olsttech.myalarm.data;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olsttech.myalarm.models.Alarm;

import java.util.List;

public interface AlarmJSONApi{

    void saveAlarmToJSON(@NonNull List<Alarm> alarmList, AlarmJsonSuccess success);
    
    void getAlarmsFromJSON(AlarmJsonCallBack callbacks);
    
    void getAlarmInfoJSON(String alarmId, String param );
    
    interface AlarmJsonCallBack{
        void onAlarmLoadedCallBack( List<Alarm> alarmList);
    }
    
    interface AlarmJsonSuccess{
        void onSuccess(boolean isSaved);
    }
}