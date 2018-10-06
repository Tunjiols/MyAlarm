package com.olsttech.myalarm.data;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.TimePicker;
import com.olsttech.myalarm.services.AlarmHandlerApi;

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

    interface AlarmManagerPresenter {

        void getAllAlarms(@NonNull DataManagerLoadAlarmCallBack callBack);

        void saveAlarm(@NonNull List<Alarm> alarmList, OnSavedAlarmListener onSavedAlarmListener);

        void registerAlarm(TimePicker timePicker, int dayInterval,
                           AlarmHandlerApi.OnRegisterAlarmListener listener);

        void unRigisterAlarm(@NonNull Alarm alarm);

        void deleteAlarmAndUnregisterIt(@NonNull Alarm alarm);
    }

    interface OnSavedAlarmListener {
        void onSuccess(boolean isSuccess);
    }
}
