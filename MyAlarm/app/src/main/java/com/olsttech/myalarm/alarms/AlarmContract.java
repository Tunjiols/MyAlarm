package com.olsttech.myalarm.alarms;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.data.Alarm;

import java.util.List;

/**
 * Created by adetunji on 01/09/2018. Alarm interface setup
 */

public interface AlarmContract {
    interface view {
        void showAlarm(List<Alarm> Alarm);
        void showAddAlarm();
        void showAlarmDetails(@NonNull Alarm alarm);
    }

    interface userActionListener{
        void addNewAlarm();
        void loadAlarm();
        void disableAlarm(@NonNull Alarm alarm);
    }
}
