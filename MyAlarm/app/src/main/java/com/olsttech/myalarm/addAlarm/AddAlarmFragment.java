package com.olsttech.myalarm.addAlarm;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.adapters.HourRecyclerAdapter;
import com.olsttech.myalarm.alarms.AlarmMainActivity;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;
import com.olsttech.myalarm.uis.LabelActivity;
import com.olsttech.myalarm.uis.LabelContract;
import com.olsttech.myalarm.uis.RepeatActivity;
import com.olsttech.myalarm.uis.RepeatContract;
import com.olsttech.myalarm.uis.SoundsActivity;
import com.olsttech.myalarm.uis.SoundsContract;
import com.olsttech.myalarm.uis.TimeTracking;
import com.olsttech.myalarm.utils.AlarmConstants;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by adetunji on 01/09/2018.AddAlarmFragment
 */

public class AddAlarmFragment extends Fragment implements AddAlarmContract.View,
                    View.OnClickListener{

    private TextView mCancel;
    private TextView mSave;
    private TextView mAlarmTimeView;
    private TextView mRepeat_value;
    private TextView mLabel_value;
    private TextView mSound_value;
    private ImageButton mSnoozeBtn;
    private RecyclerView mFrameLayoutHour;
    private RecyclerView mFrameLayoutMinute;
    
    private AddAlarmContract.Presenter mAddAlarmPresenter;
    
    private long mAlarmTime;
    private String  mAlarmLabel;
    private String mSetDays = "No Repeat" ;

    private List<DayModel> mAlarmDays;
    private SoundModel mAlarmSound;
    private boolean mAlarmStatus = false;

    private RelativeLayout mRepeat, mLabel, mSound, mSnooze;
    private FrameLayout mAlarmTimeFrameView;

    private static AddAlarmContract.SaveAlarmCallBack mOnAlarmsave;

    private List<String> hourTime;
    private List<String> minuteTime;
    private String[] hourTimes = {"01","02","03","04","05","06","07","08","09","10","11","12","13"
            ,"14","15","16","17","18","19","20","21","22","23","00"};
    private String[] minuteTimes = {"01","02","03","04","05","06","07","08","09","10","11","12","13"
            ,"14","15","16","17","18","19","20","21","22","23","30","31","32","33","34","35","36",
            "37", "38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53",
            "54","55","56","57","58","59","00"};



    public static AddAlarmFragment newInstance(AddAlarmContract.SaveAlarmCallBack onAlarmsave) {
        mOnAlarmsave = onAlarmsave;
        return new AddAlarmFragment();
    }

    public AddAlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAddAlarmPresenter = new AddAlarmPresenter(this, getContext());
        mAlarmLabel = AlarmConstants.INIT_LABEL;
        mAlarmDays = new ArrayList<>();
        mAlarmDays.add(new DayModel(AlarmConstants.NO_REPEAT, true)) ;
        mAlarmSound = new SoundModel(AlarmConstants.DAFAULT_SOUND, true);

        mAlarmTime = 1200;//s default time


    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addalarm, container, false);
            mCancel = rootView.findViewById(R.id.cancel);
            mSave = rootView.findViewById(R.id.save);
            mRepeat_value = rootView.findViewById(R.id.repeat_value);
            mLabel_value = rootView.findViewById(R.id.label_value);
            mSound_value = rootView.findViewById(R.id.sound_value);
            mSnoozeBtn = rootView.findViewById(R.id.snoozeBtn);

        mLabel = rootView.findViewById(R.id.label);
        mRepeat = rootView.findViewById(R.id.repeat);
        mSound = rootView.findViewById(R.id.sound);
        mSnooze = rootView.findViewById(R.id.snooze);

        mFrameLayoutHour = rootView.findViewById(R.id.frame_hour);
        mFrameLayoutMinute = rootView.findViewById(R.id.frame_minute);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        mLabel.setOnClickListener(this);
        mRepeat.setOnClickListener(this);
        mSound.setOnClickListener(this);
        mSnooze.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mSave.setOnClickListener(this);

        hourTime = new ArrayList<>();
        minuteTime = new ArrayList<>();
        for (String hour : hourTimes ) {
            hourTime.add(hour);
        }
        for (String minute : minuteTimes){
            minuteTime.add(minute);
        }

        Log.e("houetime size :", String.valueOf(minuteTime.size()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        mFrameLayoutHour.setLayoutManager(linearLayoutManager);
        mFrameLayoutMinute.setLayoutManager(linearLayoutManager2);
        HourRecyclerAdapter hourRecyclerAdapter = new HourRecyclerAdapter(getContext(),hourTime);
        mFrameLayoutHour.setAdapter(hourRecyclerAdapter);
        HourRecyclerAdapter minuteRecyclerAdapter = new HourRecyclerAdapter(getContext(),minuteTime);
        mFrameLayoutMinute.setAdapter(minuteRecyclerAdapter);

        scrollingTime(mFrameLayoutHour);
        scrollingTime(mFrameLayoutMinute);

        mSnoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddAlarmPresenter.setSnooze(mAlarmStatus);
            }
        });

        return rootView;
    }

    @Override
    public void showAddAlarm() {

    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link } of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();

        mLabel_value.setText(mAlarmLabel);//set alarm value
        if(mAlarmSound != null) {
            mSound_value.setText(mAlarmSound.getSound());//set default label value as "Default sound"
        }
        
        mSetDays = getShortDay(mAlarmDays);
        
        if (!mSetDays.isEmpty()){
            mRepeat_value.setText(mSetDays);//set alarm repeat days
        }else   {
            mRepeat_value.setText("No Repeat"));//set as Default
        }      
    }

    @Override
    public void showRepeatScreen() {
        RepeatActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK, mAlarmDays,
                new  RepeatContract.RepeatCallBack(){

                    @Override
                    public void onDaySelectedCallBack(List<DayModel> selectedDays) {
                            if (selectedDays != null) {
                                mAlarmDays = selectedDays;
                            }
                    }
                });

    }

    @Override
    public void showLabelScreen() {
        LabelActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK, mAlarmLabel,
                 new  LabelContract.LabelCallBack(){
                    @Override
                    public void callBack(@Nullable String label){
                        mAlarmLabel = label;
                    }
                });
    }

    @Override
    public void showSoundsListScreen(SoundModel sound) {
        SoundsActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK, sound,
                new SoundsContract.SoundsCallBack() {
                    @Override
                    public void callBack(@Nullable SoundModel soundName) {
                        if(soundName != null)
                            mAlarmSound = soundName;
                    }
                });
    }
    
    @Override
    public void setSnooze(boolean snooze) {
        if (!snooze) {
            mSnoozeBtn.setImageResource(R.drawable.ic_status);
            mAlarmStatus = true;
        } else {
            mSnoozeBtn.setImageResource(R.drawable.ic_status_off);
            mAlarmStatus = false;
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.cancel:
                getActivity().finish();
                break;
            case R.id.save:
                Alarm alarm = new Alarm( mAlarmTime, mAlarmLabel, mSetDays, mAlarmSound.getSound(), mAlarmStatus);
                String alarmId = "0";
                mAddAlarmPresenter.saveAlarm(alarm, alarmId, new AddAlarmContract.SaveAlarmCallBack() {
                    //callback to know if alarm is successfully saved
                    @Override
                    public void onAlarmSaveCallBack( boolean value) {
                        mOnAlarmsave.onAlarmSaveCallBack (value);
                        if(value)
                            getActivity().finish();
                    }
                });
                break;
            case R.id.repeat:
                //Load repeat activity
                mAddAlarmPresenter.setRepeat();
                break;
            case R.id.label:
                //Load label activity
                mAddAlarmPresenter.addAlarmLabel();
                break;
            case R.id.sound:
                mAddAlarmPresenter.setSound(mAlarmSound);
                break;
            case R.id.snooze:
                mAddAlarmPresenter.setSnooze(mAlarmStatus);
                break;

        }
    }
    public void scrollingTime(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                int firstVisiblesItem = dy;//recyclerView.getLayoutManager();

                int lastVisiblesItem = dx;//recyclerView.getLayoutManager();

                TimeTracking.NowVisible visible = new TimeTracking.NowVisible(firstVisiblesItem, lastVisiblesItem);

                TimeTracking timeTracking = new TimeTracking(
                        new Action1<TimeTracking.NowVisible>() {
                            @Override
                            public void call(TimeTracking.NowVisible visible) {
                                int position = visible.getLastVisible();
                                //TODO get my outputs
                                View v = recyclerView.getLayoutManager().getChildAt(position);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable s) {
                                Log.e("Error getting the time", s.getMessage());
                            }
                        }
                );

                timeTracking.postViewEvent(visible);

                //---------------------------------------------------
               // int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                //int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            }
        });
    }

    private List<String> timeEmitter(String... time){
    
        List<String> hours = new ArrayList<String>();
        
		Observable<String>.from(time).subscribe(new Action1() {
				@Override
				public void call(String hour) {
				    hours.add(hour);
                }    
			});
        return hours;
	}
    
    public String getShortDay(DayModel dayLists) { 
        StringBuilder stringBuilder = new StringBuilder(7);
        if(dayLists.size() == 1){
            dayLists.get(0).getDay().substring(0, 2);
            return stringBuilder.append(day).toString();
        }
        else{
            for (DayModel day : dayLists) {
                day.getDay().substring(0, 2) + "," ;
				return stringBuilder.append(day).toString;
            }
        }		
	}
    
    private String getShortDay2(DayModel dayList){
        StringBuilder stringBuilder = new StringBuilder(7);
        Observable<DayModel> buildString = Observable.from(dayList);
	    Subscriber subscriber = new Subscriber() 
		    @Override
			    public void onNext(DayModel day) {
                    day.getDay().substring(0, 2) + "," ;
				    return stringBuilder.append(day).toString;
                }
		    @Override
			    public void onCompleted() { }
		    @Override
			    public void onError(Throwable err) { }
	    };
	    buildString.subscribe(subscriber);
    }
}
