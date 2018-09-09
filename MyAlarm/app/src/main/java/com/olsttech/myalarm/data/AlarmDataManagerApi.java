package com.olsttech.myalarm.data;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

import java.util.List;

/**
 * Created by adetunji on 06/09/2018. interface AlarmDataManagerApi
 */

public interface AlarmDataManagerApi {

    interface DataManagerLoadAlarmCallBack{

        void onAlarmLoaded(List<Alarm> alarms);

    }

    interface DataManagerGetAlarmCallBack{

        void onAlarmLoaded(Alarm alarms);

    }

    interface LoadAlarmFromPreference {

        void getAllAlarms(@NonNull DataManagerLoadAlarmCallBack callBack);

        void getAlarmTime();

        void getAlarmDay();

        void getAlarmLabel();
    }

    interface OnLodingAlarmListener {
        void onLoadingSuccess(String message);

        void onLoadingFailure(String message);
    }
}
