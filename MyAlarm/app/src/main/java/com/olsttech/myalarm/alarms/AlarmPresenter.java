package com.olsttech.myalarm.alarms;

import android.content.Context;

import com.olsttech.myalarm.data.AlarmDataManager;
import com.olsttech.myalarm.data.AlarmDataManagerApi;

/**
 * Created by adetunji on 01/09/2018.class AlarmPresenter
 */

public class AlarmPresenter implements AlarmContract.Presenter{

    private AlarmContract.View mView;
    private AlarmDataManagerApi.LoadAlarmFromPreference mAlarmDataManager;

    public AlarmPresenter(AlarmContract.View view, Context context){
        this.mView = view;
        mAlarmDataManager = new AlarmDataManager(context);
    }

    @Override
    public void getAllAlarms() {

        mView.showAlarms(mAlarmDataManager.getAllAlarms());
    }

    @Override
    public void getAlarm() {

    }
    
    @Override
    public void addAlarm(){
    
    }
    
    public void editAlarm(){
    
    }

    @Override
    public void getAlarmRadioBtnStatus() {

    }
}
