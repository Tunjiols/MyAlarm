package com.olsttech.myalarm.uis;

import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.DayModel;

import java.util.List;

/** interface for LabelContract 
*/

public interface SoundsContract{
    interface View{
        void loadView();
    }
    
    interface Presenter{
        getSounds();
    }
    
    interface SoundsCallBack{
        callBack(@NonNull String soundName);
    }
    
    interface ClickListener{
        onSoundSelect(String soundName);
    }
}