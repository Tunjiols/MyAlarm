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
        void showEditAlarm(String alarmId, @NonNull Alarm alarm );
        void showAlarmRadioBtn();
    }

    interface Presenter{
        void getAllAlarms();
        void getAlarm(@NonNull String alarmId);
        void addAlarm();
        void editAlarm();
        void getAlarmRadioBtnStatus();
    }
    
    interface AlarmItemClickListener {

        void onAlarmClicked(@NonNull Alarm alarm);
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
