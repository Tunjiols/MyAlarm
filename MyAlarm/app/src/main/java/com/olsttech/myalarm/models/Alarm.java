package com.olsttech.myalarm.models;

import android.support.annotation.Nullable;

/**
 * Created by adetunji on 03/09/2018. Alarm class
 */

public class Alarm {
    public String alarmId;
    public long alamTime;
    public String alarmLabel;
    public String alarmDay;
    public String alarmSound;
    public boolean alarmStatus;

    public Alarm (){}

    public Alarm (long alamTime, String alarmLabel, String alarmDay, String alarmSound, boolean alarmStatus){
        this.alamTime = alamTime;
        this.alarmLabel = alarmLabel;
        this.alarmDay = alarmDay;
        this.alarmSound = alarmSound;
        this.alarmStatus = alarmStatus;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public long getAlamTime() {
        return alamTime;
    }

    public void setAlamTime(long alamTime) {
        this.alamTime = alamTime;
    }

    public String getAlarmLabel() {
        return alarmLabel;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }

    public String getAlarmDay() {
        return alarmDay;
    }

    public void setAlarmDay(String alarmDay) {
        this.alarmDay = alarmDay;
    }

    public String getAlamSound() {
        return alarmSound;
    }

    public void setAlamSound(String alarmSound) {
        this.alarmSound = alarmSound;
    }

    public boolean getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(boolean alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

}
