package com.olsttech.myalarm.data;


import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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
        loadFilesFromDatabase(callback);
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
    
    
    /**Method that saves the JsonObject into local disk. it converts the Jsonobjects into Jsonarray
    *@param  jsonObject: input JsonObject
    *@param success : callback that returns success if the file is saved successfully.
    */
    private void saveFileToDatabase(JSONObject jsonObject, AlarmJsonSuccess success) throws IOException, JSONException{
    
        //Json filename
        String fileName = "my_alarm.json";
        
        //Make  json array
        JSONArray jsonArray = new JSONArray();
        
        jsonArray.put(jsonObject);
        
        //Write the json file to the private disk space
        Writer writer = null;
        OutputStream outputStream = null;
        try{
            outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(jsonArray.toString());
        }finally{
            if(null != writer ){
                success.onSuccess("Success");
                outputStream.close();
                writer.close();
            }
        }     
    }
    
    /**load all alarms from Json file
    *@param callback, callback that returns alarm list.
    */
    private void loadFilesFromDatabase(AlarmJsonCallBack callback){
        List<Alarm> alarmList = new ArrayList<Alarm>();
        //Json filename
        String filename = "my_alarm.json";
        BufferedReader reader = null;
        InputStream inputStream = null;

        try{
            inputStream = mContext.openFileInput(filename);

            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder alarms = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                alarms.append(line);
            }
            JSONArray alarmArray = (JSONArray) new JSONTokener(alarms.toString());
            //nextValue();
            
            for(JSONArray alarm : alarmArray){
                alarmList.add(convertJSONObjectToAlarm(alarm));
            }
            
            callback.onAlarmLoadedCallBack(alarmList);
            
        }catch(FileNotFoundException e){
        
        }catch (IOException e){

        }finally{
            if(reader != null){
                reader.close();
                inputStream.close();
            }
        }
    }
    
     /**converts Json Object to Alarm Object
    *@param jsonObj, the jsonObject that has the alarm.
    */
    private Alarm convertJSONObjectToAlarm(JSONObject jsonObj) throws JSONException{
        Alarm alarm = new Alarm();

        alarm.setAlarmId(jsonObj.getString(ALARM_ID));
        alarm.setAlamTime(Long.valueOf(jsonObj.getString(ALARM_TIME)));
        alarm.setAlarmDay(jsonObj.getString(ALARM_REPEAT));
        alarm.setAlarmLabel(jsonObj.getString(ALARM_LABEL));
        alarm.setAlarmStatus(jsonObj.getString(ALARM_SNOOZE));
        alarm.setAlamSound(jsonObj.getString(ALARM_SOUND));
        
        return alarm;
    }
}