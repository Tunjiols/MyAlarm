package com.olsttech.myalarm.editAlarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.DayModel;
import com.olsttech.myalarm.models.SoundModel;
import com.olsttech.myalarm.uis.LabelActivity;
import com.olsttech.myalarm.uis.LabelContract;
import com.olsttech.myalarm.uis.RepeatActivity;
import com.olsttech.myalarm.uis.RepeatContract;
import com.olsttech.myalarm.uis.SoundsActivity;
import com.olsttech.myalarm.uis.SoundsContract;
import com.olsttech.myalarm.utils.AlarmConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adetunji on 01/09/2018.EditAlarmFragment
 */

public class EditAlarmFragment extends Fragment implements EditAlarmContract.View,
                    View.OnClickListener{

    private TextView mCancel;
    private TextView mSave;
    private TextView mRepeat_value;
    private TextView mLabel_value;
    private TextView mSound_value;
    private RadioButton mSnoozeBtn;
    private String mAlarmSound;
    private boolean mAlarmStatus;

    private RelativeLayout mRepeat, mLabel, mSound, mSnooze;
    private FrameLayout mAlarmTimeView;
    private long mAlarmTime;
    private String mAlarmLabel = AlarmConstants.INIT_LABEL;

    private EditAlarmPresenter mEditAlarmPresenter;
    private static Alarm mAlarm;
    private List<DayModel> mAlarmDays;

    public static EditAlarmFragment newInstance(Alarm alarm) {
        mAlarm = alarm;
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
        mAlarmDays.add(new DayModel(AlarmConstants.NO_REPEAT, true)) ;
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

        mLabel = rootView.findViewById(R.id.label);
        mRepeat = rootView.findViewById(R.id.repeat);
        mSound = rootView.findViewById(R.id.sound);
        mSnooze = rootView.findViewById(R.id.snooze);

        mLabel.setOnClickListener(this);
        mRepeat.setOnClickListener(this);
        mSound.setOnClickListener(this);
        mSnooze.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mSave.setOnClickListener(this);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        mRepeat_value.setText(mAlarm.getAlarmDay());
        mLabel_value.setText(mAlarm.getAlarmLabel());
        mSound_value.setText(mAlarm.getAlamSound());
        mSnoozeBtn.setChecked(mAlarm.getAlarmStatus());

        return rootView;
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


        StringBuilder stringBuilder = new StringBuilder(7);
        String data = "" ;

        //concatinate the selected days
        for (DayModel week : mAlarmDays) {
            stringBuilder.append(week.getDay() + ",") ;
        }
        data = stringBuilder.toString();
        if (!data.isEmpty()){
            mRepeat_value.setText(data);//set alarm repeat days
        }else   {
            mRepeat_value.setText(mAlarmDays.get(0).getDay());//set alarm repeat days
        }

        Log.e("stringBuilder: ", data);

    }

    @Override
    public void showRepeatScreen() {
        RepeatActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK, mAlarmDays,
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
    public void showLabelScreen() {
        LabelActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK,
                mAlarm.getAlarmLabel(), new LabelContract.LabelCallBack() {
                    @Override
                    public void callBack(@Nullable String label) {

                    }
                });
    }

    @Override
    public void showSoundsListScreen(SoundModel sound) {
        SoundsActivity.startActivity(getContext(), Intent.FLAG_ACTIVITY_NEW_TASK,
                sound , new SoundsContract.SoundsCallBack() {
                    @Override
                    public void callBack(@NonNull SoundModel soundName) {
                        if(soundName == null) {
                            //set default label value as "Default sound"
                            mSound_value.setText("Default sound");
                            mAlarmSound = "Default sound";
                        }
                        //else
                            //mSound_value.setText(soundName);
                            //mAlarmSound = soundName;

                    }
                });
    }
    
    @Override
    public boolean setSnooze(){
        if(!mSnoozeBtn.isChecked()) {
            mSnoozeBtn.setChecked(true);
            mAlarmStatus = true;
        }
        else
            mSnoozeBtn.setChecked(false);
            mAlarmStatus = false;

        return true;
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.cancel:
                getActivity().onBackPressed();
                break;
            case R.id.save:
                mAlarmTime = 1430;
                Alarm alarm = new Alarm( mAlarmTime, mAlarmLabel, "Tuesday", mAlarmSound, mAlarmStatus);
                String alarmId = "0";
                 mEditAlarmPresenter.saveEditedAlarm(alarm, alarmId, new EditAlarmContract.SaveAlarmEditCallBack() {
                     @Override
                     public void onAlarmSaveCallBack( boolean value) {
                         if (value)
                             getActivity().finish();
                     }
                 });
                break;
            case R.id.repeat:
                mEditAlarmPresenter.editRepeat();
                break;
            case R.id.label:
                mEditAlarmPresenter.editAlarmLabel();
                break;
            case R.id.sound:
                //mEditAlarmPresenter.changeSound();
                break;
            case R.id.snooze:
                mEditAlarmPresenter.editSnooze();
                break;
        }
    
    }
}
