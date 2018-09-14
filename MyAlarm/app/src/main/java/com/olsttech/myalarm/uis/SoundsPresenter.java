package com.olsttech.myalarm.uis;

import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;

import java.util.ArrayList;
import java.util.List;

public class SoundsPresenter implements SoundsContract.Presenter{

    private List<SoundModel> soundNames;
    private SoundsContract.View mView;
    
    public SoundsPresenter(SoundsContract.View view){
        this.mView = view;
        
    }
    @Override
    public void getSounds(){
       
        mView.loadView(getSoundsFromService());
    }
    
    private List<SoundModel> getSoundsFromService(){
        soundNames = new ArrayList<SoundModel>();
         //call the media service
        return soundNames;
    }
}
    