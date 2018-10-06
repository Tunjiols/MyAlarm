package com.olsttech.myalarm.editAlarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.olsttech.myalarm.models.TimePicker;
import com.olsttech.myalarm.utils.DeleteAlarmDialog;
import com.olsttech.myalarm.utils.TimeEmitter;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;
import com.olsttech.myalarm.helpers.LabelActivity;
import com.olsttech.myalarm.helpers.LabelContract;
import com.olsttech.myalarm.helpers.RepeatActivity;
import com.olsttech.myalarm.helpers.RepeatContract;
import com.olsttech.myalarm.helpers.SoundsActivity;
import com.olsttech.myalarm.helpers.SoundsContract;
import com.olsttech.myalarm.utils.TimeTracking;
import com.olsttech.myalarm.utils.AlarmConstants;
import com.olsttech.myalarm.utils.ScrollingTime;
import com.olsttech.myalarm.utils.SimpleItemDividerForDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by adetunji on 01/09/2018.EditAlarmFragment
 */

public class EditAlarmFragment extends Fragment implements EditAlarmContract.View,
                    View.OnClickListener{
    private static final String TAG = EditAlarmFragment.class.getCanonicalName();
    private TextView mCancel, mTimeset;
    private TextView mSave;
    private TextView mRepeat_value;
    private TextView mLabel_value;
    private TextView mSound_value;
    private ImageButton mSnoozeBtn;
    private SoundModel mAlarmSound;
    private String mAlarmLabel;
    private boolean mAlarmStatus;
    private RelativeLayout mRepeat, mLabel, mSound, mSnooze;
    private RecyclerView mFrameLayoutHour;
    private RecyclerView mFrameLayoutMinute;
    private RelativeLayout mDeleteAlarm;

    private String mSetDays;
    private String editedTimeInitHour ;
    private String editedTimeInitMin ;


    private EditAlarmPresenter mEditAlarmPresenter;
    private static EditAlarmContract.SaveAlarmCallBack mOnAlarmsave;
    private static Alarm mAlarm;
    
    private List<DayModel> mAlarmDays;
    private List<String> hourTime;
    private List<String> minuteTime;
    private TimePicker mTimePicker;

    public static EditAlarmFragment newInstance(Alarm alarm, EditAlarmContract.SaveAlarmCallBack onAlarmsave) {
        mAlarm = alarm;
        mOnAlarmsave = onAlarmsave;
        return new EditAlarmFragment();
    }

    public EditAlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditAlarmPresenter = new EditAlarmPresenter(this, getContext());
        mAlarmDays = new ArrayList<>();
        hourTime = new ArrayList<>();
        minuteTime = new ArrayList<>();
        mAlarmDays.add(new DayModel(AlarmConstants.NO_REPEAT, true)) ;
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

        View rootView = inflater.inflate(R.layout.fragment_editalarm, container, false);
        
        mCancel = rootView.findViewById(R.id.cancel);
        mSave = rootView.findViewById(R.id.save);
        mRepeat_value = rootView.findViewById(R.id.repeat_value);
        mLabel_value = rootView.findViewById(R.id.label_value);
        mSound_value = rootView.findViewById(R.id.sound_value);
        mSnoozeBtn = rootView.findViewById(R.id.snoozeBtn);
        mTimeset = rootView.findViewById(R.id.timeset);

        mLabel = rootView.findViewById(R.id.label);
        mRepeat = rootView.findViewById(R.id.repeat);
        mSound = rootView.findViewById(R.id.sound);
        mSnooze = rootView.findViewById(R.id.snooze);
        mFrameLayoutHour = rootView.findViewById(R.id.frame_hour);
        mFrameLayoutMinute = rootView.findViewById(R.id.frame_minute);
        mDeleteAlarm = rootView.findViewById(R.id.delete_alarm);

        mLabel.setOnClickListener(this);
        mRepeat.setOnClickListener(this);
        mSound.setOnClickListener(this);
        mSnooze.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mDeleteAlarm.setOnClickListener(this);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        extractAllFromAlarm(mAlarm);// get all values from alarm

        setTimeRecyclerAdapter();

        TimeTracking mTimeTracking = new TimeTracking(new Action1<TimeTracking.NowVisible>() {
            @Override
            public void call(TimeTracking.NowVisible nowVisible) {
                Log.e( String.valueOf(nowVisible.getFirstVisible()), String.valueOf(nowVisible.getLastVisible()));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

        ScrollingTime scrollingTimeHou = new ScrollingTime (mFrameLayoutHour, mTimeTracking);
        ScrollingTime scrollingTimeMin = new ScrollingTime(mFrameLayoutMinute, mTimeTracking);
        scrollingTimeHou.scrollView();
        scrollingTimeMin.scrollView();
        
        mSnoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditAlarmPresenter.editSnooze(mAlarmStatus);
            }
        });

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

        editedTimeInitHour = mAlarm.getAlarmHour();
        editedTimeInitMin = mAlarm.getAlarmMinute();

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
            mTimePicker.setmHour(mAlarm.getAlarmHour());
        }
        if (mTimePicker.getmMinute() == null ){
            mTimePicker.setmMinute(mAlarm.getAlarmMinute());
        }


        return  new Alarm(mAlarm.getAlarmId(), mTimePicker.getmHour(),mTimePicker.getmMinute(),
                mAlarmLabel, mSetDays, mAlarmSound.getSound(), mAlarmStatus, mAlarm.isAlarmEnabled());

    }

    private void extractAllFromAlarm(Alarm alarm){
        //set preset texts
        //mAlarmDays =
        mRepeat_value.setText(alarm.getAlarmDay());

        mAlarmLabel = alarm.getAlarmLabel();
        mLabel_value.setText(mAlarmLabel);

        //put the current sound parameters into mAlarmSound
        mAlarmSound = new SoundModel(alarm.getAlamSound(),alarm.getAlarmStatus());
        mSound_value.setText(alarm.getAlamSound());

        if (alarm.getAlarmStatus())
            mSnoozeBtn.setImageResource(R.drawable.ic_status);

        //display the alarm time
        String setTime = alarm.getAlarmHour()+":" + alarm.getAlarmMinute();
        mTimeset.setText(setTime);
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
        mSound_value.setText(mAlarmSound.getSound());//set default label value as "Default sound"

        mSetDays = getShortDay2(mAlarmDays);
        if (!mSetDays.isEmpty()){
            mRepeat_value.setText(mSetDays);//set alarm repeat days
        }else   {
            mRepeat_value.setText(mAlarmDays.get(0).getDay());//set alarm repeat days
        }
    }

    @Override
    public void showRepeatScreen(List<DayModel> days) {
        RepeatActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK, days,
                new RepeatContract.RepeatCallBack() {
                    @Override
                    public void onDaySelectedCallBack(List<DayModel> selectedDays) {
                        if (selectedDays != null) {
                            mAlarmDays = selectedDays;
                        }
                }
        });
    }

    @Override
    public void showLabelScreen(String label) {
        LabelActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK,
                label, new LabelContract.LabelCallBack() {
                    @Override
                    public void callBack(@Nullable String label) {
                        mAlarmLabel = label;
                    }
                });
    }

    @Override
    public void showSoundsListScreen(SoundModel sound) {
        SoundsActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK, sound,
                new SoundsContract.SoundsCallBack() {
                    @Override
                    public void callBack(@NonNull SoundModel soundName) {
                            mAlarmSound = soundName;
                    }
                });
    }
    
    @Override
    public boolean setSnooze(boolean snooze){
      if (!snooze) {
            mSnoozeBtn.setImageResource(R.drawable.ic_status);
            mAlarmStatus = true;
        } else {
            mSnoozeBtn.setImageResource(R.drawable.ic_status_off);
            mAlarmStatus = false;
        }
        return false;
    }

    @Override
    public void deleAlarm(){

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.cancel:
                getActivity().onBackPressed();
                break;
            case R.id.save:

                mEditAlarmPresenter.saveEditedAlarm(getAlarmParameters(), new EditAlarmContract.SaveAlarmEditCallBack() {
                    //callback to know if alarm is successfully saved
                    @Override
                    public void onAlarmSaveCallBack( boolean value) {
                        mOnAlarmsave.onAlarmSaveCallBack (value);
                        getActivity().finish();
                    }
                });
                break;
            case R.id.repeat:
                mEditAlarmPresenter.editRepeat(mAlarmDays);
                break;
            case R.id.label:
                mEditAlarmPresenter.editAlarmLabel(mAlarmLabel);
                break;
            case R.id.sound:
                mEditAlarmPresenter.changeSound(mAlarmSound);
                break;
            case R.id.snooze:
                mEditAlarmPresenter.editSnooze(mAlarmStatus);
                break;
            case R.id.delete_alarm:
                DeleteAlarmDialog deleteAlarmDialog = new DeleteAlarmDialog();
                deleteAlarmDialog.setSetMessage("Delete Alarm?", new DeleteAlarmDialog.OnAlarmDeleteClicked() {
                    @Override
                    public void onAlarmDelete() {
                        mEditAlarmPresenter.deleteAlarm(mAlarm, new EditAlarmContract.SaveAlarmEditCallBack() {
                            @Override
                            public void onAlarmSaveCallBack(@Nullable boolean value) {
                                mOnAlarmsave.onAlarmSaveCallBack (value);
                                getActivity().finish();
                            }
                        });
                    }
                });
                deleteAlarmDialog.show(getFragmentManager(),"");

                break;

        }
    
    }

    private String getShortDay2(List<DayModel> dayList){
        final StringBuilder stringBuilder = new StringBuilder(7);
        Observable<DayModel> buildString = Observable.from(dayList);
        Subscriber<DayModel> subscriber = new Subscriber<DayModel>(){
            @Override
            public void onNext(DayModel day) {
                stringBuilder.append(day.getDay().substring(0, 3) + ", ");
            }
            @Override
            public void onCompleted() { Log.i(TAG, "timeEmitter completed");}
            @Override
            public void onError(Throwable err) {  Log.e(TAG, "timeEmitter Error", err);}
        };
        buildString.subscribe(subscriber);
        return stringBuilder.toString();
    }
}
