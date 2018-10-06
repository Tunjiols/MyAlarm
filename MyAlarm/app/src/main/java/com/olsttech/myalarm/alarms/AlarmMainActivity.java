package com.olsttech.myalarm.alarms;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.adapters.HomeAlarmViewAdapter;
import com.olsttech.myalarm.adapters.EditScreenAlarmAdapter;
import com.olsttech.myalarm.addAlarm.AddAlarmActivity;
import com.olsttech.myalarm.addAlarm.AddAlarmContract;
import com.olsttech.myalarm.editAlarm.EditAlarmActivity;
import com.olsttech.myalarm.editAlarm.EditAlarmContract;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.utils.SimpleItemDividerForDecoration;

import java.util.ArrayList;
import java.util.List;

public class AlarmMainActivity extends AppCompatActivity implements AlarmContract.View,  
                 View.OnClickListener{

    private Toolbar toolbar;
    private AlarmContract.Presenter mAlarmPresenter;
    private RecyclerView mRecyclerView;
    private TextView mEdit;
    private TextView mAdd;
    private TextView mAlarm_select;
    
    private List<Alarm> mAlarmList;

    private HomeAlarmViewAdapter recyclerViewAdapter;
    private EditScreenAlarmAdapter editAdapter;
    private boolean edit = false;
    private boolean mRefresh = false;
    private boolean mEditRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initSetup();
    }

    /**Bind view method*/
    private void bindViews(){
        toolbar         = findViewById(R.id.toolbar);
        mEdit           = findViewById(R.id.edit);
        mAdd            = findViewById(R.id.add);
        mRecyclerView   = findViewById(R.id.recyclayout);
        mAlarm_select   = findViewById(R.id.alarm_select);
    }

    /**Initial setups method*/
    private void initSetup(){
        LinearLayoutManager mLinearLayoutManager;
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleItemDividerForDecoration(this));
        mAlarmList = new ArrayList<>();

        mAlarmPresenter = new AlarmPresenter(this, this);
        mAlarmPresenter.getAllAlarms();//get all alarms

        setSupportActionBar(toolbar);
        mEdit.setOnClickListener(this);
        mAdd.setOnClickListener(this);
    }


    /**
     * Called after {@link #onStop} when the current activity is being
     * re-displayed to the user (the user has navigated back to it).  It will
     * be followed by {@link #onStart} and then {@link #onResume}.
     * <p>
     * <p>For activities that are using raw {@link } objects (instead of
     * creating them through
     * {@link #managedQuery(Uri, String[], String, String[], String)},
     * this is usually the place
     * where the cursor should be requeried (because you had deactivated it in
     * {@link #onStop}.
     * <p>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onStop
     * @see #onStart
     * @see #onResume
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        if (mRefresh){
            mAlarmPresenter.getAllAlarms();//get all alarms
        }
        if (mEditRefresh){
            mAlarmPresenter.getAllAlarms();//get all alarms again
        }

        edit = false;
        mAdd.setText(R.string.add);
        setAdapter(recyclerViewAdapter);
        mAlarm_select.setText(R.string.alarm);
    }

    @Override
    public void showAlarms(List<Alarm> alarms) {
        mAlarmList = alarms;
        recyclerViewAdapter = new HomeAlarmViewAdapter(this, alarms, this);
        setAdapter(recyclerViewAdapter);
    }

    /**
     * Method to set adapter to the two recycler view
     * the method is being used by two different recycler views*/
    private void setAdapter(HomeAlarmViewAdapter recyclerViewAdapter){
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void showAddAlarm() {
        //Start AddAlarmActivity
        AddAlarmActivity.startActivity(this, new AddAlarmContract.SaveAlarmCallBack() {
            @Override
            public void onAlarmSaveCallBack( boolean value) {
                mRefresh = (value);
            }
        });
    }
    
    @Override
    public void showAlarmEditScreen(@NonNull List<Alarm> alarmList ) {

        AlarmContract.AlarmItemClickListener mAlarmItemClickListener = new AlarmContract.AlarmItemClickListener(){
            @Override
            public void onAlarmClicked(@NonNull Alarm clickedAlarm){
                EditAlarmActivity.startActivity(AlarmMainActivity.this, clickedAlarm, new EditAlarmContract.SaveAlarmCallBack() {
                    @Override
                    public void onAlarmSaveCallBack(@Nullable boolean value) {
                        mEditRefresh = value;
                    }
                });
            }
        };

        editAdapter = new EditScreenAlarmAdapter(alarmList, mAlarmItemClickListener);
        setEditAdapter(editAdapter);
        mAlarm_select.setText(R.string.select_to_edit);
    }

    private void setEditAdapter(EditScreenAlarmAdapter editAdapter){
        mRecyclerView.setAdapter(editAdapter);
        mRecyclerView.addItemDecoration(new SimpleItemDividerForDecoration(this));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.edit:
                edit = true;
                mAdd.setText(R.string.cancel);
                mAlarmPresenter.openEditAlarmScreen(mAlarmList);
                break;
            case R.id.add:
                if (!edit) {
                    mAlarmPresenter.addAlarm();
                }
                else {
                    edit = false;
                    mAdd.setText(R.string.add);
                    setAdapter(recyclerViewAdapter);
                    mAlarm_select.setText(R.string.alarm);
                }
                break;
        }
    }


    /********************************************************************************************/
}
