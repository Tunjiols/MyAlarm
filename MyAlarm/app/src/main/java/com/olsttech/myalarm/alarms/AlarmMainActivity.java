package com.olsttech.myalarm.alarms;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.adapters.AlarmRecyclerViewAdapter;
import com.olsttech.myalarm.addAlarm.AddAlarmActivity;
import com.olsttech.myalarm.addAlarm.AddAlarmContract;
import com.olsttech.myalarm.editAlarm.EditAlarmActivity;
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
    private LinearLayoutManager mLinearLayoutManager;
    
    private List<Alarm> mAlarmList;

    private AlarmRecyclerViewAdapter recyclerViewAdapter;
    private EditAlarmAdapter editAdapter;
    private boolean edit = false;
    private boolean mRefresh = false;

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
       // if (mRefresh){
            mAlarmPresenter.getAllAlarms();//get all alarms
       // }

        edit = false;
        mAdd.setText("Add");
        setAdapter(recyclerViewAdapter);
        mAlarm_select.setText("Alarm");
    }

    @Override
    public void showAlarms(List<Alarm> alarms) {
        mAlarmList = alarms;
        recyclerViewAdapter = new AlarmRecyclerViewAdapter(this, alarms, this);
        setAdapter(recyclerViewAdapter);
    }

    private void setAdapter(AlarmRecyclerViewAdapter recyclerViewAdapter){
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
                EditAlarmActivity.startActivity(AlarmMainActivity.this, clickedAlarm);
            }
        };

        editAdapter = new EditAlarmAdapter(alarmList, mAlarmItemClickListener);
        setEditAdapter(editAdapter);
        mAlarm_select.setText("Select to  Edit");
    }

    private void setEditAdapter(EditAlarmAdapter editAdapter){
        mRecyclerView.setAdapter(editAdapter);
        mRecyclerView.addItemDecoration(new SimpleItemDividerForDecoration(this));
    }

    @Override
    public void openEditAlarm(@NonNull Alarm alarm) {
        //open alarm edit activity
        //EditAlarmActivity.startActivity(this, alarm);
    }

    @Override
    public void showAlarmRadioBtn() {

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.edit:
                edit = true;
                mAdd.setText("Cancel");
                mAlarmPresenter.openEditAlarmScreen(mAlarmList);
                break;
            case R.id.add:
                if (!edit) {
                    mAlarmPresenter.addAlarm();
                }
                else {
                    edit = false;
                    mAdd.setText("Add");
                    setAdapter(recyclerViewAdapter);
                    mAlarm_select.setText("Alarm");
                }
                break;
        }
    }


    /********************************************************************************************/

    private static class EditAlarmAdapter extends RecyclerView.Adapter<EditAlarmAdapter.EditViewHolder>{
    
        private List<Alarm> mAlarmList;
        private AlarmContract.AlarmItemClickListener mAlarmItemClickListener;
        
        public EditAlarmAdapter(List<Alarm> alarmList, AlarmContract.AlarmItemClickListener alarmItemClickListener){
            this.mAlarmItemClickListener = alarmItemClickListener;
            this.mAlarmList = alarmList;
        }

        @NonNull
        @Override
        public EditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.edit_recycler_list, parent, false);
            
            return new EditViewHolder(view, mAlarmItemClickListener);
        }
        
        @Override
        public void onBindViewHolder(@NonNull EditViewHolder editViewHolder, int position) {
            //Alarm alarm = mAlarmList.get(position);

            editViewHolder.mTime.setText(String.valueOf(mAlarmList.get(position).getAlamTime()));
            editViewHolder.mDay.setText(mAlarmList.get(position).getAlarmDay());
            editViewHolder.mLabel.setText(mAlarmList.get(position).getAlarmLabel());
           // editViewHolder.mStatus = mAlarmList.get(position).isAlarmStatus();
           
        }
        
        @Override
        public int getItemCount() {
            if (mAlarmList != null) {
                return this.mAlarmList.size();
            }else return 0;
        }

        /*****************************************************************************************/
        public class EditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            
            public TextView mTime;
            public TextView mDay;
            public TextView mLabel;
            public ImageView mStatus;
            public ImageView mDeleteIcon;
            public ImageView mForward;
            //public ImageView mItemDelete
            private AlarmContract.AlarmItemClickListener mAlarmItemClickListener;
            
            private EditViewHolder(View itemView, AlarmContract.AlarmItemClickListener alarmItemClickListener){
                super(itemView);
                mAlarmItemClickListener = alarmItemClickListener;
                mTime 	    = itemView.findViewById(R.id.alarm_time);
                mDay 	= itemView.findViewById(R.id.alarm_date);
                mLabel 	= itemView.findViewById(R.id.alarm_label);
                mStatus  =  itemView.findViewById(R.id.alarm_status);
                mDeleteIcon  =  itemView.findViewById(R.id.alarm_deleteicon);
                mForward  =  itemView.findViewById(R.id.alarm_forward);

                itemView.setOnClickListener(this);
            }
            
            @Override
            public void onClick(View v){
                int position = getAdapterPosition();
                Alarm alarm = mAlarmList.get(position);
                mAlarmItemClickListener.onAlarmClicked(alarm);
            }
        }
    }
}
