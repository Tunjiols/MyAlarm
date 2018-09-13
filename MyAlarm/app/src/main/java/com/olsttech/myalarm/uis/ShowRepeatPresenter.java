package com.olsttech.myalarm.uis;

import com.olsttech.myalarm.models.DayModel;

import java.util.ArrayList;
import java.util.List;

public class ShowRepeatPresenter implements ShowRepeatContract.Presenter{
    
    private ShowRepeatContract.View mView;
    
    public ShowRepeatPresenter(ShowRepeatContract.View view){
        mView = view;
    }
    
    @Override
    public void setView(){
        mView.showView(getDayList());
    }

    private static List<DayModel> getDayList(){
        List<DayModel> dayList = new ArrayList<DayModel>();

        String[] weekName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        
        for (String week : weekName){
            dayList.add(new DayModel(week, false));
        }
        
        return dayList;
    }
}