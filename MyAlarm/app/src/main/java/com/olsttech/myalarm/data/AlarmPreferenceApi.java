package com.olsttech.myalarm.data;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

import java.util.List;

/**
 * Created by adetunji on 06/09/2018.interface AlarmPreferenceApi
 */

public interface AlarmPreferenceApi {

    interface LoadAlarmCallback {

        void onAlarmLoaded(List<Alarm> alarms);
    }

    interface SaveAlarmCallback {

        void onAlarmSaved(List<Alarm> alarms);
    }



        void prefGetAllAlarms(@NonNull LoadAlarmCallback callback);

        //void prefGetAlarm(@NonNull String alarmId, @NonNull GetAlarmCallback callback);

        void prefSaveAlarm(@NonNull Alarm alarm, SaveAlarmCallback saveAlarmCallback );

        void refreshData();

}
