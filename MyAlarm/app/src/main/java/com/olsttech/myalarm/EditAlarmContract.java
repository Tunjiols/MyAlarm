package com.olsttech.myalarm.addAlarm;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

/**
 * Created by adetunji on 01/09/2018. interface EditAlarmContract
 */

public interface EditAlarmContract {

    interface View {
        
        void showRepeatScreen();
        void showLabelScreen();
        void showSoundsListScreen();
        boolean setSnooze();
    }
  
    interface Presenter{
        void editRepeat();
        void editAlarmLabel();
        void changeSound();
        void editAlarmTime();
        void editSnooze();
        void saveEditedAlarm(@NonNull Alarm alarm, String alarmId);
    }

    void onAlarmEdited(@NonNull Alarm alarm);
}
