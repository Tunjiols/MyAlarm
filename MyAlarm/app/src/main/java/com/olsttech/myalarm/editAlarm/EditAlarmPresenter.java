package com.olsttech.myalarm.editAlarm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.addAlarm.AddAlarmContract;
import com.olsttech.myalarm.alarms.AlarmContract;
import com.olsttech.myalarm.data.AlarmDataManager;
import com.olsttech.myalarm.data.AlarmDataManagerApi;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adetunji on 01/09/2018.class AlarmPresenter
 */

public class EditAlarmPresenter implements EditAlarmContract.Presenter{

    private EditAlarmContract.View mView;
    private AlarmDataManagerApi.LoadAlarmFromPreference mAlarmDataManager;

    public EditAlarmPresenter(EditAlarmContract.View view, Context context){
        this.mView = view;
        mAlarmDataManager = new AlarmDataManager(context);
    }

    @Override
    public void editRepeat(List<DayModel> day) {
        mView.showRepeatScreen(day);
    }

    @Override
    public void editAlarmLabel(String label) {
        mView.showLabelScreen(label);
    }

    @Override
    public void changeSound(SoundModel soundModel) {
        mView.showSoundsListScreen(soundModel);
    }

    @Override
    public void editAlarmTime() {

    }

    @Override
    public void editSnooze(boolean snooze) {
        mView.setSnooze(snooze);
    }

    @Override
    public void saveEditedAlarm(@NonNull Alarm alarm, String alarmId, EditAlarmContract.SaveAlarmEditCallBack onAlarmsave) {
        onAlarmsave.onAlarmSaveCallBack(true);
    }
}
