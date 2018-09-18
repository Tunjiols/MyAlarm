package com.olsttech.myalarm.uis;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.collect.Lists;
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
    public void getSounds(@NonNull SoundModel setSound){
        mView.loadView(getSoundsFromService(setSound));
    }
    
    private List<SoundModel> getSoundsFromService(SoundModel setSound){
        List<SoundModel> soundNames = new ArrayList<SoundModel>();

        String[] soundList = {"Bell", "Dance", "Mew", "Wince", "Dnacers", "Is Alive", "Right na na"};

        for (String sound : soundList){
            if (sound.equals(setSound.getSound()))
                soundNames.add(new SoundModel(sound, true));
            else
                soundNames.add(new SoundModel(sound, false));
        }

        return soundNames;
    }
}
    