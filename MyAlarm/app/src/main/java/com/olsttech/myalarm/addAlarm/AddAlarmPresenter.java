package com.olsttech.myalarm.addAlarm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.data.AlarmDataManager;
import com.olsttech.myalarm.data.AlarmDataManagerApi;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.SoundModel;
import com.olsttech.myalarm.models.TimePicker;
import com.olsttech.myalarm.services.AlarmHandlerApi;

import java.util.ArrayList;
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
    public void setSnooze(boolean snooze) {
        mView.setSnooze(snooze);
        
    }

    @Override
    public void saveAlarm(@NonNull Alarm alarm, final AddAlarmContract.SaveAlarmCallBack onAlarmsave) {
        //Get the current lists of alarms and add newly created alarm to the list
        mAlarmDataManager.getAllAlarms(new AlarmDataManagerApi.DataManagerLoadAlarmCallBack() {
            @Override
            public void onAlarmLoaded(List<Alarm> alarms) {
                alarms.add(alarm);
                //save alarm to repo through AlarmDataManager
                mAlarmDataManager.saveAlarm(alarms, new AlarmDataManagerApi.OnSavedAlarmListener() {
                    @Override
                    public void onSuccess(boolean isSaved) {

                        TimePicker timePicker = new TimePicker(Integer.valueOf(alarm.getAlarmHour()),
                                Integer.valueOf(alarm.getAlarmHour()));

                        if(isSaved) {
                            //Register alarm time with Android AlarmManager
                            mAlarmDataManager.registerAlarm(timePicker, mAlarmDataManager.calculateDayInterval(),
                                    new AlarmHandlerApi.OnRegisterAlarmListener() {
                                        public void onRegister(boolean status) {

                                            onAlarmsave.onAlarmSaveCallBack(status);
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }
}
