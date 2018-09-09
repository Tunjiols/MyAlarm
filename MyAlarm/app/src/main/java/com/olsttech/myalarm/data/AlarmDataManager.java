package com.olsttech.myalarm.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adetunji on 06/09/2018.class AlarmDataManager
 */

public class AlarmDataManager implements AlarmDataManagerApi.LoadAlarmFromPreference,
        AlarmDataManagerApi.OnLodingAlarmListener {

    private AlarmPreference mAlarmPreference;
    private List<Alarm> mAlarmList;

    public AlarmDataManager(Context context){
        mAlarmPreference = new AlarmPreference(context);
        mAlarmList = new ArrayList<Alarm>();
    }

    @Override
    public void getAllAlarms(@NonNull AlarmDataManagerApi.DataManagerLoadAlarmCallBack callBack) {

        callBack.onAlarmLoaded(mAlarmList = mAlarmPreference.prefGetAllAlarms(new AlarmPreferenceApi.LoadAlarmCallback() {
            @Override
            public void onAlarmLoaded(List<Alarm> alarms) {

            }
        }));

    }

    @Override
    public void getAlarmTime() {

    }

    @Override
    public void getAlarmDay() {

    }

    @Override
    public void getAlarmLabel() {

    }

    @Override
    public void onLoadingSuccess(String message) {

    }

    @Override
    public void onLoadingFailure(String message) {

    }
}
