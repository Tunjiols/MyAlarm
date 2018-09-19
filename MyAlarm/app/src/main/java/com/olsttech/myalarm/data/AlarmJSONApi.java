

public interface AlarmJSONApi{

    void saveAlarmToJSON(@NonNull Alarm alarm, String Alarm id);
    
    void getAlarmsFromJSON(AlarmJsonCallBack callbacks);
    
    void getAlarmInfoJSON(String alarmId, String param );
    
    interface AlarmJsonCallBack{
        void onAlarmLoadedCallBack(List<Alarm> alarmList);
    }
    
    interface AlarmJsonSuccess{
        onSuccess(String success);
        onFailure(String failure);
    }
}