package com.olsttech.myalarm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.alarms.AlarmContract;
import com.olsttech.myalarm.models.Alarm;

import java.util.List;

public class EditScreenAlarmAdapter extends RecyclerView.Adapter<EditScreenAlarmAdapter.EditViewHolder>{
    private List<Alarm> mAlarmList;
    private AlarmContract.AlarmItemClickListener mAlarmItemClickListener;

    public EditScreenAlarmAdapter(List<Alarm> alarmList, AlarmContract.AlarmItemClickListener alarmItemClickListener){
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
        editViewHolder.mHour.setText(String.valueOf(mAlarmList.get(position).getAlarmHour() + ":"));
        editViewHolder.mMinute.setText(String.valueOf(mAlarmList.get(position).getAlarmMinute()));
        editViewHolder.mDay.setText(mAlarmList.get(position).getAlarmDay());
        editViewHolder.mLabel.setText(mAlarmList.get(position).getAlarmLabel());

        if(mAlarmList.get(position).getAlarmStatus()){
            editViewHolder.mSnooze.setVisibility(View.VISIBLE);
        }else {
            editViewHolder.mSnooze.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mAlarmList != null) {
            return this.mAlarmList.size();
        }else return 0;
    }

    /*****************************************************************************************/
    public class EditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mHour;
        public TextView mMinute;
        public TextView mDay;
        public TextView mLabel;
        public ImageView mStatus;
        public ImageView mDeleteIcon;
        public ImageView mForward;
        public TextView mSnooze;
        //public ImageView mItemDelete
        private AlarmContract.AlarmItemClickListener mAlarmItemClickListener;

        private EditViewHolder(View itemView, AlarmContract.AlarmItemClickListener alarmItemClickListener){
            super(itemView);
            mAlarmItemClickListener = alarmItemClickListener;
            mHour 	= itemView.findViewById(R.id.alarm_hour);
            mMinute = itemView.findViewById(R.id.alarm_min);
            mDay 	= itemView.findViewById(R.id.alarm_date);
            mLabel 	= itemView.findViewById(R.id.alarm_label);
            mStatus  =  itemView.findViewById(R.id.alarm_status);
            mDeleteIcon  =  itemView.findViewById(R.id.alarm_deleteicon);
            mForward  =  itemView.findViewById(R.id.alarm_forward);
            mSnooze = itemView.findViewById(R.id.alarm_snooze);

            mSnooze.setVisibility(View.INVISIBLE);

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
