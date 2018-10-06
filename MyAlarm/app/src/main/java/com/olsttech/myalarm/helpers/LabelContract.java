package com.olsttech.myalarm.helpers;

import android.support.annotation.Nullable;

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