package com.olsttech.myalarm.alarms;

/**
 * Created by adetunji on 01/09/2018. Alarm interface setup
 */

public interface AlarmContract {
    interface view {
        void showAlarm();
        void showAddAlarm();
        void showAlarmDetails();
    }

    interface userActionListener{
        void addNewAlarm();
        void loadAlarm();
        void disableAlarm();
    }
}
