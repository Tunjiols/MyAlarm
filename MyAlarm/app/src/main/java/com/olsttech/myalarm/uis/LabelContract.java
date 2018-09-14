package com.olsttech.myalarm.uis;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.DayModel;

import java.util.List;

/** interface for LabelContract 
*/

public interface LabelContract{

    interface View{
        void loadView();
    }
    
    interface Presenter{
        loadlabelEditScreen();
    }
    
    void callBack(@Nullable String label);
}