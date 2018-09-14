package com.olsttech.myalarm.uis;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olsttech.myalarm.models.DayModel;

import java.util.List;

/** interface for LabelContract 
*/

public interface LabelContract{

    interface View{
        void loadView();
    }
    
    interface Presenter{
        void loadlabelEditScreen();
    }
    
    interface LabelCallBack{
        void callBack(@Nullable String label);
    }
}