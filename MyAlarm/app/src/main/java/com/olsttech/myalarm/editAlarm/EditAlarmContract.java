package com.olsttech.myalarm.editAlarm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olsttech.myalarm.addAlarm.AddAlarmContract;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.SoundModel;

/**
 * Created by adetunji on 01/09/2018. interface EditAlarmContract
 */

public interface EditAlarmContract {

    interface View {
        
        void showRepeatScreen();
        void showLabelScreen();
        void showSoundsListScreen(SoundModel sound);
        boolean setSnooze();
    }
  
    interface Presenter{
        void editRepeat();
        void editAlarmLabel();
        void changeSound(SoundModel soundModel);
        void editAlarmTime();
        void editSnooze();
        void saveEditedAlarm(@NonNull Alarm alarm, String alarmId, SaveAlarmEditCallBack onAlarmsave);
    }
    interface SaveAlarmEditCallBack{
        void onAlarmSaveCallBack(@Nullable boolean value);
    }
    void onAlarmEdited(@NonNull Alarm alarm);
}
