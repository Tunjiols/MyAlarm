package com.olsttech.myalarm.models;



/**
 * Created by adetunji on 01/09/2018. SoundModel setup
 */

public class SoundModel{
 
        private String mSound;
        private boolean mChecked;
        
        public DayModel(String sond, boolean checked){
            this.mSound = sound;
            this.mChecked = checked;
        }
        
        public String getSound(){
            return mSound;
        }
        
        public void setSound(String sound){
            this.mSound = sound;
        }
        
        public boolean getChecked(){
            return mChecked;
        }
        
        public void setChecked(boolean checked){
            this.mChecked = checked;
        }
        
}