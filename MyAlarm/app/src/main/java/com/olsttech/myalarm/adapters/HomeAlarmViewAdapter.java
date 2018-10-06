package com.olsttech.myalarm.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.olsttech.myalarm.alarms.AlarmContract;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.R;
import com.olsttech.myalarm.utils.AlarmConstants;

import java.util.List;

/**
 * Created by adetunji on 20/08/2018.class HomeAlarmViewAdapter
 */

public class HomeAlarmViewAdapter extends RecyclerView.Adapter<HomeAlarmViewAdapter.AlarmViewHolder> {
    protected Context mContext;
    public List<Alarm> mAlarmList;
    private Activity mActivity;
    private AlarmContract.OnEnableStateChanged mOEnableStateChanged;


    public HomeAlarmViewAdapter(Context context, List<Alarm> alarmList, Activity activity){
        this.mContext 	    = context;
        this.mAlarmList 	= alarmList;
        this.mActivity      = activity;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlarmViewHolder viewholder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_list, parent, false);
        viewholder = new AlarmViewHolder(view);
        return  viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmViewHolder holder,  int position) {
        holder.mHour.setText(String.valueOf(mAlarmList.get(position).getAlarmHour() + ":"));
        holder.mMinute.setText(String.valueOf(mAlarmList.get(position).getAlarmMinute()));
        holder.mDay.setText(mAlarmList.get(position).getAlarmDay());
        holder.mLabel.setText(mAlarmList.get(position).getAlarmLabel());

        if(mAlarmList.get(position).getAlarmStatus()){
            holder.mSnooze.setVisibility(View.VISIBLE);
        }else {
            holder.mSnooze.setVisibility(View.INVISIBLE);
        }

        if(!mAlarmList.get(position).isAlarmEnabled()){
            holder.mStatus.setImageResource(R.drawable.ic_status);
       }else {
            holder.mStatus.setImageResource(R.drawable.ic_status_off);
        }

        holder.mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlarmList.get(position).isAlarmEnabled()) {
                    mAlarmList.get(position).setAlarmEnabled(false);
                   mOEnableStateChanged.onEnableChanged(false);
                    holder.mStatus.setImageResource(R.drawable.ic_status_off);
                } else {
                    mAlarmList.get(position).setAlarmEnabled(true);
                    mOEnableStateChanged.onEnableChanged(true);
                    holder.mStatus.setImageResource(R.drawable.ic_status);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mAlarmList != null) {
            return this.mAlarmList.size();
        }else return 0;
    }

    /**
     * View Class for HomeAlarmViewAdapter.
     *  The view that was clicked.
     */
    public class AlarmViewHolder extends RecyclerView.ViewHolder{
        public TextView mHour;
        public TextView mMinute;
        public TextView mDay;
        public TextView mLabel;
        public TextView mSnooze;
        public ImageButton mStatus;

        public AlarmViewHolder(final View itemView){
            super(itemView);
            mHour 	= itemView.findViewById(R.id.alarm_hour);
            mMinute = itemView.findViewById(R.id.alarm_min);
            mDay 	= itemView.findViewById(R.id.alarm_date);
            mLabel 	= itemView.findViewById(R.id.alarm_label);
            mStatus = itemView.findViewById(R.id.alarm_status);
            mSnooze = itemView.findViewById(R.id.alarm_snooze);

            mSnooze.setVisibility(View.INVISIBLE);
        }
    }

}
