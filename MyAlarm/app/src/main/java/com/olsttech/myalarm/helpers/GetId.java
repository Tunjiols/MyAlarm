package com.olsttech.myalarm.helpers;

import android.content.Context;

import com.olsttech.myalarm.utils.AlarmConstants;
import com.olsttech.myalarm.utils.AlarmSharePrefs;

public class GetId {

    private GetId(){}

    public static int getIdFromPref(Context context){
        AlarmSharePrefs mAlarmSharePrefs = new AlarmSharePrefs(context);
        return mAlarmSharePrefs.getInt(AlarmConstants.ALARM_ID) + 1;
    }

    public static boolean validateIdFromPref(int id){

        return true;
    }

    public static void saveId(Context context, int id){
        AlarmSharePrefs mAlarmSharePrefs = new AlarmSharePrefs(context);
        mAlarmSharePrefs.saveInt(AlarmConstants.ALARM_ID, id);
    }
}
