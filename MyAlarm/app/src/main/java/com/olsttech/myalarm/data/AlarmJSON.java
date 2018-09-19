package com.olsttech.myalarm.data;


import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class AlarmJSON implements AlarmJSONApi{
    
    private static final String ALARM_ID = "Alarm id";
    private static final String ALARM_TIME = "Alarm time";
    private static final String ALARM_REPEAT = "Alarm repeat";
    private static final String ALARM_LABEL = "Alarm label";
    private static final String ALARM_SNOOZE = "Alarm snooze";
    private static final String ALARM_SOUND = "Alarm sound";
    
    private Context mContext;
    
    public AlarmJSON(Context context){
        this.mContext = context;
    }
    
    @Override
    public void saveAlarmToJSON(@NonNull Alarm alarm, String Alarm_id, AlarmJsonSuccess success){
    
    }
    
    @Override
    public void getAlarmsFromJSON(AlarmJsonCallBack callback){
    
    }
    
    @Override
    public void getAlarmInfoJSON(String alarmId, String param ){
    
    }
    
    private JSONObject convertToJson(@NonNull Alarm alarm, String id) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put(ALARM_ID, id);
        jsonObject.put(ALARM_TIME, alarm.getAlamTime());
        jsonObject.put(ALARM_REPEAT, alarm.getAlarmDay());
        jsonObject.put(ALARM_LABEL, alarm.getAlarmLabel());
        jsonObject.put(ALARM_SNOOZE, alarm.getAlarmStatus());
        jsonObject.put(ALARM_SOUND, alarm.getAlamSound());
        
        return jsonObject;
    }
    
    private void saveFileToDatabase(JSONObject jsonObject, AlarmJsonSuccess success) throws IOException, JSONException{
    
        //Json filename
        String fileName = "my_alarm.json";
        
        //Make  json array
        JSONArray jsonArray = new JSONArray();
        
        jsonArray.put(jsonObject);
        
        //Write the json file to the private disk space
        Writer writer = null;
        try{
            OutputStream outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(jsonArray.toString());
        }finally{
            if(null != writer ){
                success.onSuccess("Success");
                writer.close();
            }
        }     
    }
    
}