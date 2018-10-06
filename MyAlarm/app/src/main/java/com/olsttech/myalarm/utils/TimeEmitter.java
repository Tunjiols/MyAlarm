package com.olsttech.myalarm.utils;

import com.olsttech.myalarm.data.AlarmJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rx.Observable;
import rx.Subscriber;

public class TimeEmitter{
    private static  final Logger LOGGER = Logger.getLogger(AlarmJSON.class.getSimpleName());
    public static final String HOUR_EMITTER = "hours";
    public static final String MINUTES_EMITTER = "minutes";
    
    private String[] hourTimes = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13"
            ,"14","15","16","17","18","19","20","21","22","23"};
    private String[] minuteTimes = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13"
            ,"14","15","16","17","18","19","20","21","22","23","30","31","32","33","34","35","36",
            "37", "38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53",
            "54","55","56","57","58","59"};
            
    public TimeEmitter(){}

    public List<String> emitTime(String emitterType){
        String[] time = null;
        switch(emitterType) {
            case HOUR_EMITTER:
                time = hourTimes;
                break;
            case MINUTES_EMITTER:
                time = minuteTimes;
                break;
            default:
                time = null;
                break;
        }
        final List<String> timeget = new ArrayList<String>();
        Observable<String> observable = Observable.from(time);

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LOGGER.log(Level.ALL, "timeEmitter completed");
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.log(Level.ALL, "timeEmitter Error", e);
            }

            @Override
            public void onNext(String s) {
                timeget.add(s);
            }
        };
        observable.subscribe(subscriber);
        return timeget;

	}
}