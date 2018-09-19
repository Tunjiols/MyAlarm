
public class AlarmJSON implements AlarmJSONApi{
    
    private static final ALARM_ID = "Alarm id";
    private static final ALARM_TIME = "Alarm time";
    private static final ALARM_REPEAT = "Alarm repeat";
    private static final ALARM_LABEL = "Alarm label";
    private static final ALARM_SNOOZE = "Alarm snooze";
    private static final ALARM_SOUND = "Alarm sound";
    
    private Context mContext;
    
    public AlarmJSON(Context context){
        this.mContext = context;
    }

    public void saveAlarmToJSON(@NonNull Alarm alarm, String Alarm id){
    
    }
    
    public void getAlarmsFromJSON(AlarmJsonCallBack callback){
    
    }
    
    public void getAlarmInfoJSON(String alarmId, String param ){
    
    }
    
    private JSONObject convertToJson(@NonNull Alarm alarm, String id){
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put(ALARM_ID, id);
        jsonObject.put(ALARM_TIME, alarm.getAlamTime());
        jsonObject.put(ALARM_REPEAT, alarm.getAlarmDay());
        jsonObject.put(ALARM_LABEL, alarm.getAlarmLabel());
        jsonObject.put(ALARM_SNOOZE, alarm.getAlarmStatus());
        jsonObject.put(ALARM_SOUND, alarm.getAlamSound());
        
        return jsonObject;
    }
    
    
    
}