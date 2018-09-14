package com.olsttech.myalarm.uis;

import com.olsttech.myalarm.models.DayModel;

import java.util.ArrayList;
import java.util.List;

public class SoundsPresenter implements SoundsContract.Presenter{

    private List<String> soundNames;
    private SoundsContract.View mView;
    
    public SoundsPresenter(SoundsContract.View view){
        this.mView = view;
        
    }
    @Override
    void getSounds(){
       
        mView.loadView(getSoundsFromService());
    }
    
    private List<String> getSoundsFromService(){
        soundNames = new ArrayList<String>();
         //call the media service
        return soundNames;
    }
}
    