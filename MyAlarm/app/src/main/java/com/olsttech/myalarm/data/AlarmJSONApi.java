

public interface AlarmJSONApi{

    void saveAlarmToJSON(@NonNull Alarm alarm, String Alarm id);
    
    void getAlarmsFromJSON();
    
    void getAlarmInfoJSON(String alarmId, String param );
    
    interface AlarmJsonCallBack{
        void onAlarmLoadedCallBack(List<Alarm> alarmList);
    }
}