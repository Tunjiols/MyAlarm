package com.olsttech.myalarm.uis;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.DayModel;

import java.util.List;

public interface ShowRepeatContract{

    interface View{
        void showView(@NonNull List<DayModel> getDayList);
    }
    
    interface Presenter{
        void setView();
    }

}