package com.olsttech.myalarm.data;

import android.content.Context;
import android.support.annotation.NonNull;

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

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.subscriptions.Unsubscribed;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class AlarmJSONRxJava implements AlarmJSONApi{
    private static  final Logger LOGGER = Logger.getLogger(AlarmJSON.class.getSimpleName());

    private static final String ALARM_ID = "Alarm id";
    private static final String ALARM_HOUR = "Alarm hour";
    private static final String ALARM_MINUTE = "Alarm min";
    private static final String ALARM_REPEAT = "Alarm repeat";
    private static final String ALARM_LABEL = "Alarm label";
    private static final String ALARM_SNOOZE = "Alarm snooze";
    private static final String ALARM_SOUND = "Alarm sound";
    
    private Context mContext;
    
    public AlarmJSONRxJava(Context context){
        this.mContext = context;
    }
    
    @Override
    public void saveAlarmToJSON(@NonNull List<Alarm> alarmList, AlarmJsonSuccess success){

        /*
        try {
            saveFileToDatabase(alarmList, success);
        }catch (JSONException e){
            LOGGER.log(Level.ALL, e.toString());
        }catch (IOException e){
            LOGGER.log(Level.ALL, e.toString());
        }*/
    }
    
    @Override
    public void getAlarmsFromJSON(AlarmJsonCallBack callback){

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
    
    /*
    /**Method that saves the JsonObject into local disk. it converts the Jsonobjects into Jsonarray
    *@param  jsonObject: input JsonObject
    *@param success): callback that returns success if the file is saved successfully.
    */
    private void saveFileToDatabase(List<Alarm> alarmList, AlarmJsonCallBack callback) throws JSONException {
        //Write the json file to the private disk space
        Observable<JSONArray> writeToDisk = Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {
                    for (Alarm alarm : alarmList) {
                        if (!subscriber.isUnsubscribed()) {
                            try {
                                subscriber.onNext(convertToJson(alarm));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (!subscriber.isUnsubscribed()) {

                    }
                }
            }).subcribe((jsonArray) -> {
                    //Make  json array
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(jsonObject);

                    Writer writer = null;
                    OutputStream outputStream = null;
                    try {
                        String filename = "my_alarm.json";
                        outputStream = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
                        writer = new OutputStreamWriter(outputStream);
                        writer.write(jsonArray.toString());
                    }
                    catch (IOException e) {
                        LOGGER.log(Level.ALL, e.toString());
                    }finally {
                        try {
                            //success.onSuccess(true);
                            writer.close();
                            outputStream.close();
                        }catch (IOException e){
                            LOGGER.log(Level.ALL, e.toString());
                        }
                    }
                });

        writeToDisk.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


     /**converts Json Object to Alarm Object
    *@param jsonObj jsonObj, the jsonObject that has the alarm.
    */
     private Alarm convertJSONObjectToAlarm(JSONObject jsonObj) throws JSONException{
         Alarm alarm = new Alarm();

         alarm.setAlarmId(Integer.valueOf(jsonObj.getString(ALARM_ID)));
         alarm.setAlarmHour(jsonObj.getString(ALARM_HOUR));
         alarm.setAlarmMinute(jsonObj.getString(ALARM_MINUTE));
         alarm.setAlarmDay(jsonObj.getString(ALARM_REPEAT));
         alarm.setAlarmLabel(jsonObj.getString(ALARM_LABEL));
         alarm.setAlarmStatus(jsonObj.getString(ALARM_SNOOZE) == "true");
         alarm.setAlamSound(jsonObj.getString(ALARM_SOUND));

         return alarm;
     }

    
    private Unsubscribed disposable;

    private void disposeObservableJsonArray(){
        disposable.unsubscribe();
    }

    private Observable<JSONArray> writeToDisks(JSONObject alarmJson, AlarmJsonSuccess success) {
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
                        LOGGER.log(Level.ALL, e.toString());
				    }finally {
				        try {
                            success.onSuccess(true);
                            outputStream.close();
                            writer.close();

                        }catch (IOException e){
                            LOGGER.log(Level.ALL, e.toString());
                        }
                    }
			    }));
			    //Observable<JSONObject>.from(alarmJson).subscribe(subscriber);
		    }catch (IOException ioe) {
			    subscriber.onError(ioe);
		    }
	    });
    }

    private Observable<List<Alarm>> loadAlarmsFromDB() {
         List<Alarm> alarmList = new ArrayList<Alarm>();

         return Observable.<List<Alarm>>create(onsubscriber -> {
             InputStream inputStream;
             String filename = "my_alarm.json";
		    try {
                inputStream = mContext.openFileInput(filename);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			    
                onsubscriber.add(Subscriptions.create(() -> {
				    try {
					    reader.close();
				    }catch (IOException e) {
					    LOGGER.log(Level.ALL, e.toString());
				    }
			    }));

                StringBuilder alarms = new StringBuilder();
                String line = null;
			    while ((line = reader.readLine()) != null &&  !onsubscriber.isUnsubscribed()) {
				    alarms.append(line);   
			    }

                try {
                    JSONArray alarmArray = (JSONArray) new JSONTokener(alarms.toString()).nextValue();
                    for(int i = 0; i < alarmArray.length(); i++ ){

                        alarmList.add(convertJSONObjectToAlarm(alarmArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    LOGGER.log(Level.ALL, e.toString());
                }
                onsubscriber.onNext(alarmList);
                
			    if (!onsubscriber.isUnsubscribed()) {
				    onsubscriber.onCompleted();
			    }
		    }catch (IOException ioe) {
			    if (!onsubscriber.isUnsubscribed()) {
				    onsubscriber.onError(ioe);
			    }
		    }
	    });
    }

    private Observable<List<Alarm>> alarmListObserve(String alarm){
         return Observable.create(subscriber -> {
                 List<Alarm> alarmList = new ArrayList<>();
                 try {
                     JSONArray alarmArray = (JSONArray) new JSONTokener(alarm).nextValue();
                     for (int i = 0; i < alarmArray.length(); i++) {
                         alarmList.add(convertJSONObjectToAlarm(alarmArray.getJSONObject(i)));
                     }
                     subscriber.onNext(alarmList);
                 }catch (JSONException e){
                    LOGGER.log(Level.ALL, e.toString());
                 }
         });
    }
    
    private Observer<List<Alarm>> getAlatmObserver(){
        return new Observer<List<Alarm>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Alarm> alarms) {

            }
        };
    }
}

