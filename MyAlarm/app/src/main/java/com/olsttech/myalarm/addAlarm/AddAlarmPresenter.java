package com.olsttech.myalarm.addAlarm;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

/**
 * Created by adetunji on 01/09/2018.AddAlarmPresenter
 */

public class AddAlarmPresenter implements AddAlarmContract.Presenter{

    private AddAlarmContract.View mView;
    
    public AddAlarmPresenter(AddAlarmContract.View view){
        this.mView = view;
    }

    @Override
    public void setRepeat() {
        mView.showRepeatScreen();
    }

    @Override
    public void addAlarmLabel() {
        mView.showLabelScreen();
    }

    @Override
    public void setSound() {  
        mView.showSoundsListScreen();
    }

    @Override
    public void setAlarmTime() {
       
    }

    @Override
    public void setSnooze() {
        
    }

    @Override
    public void saveAlarm(@NonNull Alarm alarm, String alarmId) {
        
    }
}
