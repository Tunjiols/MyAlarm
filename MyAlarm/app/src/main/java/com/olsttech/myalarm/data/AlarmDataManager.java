package com.olsttech.myalarm.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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
    public void getAllAlarms(@NonNull final AlarmDataManagerApi.DataManagerLoadAlarmCallBack callBack) {

        // Load from API only if needed.
        mAlarmPreference.prefGetAllAlarms(new AlarmPreferenceApi.LoadAlarmCallback() {
            @Override
            public void onAlarmLoaded(List<Alarm> alarms) {
                mAlarmList = alarms;
                Log.e("AlarmDataManagG listsiz", String.valueOf(mAlarmList.size()));

            }
        });
        callBack.onAlarmLoaded(mAlarmList);
    }

    @Override
    public void saveAlarm(@NonNull Alarm alarm, final AlarmDataManagerApi.OnLodingAlarmListener onLodingAlarmListener) {
        mAlarmPreference.prefSaveAlarm(alarm, new AlarmPreferenceApi.SaveAlarmCallback() {
            @Override
            public void onAlarmSaved(List<Alarm> alarms) {
                mAlarmList = alarms;
                Log.e("AlarmDataManagS listsiz", String.valueOf(mAlarmList.size()));
                onLodingAlarmListener.onSuccess("Success");
            }
        });
            onLodingAlarmListener.onFailure("Failed");
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
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}
