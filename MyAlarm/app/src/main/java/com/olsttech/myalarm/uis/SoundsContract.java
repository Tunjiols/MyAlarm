package com.olsttech.myalarm.uis;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;

import java.util.List;

/** interface for LabelContract 
*/

public interface SoundsContract{

    interface View{
        void loadView(@NonNull List<SoundModel> soundNames);
    }
    
    interface Presenter{
        void getSounds(@Nullable SoundModel setSound);
    }
    
    interface SoundsCallBack{
        void callBack(@NonNull SoundModel soundName);
    }
    
    interface ClickListener{
        void onSoundSelect(@Nullable SoundModel soundModel);
    }
}