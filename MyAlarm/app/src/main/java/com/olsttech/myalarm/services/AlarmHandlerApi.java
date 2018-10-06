package com.olsttech.myalarm.services;

public interface AlarmHandlerApi{
    
    void cancelAlarm(OnCancelAlarmListener listener);
    
    interface OnRegisterAlarmListener{
        void onRegister(boolean status);
    }
    
    interface OnCancelAlarmListener{
        void onCancel(boolean status);
    }
}