package com.olsttech.myalarm.addAlarm;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

/**
 * Created by adetunji on 01/09/2018. interface AddAlarmContract
 */

public interface AddAlarmContract {

    interface View {
        void showAddAlarm();
        void showRepeatScreen();
        void showLabelScreen();
        void showSoundsListScreen();
        void setSnooze();
    }
  
    interface Presenter{
        void setRepeat();
        void addAlarmLabel();
        void setSound();
        void setAlarmTime();
        void setSnooze();
        void saveAlarm(@NonNull Alarm alarm, String alarmId);
    }

    interface AddAlarmCallBack{
        void callBack(@Nullable String value);
    }
    
    void onAlarmAdded(@NonNull Alarm alarm);
}
