package com.olsttech.myalarm.alarms;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

import java.util.List;

/**
 * Created by adetunji on 01/09/2018. Alarm interface setup
 */

public interface AlarmContract {
    interface View {
        void showAlarms(List<Alarm> Alarm);
        void showAddAlarm();
        void showAlarmEditScreen(@NonNull String alarmId);
        void showEditAlarm(@NonNull Alarm alarm);
        void showAlarmRadioBtn();
    }

    interface Presenter{
        void getAllAlarms();
        void getAlarm();
        void getAlarmRadioBtnStatus();
    }

    interface UserActionListener{
        void addNewAlarm();
        void loadAlarm();
        void radioBtnAlarm(@NonNull Alarm alarm);
    }
}
