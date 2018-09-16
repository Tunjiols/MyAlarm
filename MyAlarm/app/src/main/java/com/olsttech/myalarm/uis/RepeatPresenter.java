package com.olsttech.myalarm.uis;

import android.util.Log;

import com.olsttech.myalarm.addAlarm.AddAlarmContract;
import com.olsttech.myalarm.addAlarm.AddAlarmPresenter;
import com.olsttech.myalarm.models.DayModel;

import java.util.ArrayList;
import java.util.List;

public class RepeatPresenter implements RepeatContract.Presenter{
    
    private RepeatContract.View mView;
    
    public RepeatPresenter(RepeatContract.View view){
        mView = view;
    }


    @Override
    public void setView(List<DayModel> mDaySetList){
        mView.showView(getDayList(mDaySetList));
    }

    @Override
    public void onSelected(List<DayModel> mSelectedDays) {
        AddAlarmPresenter addAlarmPresenter = new AddAlarmPresenter();
    }

    private static List<DayModel> getDayList(List<DayModel> mDaySetList){
        List<DayModel> dayList = new ArrayList<DayModel>();

        String[] weekName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        for (String week : weekName){
            dayList.add(new DayModel(week, false));
        }

        for (DayModel day : dayList){
            for (DayModel da :mDaySetList) {
                if (day.getDay().equals(da.getDay())) {
                    if (da.getChecked())
                        day.setChecked(true);
                    else
                        day.setChecked(false);
                }
            }
        }


        return dayList;
    }
}