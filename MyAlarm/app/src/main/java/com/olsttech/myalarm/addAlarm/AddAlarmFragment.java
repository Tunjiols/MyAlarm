package com.olsttech.myalarm.addAlarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olsttech.myalarm.R;

/**
 * Created by adetunji on 01/09/2018.AddAlarmFragment
 */

public class AddAlarmFragment extends Fragment implements AddAlarmContract.View,
                    View.OnCLickListener{

    private TextView mCancel;
    private TextView mSave;
    private TextView mAlarmTime;
    private TextView mRepeat_value;
    private TextView mLabel_value;
    private TextView mSound_value;
    private Button mSnoozeBtn;
    
    private AddAlarmContract.Presenter mAddAlarmPresenter;
    
    private long mAlarmTime;
    private String  mAlarmLabel;
    private String mAlarmDay;
    private String mAlarmSound;
    private boolean mAlarmStatus;

    public static AddAlarmFragment newInstance() {
        mAddAlarmPresenter = new AddAlarmPresenter(this);
        return new AddAlarmFragment();
    }

    public AddAlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

        setHasOptionsMenu(true);
        setRetainInstance(true);

        return rootView;
    }

    @Override
    public void showAddAlarm() {

    }

    @Override
    public void showRepeatScreen() {
        ShowRepeatActivity.startActivity(getContext(), Intent.START_,"No repeat",
                new  ShowRepeatPresenter.RepeatCallBack{
                    @Override
                    void callBack(List<DayModel> selectedWeeks){
                        String repeatDays = "No repeat";
                        if(null == selectedWeeks ){
                            mRepeat_value.setText(repeatDays);
                            mAlarmDay = repeatDays;
                        }
                        else{
                            
                            for(DayModel week ; selectedWeeks){
                                week.getText();
                                repeatDays = week;
                            }
                            mAlarmDay = repeatDays;
                        }
                });
        
    }

    @Override
    public void showLabelScreen() {
        LabelActivity.startActivity(getContext(), Intent.START_,"Default alarm",
                 new  LabelPresenter.LabelCallBack{
                    @Override
                    void callBack(@Nullable String Label){
                        if(label == null)
                            //set default label value as "Alarm"
                            mSound_value.setText("Alarm");
                            mAlarmLabel = "Alarm";
                        else
                            mSound_value.setText(label);
                            mAlarmLabel = label;
                    }
                });
    }

    @Override
    public void showSoundsListScreen() {
        SoundsActivity.startActivity(getContext(), Intent.START_,"Default sound",
                new  SoundsPresenter.SoundCallBack{
                    @Override
                    void callBack(@Nullable String sound){
                        if(label == null)
                            //set default label value as "Default sound"
                            mLabel_value.setText("Default sound");
                            mAlarmSound = "Default sound";
                        else
                            mLabel_value.setText(sound);
                            mAlarmSound = sound;
                    }
                });
    }
    
    @Override
    public void setSnooze(){
        if(!mSnoozeBtn.isChecked())
            mSnoozeBtn.Checked(true)
            mAlarmStatus = true;
        else
            mSnoozeBtn.Checked(false);
            mAlarmStatus = false;
    }
    
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.cancel:
                break;
            case R.id.save:
                mAlarmTime = 2200;
                Alarm alarm = new Alarm( mAlarmTime, mAlarmLabel, mAlarmDay, mAlarmSound, mAlarmStatus);
                String alarmId = "0";
                mAddAlarmPresenter.saveAlarm( alarm, alarmId);
                break;
            case R.id.repeat_layout:
                mAddAlarmPresenter.setRepeat();
                break;
            case R.id.label_layout:
                mAddAlarmPresenter.addAlarmLabel();
                break;
            case R.id.sound_layout:
                mAddAlarmPresenter.setSound();
                break;
            case R.id.snooze_btn:
                mAddAlarmPreseenter.setSnooze();
                break;
        }
    }
}
