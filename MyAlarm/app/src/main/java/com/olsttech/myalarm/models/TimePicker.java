package com.olsttech.myalarm.models;

import android.support.annotation.IntRange;
import android.support.v4.math.MathUtils;

public class TimePicker{

    private int mHour;
    private int mMinute;

    private String mHourS;
    private String mMinuteS;

    public TimePicker(){}

    public TimePicker(int hourOfDay, int minute){
        this.mHour = hourOfDay;
        this.mMinute = minute;
    }

    private static void gsetTime(int hourOfDay, int minute){
        new TimePicker(hourOfDay, minute);
    }

    /**
     * Sets the currently selected hour using 24-hour time.
     * @param hour the hour to set, in the range (0-23)
     * @see #getHour()
     */
    public void setHour(@IntRange(from = 0, to = 23) int hour) {
        this.mHour =(MathUtils.clamp(hour, 0, 23));
    }

    /**
     * Returns the currently selected hour using 24-hour time.
     * @return the currently selected hour, in the range (0-23)
     * @see #setHour(int)
     */
    public int getHour() {
        return mHour;
    }

    /**
     * Sets the currently selected minute.
     * @param minute the minute to set, in the range (0-59)
     * @see #getMinute()
     */
    public void setMinute(@IntRange(from = 0, to = 59) int minute) {
        this.mMinute = (MathUtils.clamp(minute, 0, 59));
    }

    /**
     * Returns the currently selected minute.
     * @return the currently selected minute, in the range (0-59)
     */
    public int getMinute() {
        return mMinute;
    }

    //*********************************************************************************************
    public String getmHour() {
        return mHourS;
    }

    public void setmHour(String mHourS) {
        this.mHourS = mHourS;
    }

    public String getmMinute() {
        return mMinuteS;
    }

    public void setmMinute(String mMinuteS) {
        this.mMinuteS = mMinuteS;
    }
}