package com.olsttech.myalarm.data;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.services.AlarmHandler;
import com.olsttech.myalarm.services.AlarmHandlerApi;
import com.olsttech.myalarm.utils.AlarmConstants;
import com.olsttech.myalarm.models.TimePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adetunji on 06/09/2018.class AlarmDataManager
 */

public class AlarmDataManager implements AlarmDataManagerApi.AlarmManagerPresenter {

    private AlarmDataFactory mAlarmPreference;
    private AlarmJSON mAlarmJSON;
    private List<Alarm> mAlarmList;
    private Context mContext;

    public AlarmDataManager(Context context){
        mAlarmJSON = AlarmJSON.getInstance(context);
        mAlarmList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public void getAllAlarms( AlarmDataManagerApi.DataManagerLoadAlarmCallBack callBack) {
        mAlarmJSON.getAlarmsFromJSON(new AlarmJSONApi.AlarmJsonCallBack() {
            @Override
            public void onAlarmLoadedCallBack(List<Alarm> alarmList) {
                mAlarmList = alarmList;

                callBack.onAlarmLoaded(mAlarmList);

            }
        });
    }

    @Override
    public void saveAlarm(@NonNull List<Alarm> alarmList, final AlarmDataManagerApi.OnSavedAlarmListener onSavedAlarmListener) {
        mAlarmJSON.saveAlarmToJSON(alarmList, new AlarmJSONApi.AlarmJsonSuccess(){
            @Override
            public void onSuccess(boolean saved){
                if(saved)
                    onSavedAlarmListener.onSuccess(true);
                else
                    onSavedAlarmListener.onSuccess(false);
            }
        });
    }

    @Override
    public void deleteAlarmAndUnregisterIt(@NonNull Alarm alarm) {

    }

    private int validateAlarmExist(List<Alarm> alarmList, Alarm alarm){
        for (int id = 0; id < alarmList.size(); id++){
            if (alarmList.get(id).getAlarmId() == alarm.getAlarmId()){
                return id;
            }
        }
        return -1;
    }


    /**Method to register Alarm with the Android AlarmManager service
    *@Param timePicker:
    *@param: dayInterval:
    */
    @Override
    public void registerAlarm( TimePicker timePicker, int dayInterval,
                            AlarmHandlerApi.OnRegisterAlarmListener listener){

        Bundle bundle = new Bundle();
        bundle.putInt(AlarmConstants.HOUR, timePicker.getHour());
        bundle.putInt(AlarmConstants.MIN, timePicker.getMinute());
        bundle.putInt(AlarmConstants.DAY_INTERVAL, dayInterval);
        Message msg = new Message();
        msg.setData(bundle);
        Handler handler = new AlarmHandler(mContext, listener);
        handler.sendMessage(msg);
    }

    /**Method to un-register alarm from AlarmManager service
     * @param alarm: Alarm to be un-registered
     * */
    @Override
    public void unRigisterAlarm(@NonNull Alarm alarm){
        //TODO
    }
    
    public int calculateDayInterval(){
        //TODO: write function to calculate time interval
        return 1;
    }
}
