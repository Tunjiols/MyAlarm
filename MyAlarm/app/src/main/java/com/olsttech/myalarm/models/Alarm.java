package com.olsttech.myalarm.models;

import android.support.annotation.Nullable;

/**
 * Created by adetunji on 03/09/2018. Alarm class
 */

public class Alarm {
    public long alamTime;
    public String alarmLabel;
    public String alarmDay;
    public boolean alarmStatus;

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

    public boolean isAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(boolean alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

}
