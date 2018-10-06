package com.olsttech.myalarm.editAlarm;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.data.AlarmDataManager;
import com.olsttech.myalarm.data.AlarmDataManagerApi;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;
import com.olsttech.myalarm.utils.DeleteAlarmDialog;

import java.util.List;

/**
 * Created by adetunji on 01/09/2018.class AlarmPresenter
 */

public class EditAlarmPresenter implements EditAlarmContract.Presenter{

    private EditAlarmContract.View mView;

    private AlarmDataManager mAlarmDataManager;

    private Context mContext;;

    public EditAlarmPresenter(EditAlarmContract.View view, Context context){
        this.mView = view;
        this.mContext = context;
        mAlarmDataManager = new AlarmDataManager(context);
    }

    @Override
    public void editRepeat(List<DayModel> day) {
        mView.showRepeatScreen(day);
    }

    @Override
    public void editAlarmLabel(String label) {
        mView.showLabelScreen(label);
    }

    @Override
    public void changeSound(SoundModel soundModel) {
        mView.showSoundsListScreen(soundModel);
    }

    @Override
    public void editSnooze(boolean snooze) {
        mView.setSnooze(snooze);
    }

    List<Alarm> mAlarms;

    @Override
    public void saveEditedAlarm(@NonNull Alarm editedAlarm, EditAlarmContract.SaveAlarmEditCallBack onAlarmsave) {

        //Get the current lists of alarms and add newly edited alarm to the list
        mAlarmDataManager.getAllAlarms(new AlarmDataManagerApi.DataManagerLoadAlarmCallBack() {
            @Override
            public void onAlarmLoaded(List<Alarm> alarms) {
                //loop through the alarm and remove the alarm corresponding to edited alarm id
                int index = removeAlarmAndReturnIndex(editedAlarm, alarms);

                //add edited alarm to the alarm list and save it
                alarms.add(index, editedAlarm);
                mAlarmDataManager.saveAlarm(alarms, new AlarmDataManagerApi.OnSavedAlarmListener() {
                    @Override
                    public void onSuccess(boolean isSuccess) {

                        onAlarmsave.onAlarmSaveCallBack(isSuccess);
                    }
                });
            }
        });

    }

    @Override
    public void deleteAlarm(@NonNull Alarm alarm, EditAlarmContract.SaveAlarmEditCallBack onAlarmsave){
        mAlarmDataManager.getAllAlarms(new AlarmDataManagerApi.DataManagerLoadAlarmCallBack() {
            @Override
            public void onAlarmLoaded(List<Alarm> alarms) {
                //Remove the alarm from the list
                List<Alarm> newList = removeAlarmAndReturnAlarmList(alarms, alarm);
                mAlarmDataManager.saveAlarm(newList,
                        new AlarmDataManagerApi.OnSavedAlarmListener() {
                            @Override
                            public void onSuccess(boolean isSuccess) {
                                onAlarmsave.onAlarmSaveCallBack(isSuccess);
                                //TODO: unregister the alarm from AlarmManager
                            }
                        });
            }
        });
    }

    /**This method loop through the list of all alarms to find alarm to be deleted
     * it then return the index of deleted alarm
     * @param alarmToOperateOn: alarm to perform delete operation
     * @param alarmList: The list of all alarms
     * */
    private int removeAlarmAndReturnIndex(Alarm alarmToOperateOn, List<Alarm> alarmList){
        int alarmId = alarmToOperateOn.getAlarmId();
        int index = alarmId;

        for (int id = 0; id < alarmList.size(); id++){
            if (alarmList.get(id).getAlarmId() == alarmId){
                alarmList.remove(id);
                index = id;
            }
        }
        return index;
    }

    /**This method loop through the list of all alarms to find alarm to be deleted
     * it then return the alarmList
     * @param alarmToOperateOn: alarm to perform delete operation
     * @param alarmList: The list of all alarms
     * */
    private List<Alarm> removeAlarmAndReturnAlarmList(List<Alarm> alarmList, Alarm alarmToOperateOn ){
        int alarmId = alarmToOperateOn.getAlarmId();

        for (int id = 0; id < alarmList.size(); id++){
            if (alarmList.get(id).getAlarmId() == alarmId){
                alarmList.remove(id);
            }
        }
        return alarmList;
    }
}
