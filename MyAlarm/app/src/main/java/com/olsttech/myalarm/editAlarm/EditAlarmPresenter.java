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

public class EditAlarmPresenter implements AlarmContract.Presenter{

    private EditAlarmContract.View mView;
    private AlarmDataManagerApi.LoadAlarmFromPreference mAlarmDataManager;

    public EditAlarmPresenter(EditAlarmContract.View view, Context context){
        this.mView = view;
        mAlarmDataManager = new AlarmDataManager(context);
    }

    @Override
    public void getAllAlarms() {

    }

    @Override
    public void getAlarm(@NonNull String alarmId) {

    }

    @Override
    public void addAlarm() {

    }

    @Override
    public void editAlarm() {

    }

    @Override
    public void openEditAlarmScreen(@NonNull Alarm clickedAlarm) {

    }

    @Override
    public void getAlarmRadioBtnStatus() {

    }
}
