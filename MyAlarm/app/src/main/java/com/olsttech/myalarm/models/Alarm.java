package com.olsttech.myalarm.models;

import android.support.annotation.Nullable;

/**
 * Created by adetunji on 03/09/2018. Alarm class
 */

public class Alarm {
    private int alarmId;
    private String alarmHour;
    private String alarmMinute;
    private String alarmLabel;
    private String alarmDay;
    private String alarmSound;
    private boolean alarmStatus;
    private boolean alarmEnabled;

    public Alarm (){}

    public Alarm (int id, String alarmHour,String alarmMinute, String alarmLabel, String alarmDay,
                  String alarmSound, boolean alarmStatus, boolean alarmEnabled){
        this.alarmHour = alarmHour;
        this.alarmMinute = alarmMinute;
        this.alarmLabel = alarmLabel;
        this.alarmDay = alarmDay;
        this.alarmSound = alarmSound;
        this.alarmStatus = alarmStatus;
        this.alarmId = id;
        this.alarmEnabled = alarmEnabled;
    }


    public String getAlarmHour() {
        return alarmHour;
    }

    public void setAlarmHour(String alamHour) {
        this.alarmHour = alamHour;
    }

    public String getAlarmMinute() {
        return alarmMinute;
    }

    public void setAlarmMinute(String alamMinute) {
        this.alarmMinute = alamMinute;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
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

    public boolean isAlarmEnabled() {
        return alarmEnabled;
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }
}
