package com.olsttech.myalarm.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.R;

import java.util.List;

/**
 * Created by adetunji on 20/08/2018.class AlarmRecyclerViewAdapter
 */

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {
    protected Context mContext;
    public List<Alarm> mAlarmList;
    private Activity mActivity;

    public AlarmRecyclerViewAdapter(Context context, List<Alarm> alarmList, Activity activity){
        this.mContext 	    = context;
        this.mAlarmList 	= alarmList;
        this.mActivity      = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewholder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_list, parent, false);
        viewholder = new ViewHolder(view);
        return  viewholder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {
        holder.mTime.setText(String.valueOf(mAlarmList.get(position).getAlamTime()));
        holder.mDay.setText(mAlarmList.get(position).getAlarmDay());
        holder.mLabel.setText(mAlarmList.get(position).getAlarmLabel());
       // holder.mStatus = mAlarmList.get(position).isAlarmStatus();

        final int pos = position;
        //holder.getAdapterPosition();

       /** holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Uri uri = Uri.parse(mAlarmList.get(pos).getLink());
               // mActivity.startActivity(startUrlIntent(uri));
            }
        });*/

    }

    public Intent startUrlIntent(Uri uri){
        return new Intent(Intent.ACTION_VIEW, uri);
    }


    @Override
    public int getItemCount() {
        if (mAlarmList != null) {
            return this.mAlarmList.size();
        }else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTime;
        public TextView mDay;
        public TextView mLabel;
        public RadioButton mStatus;
        public ImageView mForward;
        public ImageView mDeleteIcon;

        public ViewHolder(final View itemView){
            super(itemView);
            mTime 	    = (TextView)itemView.findViewById(R.id.alarm_time);
            mDay 	= (TextView)itemView.findViewById(R.id.alarm_date);
            mLabel 	= (TextView)itemView.findViewById(R.id.alarm_label);
            mStatus  = (RadioButton) itemView.findViewById(R.id.alarm_status);
            mDeleteIcon  = (ImageView) itemView.findViewById(R.id.alarm_deleteicon);
            mForward  = (ImageView) itemView.findViewById(R.id.alarm_forward);
            
            //mDeleteIcon.setVisibility(View.INVISIBLE);
            //mForward.setVisibility(View.INVISIBLE);
        }
    }

}
