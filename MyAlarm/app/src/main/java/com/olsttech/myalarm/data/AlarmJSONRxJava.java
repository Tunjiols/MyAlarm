package com.olsttech.myalarm.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.olsttech.myalarm.models.Alarm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

public class AlarmJSONRxJava implements AlarmJSONApi{
    
    private static final String ALARM_ID = "Alarm id";
    private static final String ALARM_TIME = "Alarm time";
    private static final String ALARM_REPEAT = "Alarm repeat";
    private static final String ALARM_LABEL = "Alarm label";
    private static final String ALARM_SNOOZE = "Alarm snooze";
    private static final String ALARM_SOUND = "Alarm sound";
    
    private Context mContext;
    
    public AlarmJSONRxJava(Context context){
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
    
    private JSONObject convertToJson(@NonNull Alarm alarm, String alarmId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put(ALARM_ID, alarmId);
        jsonObject.put(ALARM_TIME, alarm.getAlamTime());
        jsonObject.put(ALARM_REPEAT, alarm.getAlarmDay());
        jsonObject.put(ALARM_LABEL, alarm.getAlarmLabel());
        jsonObject.put(ALARM_SNOOZE, alarm.getAlarmStatus());
        jsonObject.put(ALARM_SOUND, alarm.getAlamSound());
        
        return jsonObject;
    }
    
    
    /**Method that saves the JsonObject into local disk. it converts the Jsonobjects into Jsonarray
    *@param  jsonObject: input JsonObject
    *@param success): callback that returns success if the file is saved successfully.
    */
    private void saveFileToDatabase(JSONObject jsonObject, AlarmJsonSuccess success) throws IOException, JSONException{
        Writer writer = null;
        OutputStream outputStream;
		//Observable<JSONArray> writeToDisk(SONObject jsonObject);
        
        //Write the json file to the private disk space
        Observable<JSONArray> writeToDisk = Observable.just(jsonObject)
                .doOnNext(new Func1<JSONObject, Observable<JSONArray>>() {
		            @Override
		            public Observable<JSONArray> call(JSONObject alarmJson) {
                        //Make  json array
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(alarmJson);
			            return Observable.from(jsonArray.put(jsonObject));
		            }})
                .create(jsonArray -> {
                    try{
                         //Json filename
                        String fileName = "my_alarm.json";
                        outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
                        writer = new OutputStreamWriter(outputStream);
                        writer.write(jsonArray.toString());
                    }finally{
                        
                    }
                })
				.subcribe((writer, outputStream)-> {
					if(null != writer ){
                         success.onSuccess("Success");
            	         outputStream.close();
                         writer.close();
                     }
				});
			};
    }
    
    /**load all alarms from Json file
    *@param callback, callback that returns alarm list.
    */
    private void loadFilesFromDatabase(AlarmJsonCallBack callback){
        List<Alarm> alarmList = new ArrayList<Alarm>();
        //Json filename
        String filename = "my_alarm.json";
        BufferedReader reader = null;
        
        InputStream inputStream ;
        try{
            inputStream = mContext.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder alarms = new StringBuilder();
            
            while((String line = reader.readline()) != null){
            alarm.append(line);
            }
            JSONArray alarmArray = (JSONArray) new JSONTokener(alarms.tostring());
            nextValue();
            
            for(JSONArray alarm : alarmArray){
                alarmList.add(convertJSONObjectToAlarm(alarm));
            }
            
            callback.onAlarmLoadedCallBack(alarmList);
            
        }catch(FileNotFoundException e){
        
        }finally{
            if(reader != null){
                reader.close();
            }
        }
    }
    
     /**converts Json Object to Alarm Object
    *@param jsonObj jsonObj, the jsonObject that has the alarm.
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
    
    Observable<JSONArray> writeToDisks(JSONObject alarmJson, AlarmJsonSuccess success) {
	    return Observable.create(subscriber -> {
            OutputStream outputStream;
            JSONArray jsonArray = new JSONArray();
		    try {
                String filename = "my_alarm.json";
                outputStream = mContext.openFileOutput(filename, Context.MODE_PRIVATE);

			    subscriber.add(Subscriptions.create(() -> {
                    Writer writer = new OutputStreamWriter(outputStream);
				    try {
                        jsonArray.put(alarmJson);
                        writer.write(jsonArray.toString());

				    }
				    catch (IOException e) {
					    e.printStackTrace();
				    }finally {
				        try {
                            success.onSuccess("Success");
                            outputStream.close();
                            writer.close();

                        }catch (IOException e){
				            e.printStackTrace();
                        }
                    }
			    }));
			    //Observable<JSONObject>.from(alarmJson).subscribe(subscriber);
		    }catch (IOException ioe) {
			    subscriber.onError(ioe);
		    }
	    });
    }

Observable<String> from(final Path path) { 
	return Observable.<String>create(subscriber -> {
		try {
			BufferedReader reader = Files.newBufferedReader(path);
			subscriber.add(Subscriptions.create(() -> {
				try {
					reader.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}));
			String line = null;
			while ((line = reader.readLine()) != null &&  !subscriber.isUnsubscribed()) {
				subscriber.onNext(line);
			}
			if (!subscriber.isUnsubscribed()) {
				subscriber.onCompleted();
			}
		}catch (IOException ioe) {
			if (!subscriber.isUnsubscribed()) {
				subscriber.onError(ioe);
			}
		}
	});
}	

}