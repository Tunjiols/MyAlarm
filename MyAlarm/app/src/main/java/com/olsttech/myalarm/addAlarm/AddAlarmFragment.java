package com.olsttech.myalarm.addAlarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.math.MathUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.adapters.TimeRecyclerAdapter;
import com.olsttech.myalarm.helpers.GetId;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;
import com.olsttech.myalarm.helpers.LabelActivity;
import com.olsttech.myalarm.helpers.LabelContract;
import com.olsttech.myalarm.helpers.RepeatActivity;
import com.olsttech.myalarm.helpers.RepeatContract;
import com.olsttech.myalarm.helpers.SoundsActivity;
import com.olsttech.myalarm.helpers.SoundsContract;
import com.olsttech.myalarm.models.TimePicker;
import com.olsttech.myalarm.utils.AlarmConstants;
import com.olsttech.myalarm.utils.SimpleItemDividerForDecoration;
import com.olsttech.myalarm.utils.TimeEmitter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by adetunji on 01/09/2018.AddAlarmFragment
 */

public class AddAlarmFragment extends Fragment implements AddAlarmContract.View,
                    View.OnClickListener{
    private static final String TAG = AddAlarmFragment.class.getCanonicalName();
    private TextView mCancel;
    private TextView mSave;
    private TextView mAlarmTimeView;
    private TextView mRepeat_value;
    private TextView mLabel_value;
    private TextView mSound_value;
    private TextView mTimeset;
    private ImageButton mSnoozeBtn;
    private RecyclerView mFrameLayoutHour;
    private RecyclerView mFrameLayoutMinute;
    private RelativeLayout mRepeat, mLabel, mSound, mSnooze;
    private AddAlarmContract.Presenter mAddAlarmPresenter;

    private String  mAlarmLabel;
    private String mSetDays ;

    private List<DayModel> mAlarmDays;
    private SoundModel mAlarmSound;
    private boolean mAlarmStatus = false;

    private TimePicker mTimePicker;
    private String editedTimeInitHour = "00";
    private String editedTimeInitMin = "00";

    private static AddAlarmContract.SaveAlarmCallBack mOnAlarmsave;

    private List<String> hourTime;
    private List<String> minuteTime;

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
        hourTime = new ArrayList<>();
        minuteTime = new ArrayList<>();
        mTimePicker = new TimePicker();
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
        mTimeset = rootView.findViewById(R.id.timeset);

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

        setTimeRecyclerAdapter();
        initSetup();

        return rootView;
    }

    private void setTimeRecyclerAdapter(){
        TimeEmitter timeEmitter = new TimeEmitter();
        hourTime = timeEmitter.emitTime(TimeEmitter.HOUR_EMITTER);
        minuteTime = timeEmitter.emitTime(TimeEmitter.MINUTES_EMITTER);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        mFrameLayoutHour.setLayoutManager(linearLayoutManager);
        mFrameLayoutMinute.setLayoutManager(linearLayoutManager2);
        mFrameLayoutMinute.setItemAnimator(new DefaultItemAnimator());
        mFrameLayoutMinute.addItemDecoration(new SimpleItemDividerForDecoration(getContext()));
        mFrameLayoutHour.addItemDecoration(new SimpleItemDividerForDecoration(getContext()));

        TimeRecyclerAdapter timeRecyclerAdapter = new TimeRecyclerAdapter(getContext(), hourTime,
                new TimeRecyclerAdapter.OnTimeFocusListener() {
            @Override
            public void onTimeFocus(String getFocusTime) {
                mTimePicker.setmHour(getFocusTime);
                String setEditedTime = mTimePicker.getmHour() + ":" + editedTimeInitMin;
                editedTimeInitHour = mTimePicker.getmHour();
                mTimeset.setText(setEditedTime);
            }
        });
        mFrameLayoutHour.setAdapter(timeRecyclerAdapter);
        TimeRecyclerAdapter minuteRecyclerAdapter = new TimeRecyclerAdapter(getContext(), minuteTime,
                new TimeRecyclerAdapter.OnTimeFocusListener() {
            @Override
            public void onTimeFocus(String getFocusTime) {
                mTimePicker.setmMinute(getFocusTime);
                String setEditedTime = editedTimeInitHour + ":" + mTimePicker.getmMinute();
                editedTimeInitMin = mTimePicker.getmMinute();
                mTimeset.setText(setEditedTime);
            }
        });
        mFrameLayoutMinute.setAdapter(minuteRecyclerAdapter);
    }

    private Alarm getAlarmParameters(){

        if (mTimePicker.getmHour() == null){
            mTimePicker.setmHour("00");
        }
        if (mTimePicker.getmMinute() == null ){
            mTimePicker.setmMinute("00");
        }

        return  new Alarm(GetId.getIdFromPref(getContext()), mTimePicker.getmHour(),mTimePicker.getmMinute(),
                mAlarmLabel, mSetDays, mAlarmSound.getSound(), mAlarmStatus, true);
    }

    /**Method for Initial setup*/
    private void initSetup(){

        /*Set initial repeat to No Repeat*/
        if (mSetDays != null){
            mRepeat_value.setText(mSetDays);//set alarm repeat days
        }else   {
            mRepeat_value.setText(R.string.no_repeat);//set as Default
        }

        if(mAlarmSound != null) {
            mSound_value.setText(mAlarmSound.getSound());//set default label value as "Default sound"
        }
        mLabel_value.setText(mAlarmLabel);//set alarm value

        mSnoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddAlarmPresenter.setSnooze(mAlarmStatus);
            }
        });
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
            mRepeat_value.setText(R.string.no_repeat);//set as Default
        }
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@lin Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
       // mTimeTracking.unsubscribe();
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
                mAddAlarmPresenter.saveAlarm(getAlarmParameters(), new AddAlarmContract.SaveAlarmCallBack() {
                    //callback to know if alarm is successfully saved
                    @Override
                    public void onAlarmSaveCallBack( boolean value) {
                        if(value) {
                            mOnAlarmsave.onAlarmSaveCallBack (value);
                            getActivity().finish();
                        }
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
            default: break;

        }
    }

	/** Returns the string of concatenated weekdays
     * @Link dayList: list of selected days
     * */
    private String getShortDay(List<DayModel> dayList){
        final StringBuilder stringBuilder = new StringBuilder(7);
        Observable<DayModel> buildString = Observable.from(dayList);
	    Subscriber<DayModel> subscriber = new Subscriber<DayModel>(){
		    @Override
			    public void onNext(DayModel day) {
				    stringBuilder.append(day.getDay().substring(0, 3) + ", ");
                }
		    @Override
			    public void onCompleted() {Log.i(TAG, "getShortDay completed"); }
		    @Override
			    public void onError(Throwable err) {Log.e(TAG, "getShortDay error", err); }
	    };
	    buildString.subscribe(subscriber);
	    return stringBuilder.toString();
    }
}
