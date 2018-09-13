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

public class EditAlarmActivity extends AppCompactActivity implements EditAlarmContract.View{

    private Toolbar toolbar;
    private TextView mCancel;
    private TextView mSave;
    private TextView mRepeat_value;
    private TextView mLabel_value;
    private TextView mSound_value;
    private Button mSnoozeBtn;
    
    private EditAlarmContract.Presenter mEditAlarmPresenter;

    public static Intent startActivity(Context context) {
        Intent intent = new Intent(context, EditAlarmActivity.class);
        return intent();
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.fragment_addalarm);
        bindViews();
        initSetup();
    }

    private void bindView(){
        mCancel = rootView.findViewById(R.id.cancel);
        mSave = rootView.findViewById(R.id.save);
        mRepeat_value = rootView.findViewById(R.id.repeat_value);
        mLabel_value = rootView.findViewById(R.id.label_value);
        mSound_value = rootView.findViewById(R.id.sound_value);
        mSnoozeBtn = rootView.findViewById(R.id.snoozeBtn);  
    }
    
    private initSetup(){
        setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mEditAlarmPresenter = new EditAlarmPresenter();
    }

    @Override
    public void showRepeatScreen() {

    }

    @Override
    public void showLabelScreen() {

    }

    @Override
    public void showSoundsListScreen() {

    }
    
    @Override
    public boolean setSnooze(){
    
    }
}
