package com.olsttech.myalarm.addAlarm;

/**
 * Created by adetunji on 01/09/2018. interface AddAlarmContract
 */

public interface AddAlarmContract {
  interface View {
   void showAddAlarm()
  }
  
  interface Presenter{
   void setRepeat();
   void addAlarmLabel();
   void setSound();
   void setAlarmTime();
   void saveAlarm(@NonNull Alarm alarm, String alarmId);
  }
  
  interface OnViewClickListener{
   
  }

}
