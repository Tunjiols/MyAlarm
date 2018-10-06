package com.olsttech.myalarm.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olsttech.myalarm.models.DayModel;

import java.util.List;

public interface RepeatContract {

    interface View{
        void showView(@NonNull List<DayModel> getDayList);
    }
    
    interface Presenter{
        void setView(List<DayModel> mDaySetList);
        void onSelected(List<DayModel> mSelectedDays);
    }

    interface RepeatCallBack{
        void onDaySelectedCallBack(@Nullable List<DayModel> selectedDays);
    }
}