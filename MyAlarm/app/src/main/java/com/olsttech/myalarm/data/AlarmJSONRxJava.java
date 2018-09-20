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
    
    private JSONObject convertToJson(@NonNull Alarm alarm, String alarmId) throws JSONException{
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
    *@param AlarmJsonSuccess success): callback that returns success if the file is saved successfully.
    */
    private void saveFileToDatabase(JSONObject jsonObject, AlarmJsonSuccess success) throws IOException, JSONException{
        Writer writer = null;
        OutputStream outputStream;
		//Observable<JSONArray> writeToDisk(SONObject jsonObject);
        
        //Write the json file to the private disk space
        Observable<JSONArray> writeToDisk = Observable.from(jsonObject)
                .doOnNext(new Func1<JSONObject, Observable<JSONArray>>() {
		            @Override
		            public Observable<JSONArray> call(JSONObject alarmJson) {
                        //Make  json array
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(alarmJson);
			            return Observable.from(jsonArray);
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
    *@param AlarmJsonCallBack callback, callback that returns alarm list.
    */
    private void loadFilesFromDatabase(AlarmJsonCallBack callback){
        List<Alarm> alarmList = new ArrayList<Alarm>();
        //Json filename
        String fileName = "my_alarm.json";
        BufferredReader reader = null;
        
        InputStream inputStream = mContext.openFileInput(filename);
        try{
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
    *@param JSONObject jsonObj, the jsonObject that has the alarm.
    */
    private Alarm convertJSONObjectToAlarm(JSONObject jsonObj){
        Alarm alarm = new Alarm();
        
        alarmId = jsonObj.getString(ALARM_ID);
        alarm.setAlamTime(jsonObj.getString(ALARM_TIME));
        alarm.setAlarmDay(jsonObj.getString(ALARM_REPEAT));
        alarm.setAlarmLabel(jsonObj.getString(ALARM_LABEL));
        alarm.setAlarmStatus(jsonObj.getString(ALARM_SNOOZE));
        alarm.setAlamSound(jsonObj.getString(ALARM_SOUND));
        
        return alarm;
    }
    
    Observable<JSONArray> writeToDisks(JSONObject obj, Observable<JSONArray> jArray) { 
	    return Observable<JSONArray>.create(subscriber -> {
		    try {
			    DirectoryStream<Path> stream = Files.newDirectoryStream(dir, glob);
			    subscriber.add(Subscriptions.create(() -> {
				try {
					stream.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}));
			Observable<Path>.from(stream).subscribe(subscriber);
		}catch (DirectoryIteratorException ex) {
			subscriber.onError(ex);
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

Observable<JSONObject> observable = Observable.create(new Observable.OnSubscribe() {
		    @Override
		    public void call(Subscriber<? super JSONObject> subscriber) {
			    subscriber.onNext("Welcome to the world of Mindbowser!");
			    subscriber.onCompleted();
		    }
	    });
}