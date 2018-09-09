package com.olsttech.myalarm.services;

import com.olsttech.myalarm.models.Alarm;

import java.util.List;

/**
 * Created by adetunji on 03/09/2018. Defines an interface to the service API that is used by this
 * application. All data request should be piped through this interface.
 */

public interface AlarmServiceApi {

    interface AlarmServiceCallback<T>{
        void onLoaded(T alarms);
    }

    void getAllAlarms(AlarmServiceCallback<List<Alarm>> callback);

    void getAlarm(String alarmId, AlarmServiceCallback<Alarm> callback);

    void saveAlarm(Alarm alarm);
}
