package com.olsttech.myalarm.alarms;

import android.content.Context;

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

    public EditAlarmPresenter(AlarmContract.View view, Context context){
        this.mView = view;
        mAlarmDataManager = new AlarmDataManager(context);
    }

    @Override
    public void editRepeat(){}
    @Override
    public void editAlarmLabel(){}
    @Override
    public void changeSound(){}
    @Override
    public void editAlarmTime(){}
    @Override
    public void editSnooze(){}
    @Override
    public void saveEditedAlarm(@NonNull Alarm alarm, String alarmId){}
}
