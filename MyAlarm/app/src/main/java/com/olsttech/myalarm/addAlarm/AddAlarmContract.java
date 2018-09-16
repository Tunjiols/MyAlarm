package com.olsttech.myalarm.addAlarm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;

import java.util.List;

/**
 * Created by adetunji on 01/09/2018. interface AddAlarmContract
 */

public interface AddAlarmContract {

    interface View {
        void showAddAlarm();
        void showRepeatScreen();
        void showLabelScreen();
        void showSoundsListScreen(SoundModel sound);
        void setSnooze(boolean snooze);
    }
  
    interface Presenter{
        void setRepeat();
        void addAlarmLabel();
        void setSound(SoundModel sound);
        void setAlarmTime();
        void setSnooze(boolean snooze);
        void saveAlarm(@NonNull Alarm alarm, String alarmId, SaveAlarmCallBack onAlarmsave);
    }

    interface SaveAlarmCallBack{
        void onAlarmSaveCallBack(@Nullable boolean value);
    }

    interface SelectionCalBack{
        void onDaySelected(@Nullable List<DayModel> selectedDays);
    }
    void onAlarmAdded(@NonNull Alarm alarm);
}
