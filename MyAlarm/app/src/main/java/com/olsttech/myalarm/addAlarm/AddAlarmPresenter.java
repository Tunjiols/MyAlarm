package com.olsttech.myalarm.addAlarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.olsttech.myalarm.data.AlarmDataManager;
import com.olsttech.myalarm.data.AlarmDataManagerApi;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;
import com.olsttech.myalarm.uis.RepeatContract;
import com.olsttech.myalarm.utils.AlarmConstants;

import java.util.List;

/**
 * Created by adetunji on 01/09/2018.AddAlarmPresenter
 */

public class AddAlarmPresenter implements AddAlarmContract.Presenter{

    private AddAlarmContract.View mView;
    private AlarmDataManager mAlarmDataManager;

    public AddAlarmPresenter(){}

    public AddAlarmPresenter(AddAlarmContract.View view, Context context){
        mAlarmDataManager = new AlarmDataManager(context);
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
    public void setSound(SoundModel sound) {
        mView.showSoundsListScreen(sound);
    }

    @Override
    public void setAlarmTime() {
       
    }

    @Override
    public void setSnooze(boolean snooze) {
        mView.setSnooze(snooze);
        
    }

    @Override
    public void saveAlarm(@NonNull Alarm alarm, String alarmId, final AddAlarmContract.SaveAlarmCallBack onAlarmsave) {

        //save alarm to repo
        mAlarmDataManager.saveAlarm(alarm, new AlarmDataManagerApi.OnLodingAlarmListener() {
            @Override
            public void onSuccess(String message) {
                onAlarmsave.onAlarmSaveCallBack(true);
            }

            @Override
            public void onFailure(String message) {
                onAlarmsave.onAlarmSaveCallBack(false);
            }
        });

    }
}
