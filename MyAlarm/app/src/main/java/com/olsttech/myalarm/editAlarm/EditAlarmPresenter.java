package com.olsttech.myalarm.editAlarm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.alarms.AlarmContract;
import com.olsttech.myalarm.data.AlarmDataManager;
import com.olsttech.myalarm.data.AlarmDataManagerApi;
import com.olsttech.myalarm.models.Alarm;

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
    public void editRepeat() {
        mView.showRepeatScreen();
    }

    @Override
    public void editAlarmLabel() {
        mView.showLabelScreen();
    }

    @Override
    public void changeSound() {
        mView.showSoundsListScreen();
    }

    @Override
    public void editAlarmTime() {

    }

    @Override
    public void editSnooze() {
        mView.setSnooze();
    }

    @Override
    public void saveEditedAlarm(@NonNull Alarm alarm, String alarmId) {

    }
}
