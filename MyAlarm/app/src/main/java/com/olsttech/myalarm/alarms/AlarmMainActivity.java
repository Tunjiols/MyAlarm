package com.olsttech.myalarm.alarms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.adapters.AlarmRecyclerViewAdapter;
import com.olsttech.myalarm.models.Alarm;

import java.util.List;

public class AlarmMainActivity extends AppCompatActivity implements AlarmContract.View,  
                View.OnClickListener{
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private AlarmContract.Presenter mAlarmPresenter;
    private RecyclerView mRecyclerView;
    private TextView mEdit;
    private TextView mAdd;
    private LinearLayoutManager mLinearLayoutManager;
    
    private List<Alarm> mAlarmList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initSetup();
    }

    /**Bind view method*/
    private void bindViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclayout);
        mEdit = (TextView) findViewById(R.id.edit);
        mAdd = (TextView) findViewById(R.id.add);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    /**Initial setups method*/
    private void initSetup(){
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAlarmList = new ArrayList<Alarm>();

        mAlarmPresenter = new AlarmPresenter(this, this);
        mAlarmPresenter.getAllAlarms();

        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void showAlarms(List<Alarm> alarms) {
    
        mAlarmList = alarms;
        
        AlarmRecyclerViewAdapter recyclerViewAdapter = new AlarmRecyclerViewAdapter(this,
                alarms, this);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void showAddAlarm() {
        //Start AddAlarmActivity
        AddAlarmActivity.startActivity(getContext());
    }
    
    @Override
    public void showAlarmEditScreen(String alarmId, @NonNull Alarm alarmList) {
        
        AlarmItemClickListener mAlarmItemClickListener = new AlarmItemClickListener(){
            @Override
            public void onAlarmClicked(@NonNull Alarm clickedAlarm){
                mAlarmPresenter.openEditAlarmScreen(clickedAlarm);
            }
        }
        
        EditAlarmAdapter editAdapter = new EditAlarmAdapter(alarmList, mAlarmItemClickListener);
    }

    @Override
    public void showEditAlarmScreen(@NonNull Alarm alarm) {
        //open alarm edit activity
        EditAlarmActivity.startActivity(getContext());
    }

    @Override
    public void showAlarmRadioBtn() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.edit:
                
                mAlarmPresenter.editAlarm(mAlarmList);
               
                break;
            case R.id.add:
               // new AlarmContract.AddAlarmClickListener() {
               //     @Override
                //    public void addNewAlarm() {
                        mAlarmPresenter.addAlarm();
               //     }
              //  };
                break;
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    priavate static class EditAlarmAdapter extends RecyclerView.Adapter<EditAlarmAdapter.ViewHolder>{
    
        private List<Alarm> mAlarmList;
        private AlarmContract.AlarmItemClickListener mAlarmItemClickListener;
        
        public EditAlarmAdapter(List<Alarm> alarmList, AlarmContract.AlarmItemClickListener alarmItemClickListener){
            this.mAlarmItemClickListenerr = alarmItemClickListener;
            this.mAlarmList = alarmList;
        }
        
        @Override
        public ViewHolder onCreateViewholder(ViewGroup parent, int viewType ){
            Context context = parent.getCntext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.home_recycler_list, parent, false);
            
            return new ViewHolder(view, mAlarmItemClickListener);
        }
        
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Alarm alarm = mAlarmList.get(position);
            
            holder.mTime.setText(String.valueOf(mAlarmList.get(position).getAlamTime()));
            holder.mDay.setText(mAlarmList.get(position).getAlarmDay());
            holder.mLabel.setText(mAlarmList.get(position).getAlarmLabel());
           // holder.mStatus = mAlarmList.get(position).isAlarmStatus();
           
        }
        
        @Override
        public int getItemCount() {
            if (mAlarmList != null) {
                return this.mAlarmList.size();
            }else return 0;
        }
         
        public class ViewHolder extends RecyclerView.View, implements View.OnclickListener{
            
            public TextView mTime;
            public TextView mDay;
            public TextView mLabel;
            public ImageView mStatus;
            public ImageView mDeleteIcon;
            public ImageView mForward;
            //public ImageView mItemDelete
            public AlarmContract.AlarmItemClickListener mAlarmItemClickListener;
            
            public ViewHolder(View itemView, AlarmContract.AlarmItemClickListener alarmItemClickListener){
                super(itemView);
                mAlarmItemClickListener = alarmItemClickListener;
                mTime 	    = (TextView)itemView.findViewById(R.id.alarm_time);
                mDay 	= (TextView)itemView.findViewById(R.id.alarm_date);
                mLabel 	= (TextView)itemView.findViewById(R.id.alarm_label);
                mStatus  = (ImageView) itemView.findViewById(R.id.alarm_status);
                mDeleteIcon  = (ImageView) itemView.findViewById(R.id.alarm_deleteicon);
                mForward  = (ImageView) itemView.findViewById(R.id.alarm_forward);
                //mItemDelete  = (ImageView) itemView.findViewById(R.id.alarm_itemDelete);
                mStatus.setVisibility(View.INVISIBLE):
                
                itemView.setOnclickListener(this);
            }
            
            @Override
            public onClick(View v){
                int position = getAdapterPosition();
                Alarm alarm = getitem(position);
                alarmItemClickListener.onAlarmClicked(alarm);
            }
        }
    }
}
