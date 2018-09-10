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
        void getAlarm(@NonNull String alarmId);
        void addAlarm(Alarm alarm);
        void editAlarm(Alarm alarm, String alarmId);
        void getAlarmRadioBtnStatus();
    }
    
    interface AlarmItemClickListener {

        void onAlarmClick(Alarm alarm);
    }

    interface AddAlarmClickListener{
        void addNewAlarm();
    }
    
    interface EditAlarmClickListener{
        void editAlarm();
    }
    
    interface AlarmItemBtnClickListener{
        void radioBtnAlarm(@NonNull Alarm alarm);
    }
}
