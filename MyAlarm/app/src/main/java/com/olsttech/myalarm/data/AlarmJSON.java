package com.olsttech.myalarm.data;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.olsttech.myalarm.models.Alarm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlarmJSON  implements AlarmJSONApi{
    private static  final Logger LOGGER = Logger.getLogger(AlarmJSON.class.getSimpleName());
    
    private static final String ALARM_ID = "Alarm id";
    private static final String ALARM_HOUR = "Alarm hour";
    private static final String ALARM_MINUTE = "Alarm min";
    private static final String ALARM_REPEAT = "Alarm repeat";
    private static final String ALARM_LABEL = "Alarm label";
    private static final String ALARM_SNOOZE = "Alarm snooze";
    private static final String ALARM_SOUND = "Alarm sound";
    
    private static Context mContext;

    private static AlarmJSON alarmJSON = new AlarmJSON();

    private AlarmJSON(){    }
    
    public static AlarmJSON getInstance(Context context){
        mContext = context;
        return alarmJSON;
    }
    
    @Override
    public void saveAlarmToJSON(@NonNull List<Alarm> alarmList, AlarmJsonSuccess success){
        try {
            saveFileToDatabase(alarmList, success);
        }catch (JSONException e){
            LOGGER.log(Level.ALL, e.toString());
        }catch (IOException e){
            LOGGER.log(Level.ALL, e.toString());
        }
    }
    
    @Override
    public void getAlarmsFromJSON(AlarmJsonCallBack callback){
        loadFilesFromDatabase(callback);
    }
    
    @Override
    public void getAlarmInfoJSON(String alarmId, String param ){
    
    }
    
    private JSONObject convertToJson(@NonNull Alarm alarm) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put(ALARM_ID, alarm.getAlarmId());
        jsonObject.put(ALARM_HOUR, alarm.getAlarmHour());
        jsonObject.put(ALARM_MINUTE, alarm.getAlarmMinute());
        jsonObject.put(ALARM_REPEAT, alarm.getAlarmDay());
        jsonObject.put(ALARM_LABEL, alarm.getAlarmLabel());
        jsonObject.put(ALARM_SNOOZE, alarm.getAlarmStatus());
        jsonObject.put(ALARM_SOUND, alarm.getAlamSound());
        
        return jsonObject;
    }
    
    
    /**Method that saves the JsonObject into local disk. it converts the Jsonobjects into Jsonarray
    *@param  alarmList: input Alarm
    *@param success : callback that returns success if the file is saved successfully.
    */
    private void saveFileToDatabase(List<Alarm> alarmList, AlarmJsonSuccess success) throws IOException, JSONException{
        //Json filename
        String fileName = "my_alarm.json";
        //Make  json array
        JSONArray jsonArray = new JSONArray();
        //Write the json file to the private disk space
        Writer writer = null;
        OutputStream outputStream = null;

        for (Alarm alarm : alarmList) {
            jsonArray.put(convertToJson(alarm));
        }

        try{
            outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(jsonArray.toString());
        }finally{
            if(null != writer ){
                Log.e("saveFileToDatabase :", "success");
                success.onSuccess(true);
                writer.close();

            }else {
                Log.e("saveFileToDatabase :", "failed");
                success.onSuccess(false);
            }
            outputStream.close();
        }     
    }
    
    /**load all alarms from Json file
    *@param callback, callback that returns alarm list.
    */
    private void loadFilesFromDatabase( AlarmJsonCallBack callback){
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
            JSONArray alarmArray = (JSONArray) new JSONTokener(alarms.toString()).nextValue();

            for(int i = 0; i < alarmArray.length(); i++ ){
                alarmList.add(convertJSONObjectToAlarm(alarmArray.getJSONObject(i)));
            }
            Log.e("loadFilesFromDatabase", String.valueOf(alarmList.size()));
            callback.onAlarmLoadedCallBack(alarmList);

        }catch(JSONException e){
            LOGGER.log(Level.ALL, e.toString());
        }catch (IOException e){
            LOGGER.log(Level.ALL, e.toString());
        }finally{
            if(reader != null){
                try {
                    reader.close();
                    inputStream.close();
                }catch (IOException e){
                    LOGGER.log(Level.ALL, e.toString());
                }
            }
        }
    }
    
     /**converts Json Object to Alarm Object
    *@param jsonObj, the jsonObject that has the alarm.
    */
    private Alarm convertJSONObjectToAlarm(JSONObject jsonObj) throws JSONException{
        Alarm alarm = new Alarm();

        alarm.setAlarmId(Integer.valueOf(jsonObj.getString(ALARM_ID)));
        alarm.setAlarmHour(jsonObj.getString(ALARM_HOUR));
        alarm.setAlarmMinute(jsonObj.getString(ALARM_MINUTE));
        alarm.setAlarmDay(jsonObj.getString(ALARM_REPEAT));
        alarm.setAlarmLabel(jsonObj.getString(ALARM_LABEL));
        alarm.setAlarmStatus(jsonObj.getString(ALARM_SNOOZE).equals("true"));
        alarm.setAlamSound(jsonObj.getString(ALARM_SOUND));
        
        return alarm;
    }
}