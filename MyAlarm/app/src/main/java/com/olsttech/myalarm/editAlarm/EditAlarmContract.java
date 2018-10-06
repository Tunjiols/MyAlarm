package com.olsttech.myalarm.editAlarm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olsttech.myalarm.addAlarm.AddAlarmContract;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;

import java.util.List;

/**
 * Created by adetunji on 01/09/2018. interface EditAlarmContract
 */

public interface EditAlarmContract {

    interface View {
        
        void showRepeatScreen(List<DayModel> day);

        void showLabelScreen(String label);

        void showSoundsListScreen(SoundModel sound);

        boolean setSnooze(boolean snooze);

        void deleAlarm();
    }
  
    interface Presenter{
        void editRepeat(List<DayModel> day);

        void editAlarmLabel(String label);

        void changeSound(SoundModel soundModel);

        void editSnooze(boolean snooze);

        void saveEditedAlarm(@NonNull Alarm alarm, SaveAlarmEditCallBack onAlarmsave);

        void deleteAlarm(@NonNull Alarm alarm, SaveAlarmEditCallBack onAlarmSave);
    }

    interface SaveAlarmEditCallBack{

        void onAlarmSaveCallBack(@Nullable boolean value);
    }

    interface SaveAlarmCallBack{

        void onAlarmSaveCallBack(@Nullable boolean value);
    }

    void onAlarmEdited(@NonNull Alarm alarm);
}
