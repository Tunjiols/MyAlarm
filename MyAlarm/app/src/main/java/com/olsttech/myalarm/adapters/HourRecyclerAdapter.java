package com.olsttech.myalarm.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by adetunji on 20/08/2018.class HourRecyclerAdapter
 */

public class HourRecyclerAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {
    protected Context mContext;
    private Activity mActivity;

    private int[] hourTime = {01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,00};
   
    
    public HourRecyclerAdapter(Context context, Activity activity){
        this.mContext 	    = context;
        this.mActivity      = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewholder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_time_view, parent, false);
        viewholder = new ViewHolder(view);
        return  viewholder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTime.setText(hourTime.get(posoition));
    }

    @Override
    public int getItemCount() {
        return hourTime.size();    
    }

    /**
     * View Class for AlarmRecyclerViewAdapter.
     *
     *  The view that was clicked.
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener{
        public TextView mTime;

        public ViewHolder(final View itemView){
            super(itemView);
            mTime = (TextView)itemView.findViewById(R.id.hour_time);
        }
        
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                ((TextView)view).getText();
            }
                int position = getAdapterPosition();
                DayModel dayModel = mDayList.get(position);
                mDayClickListener.onDayClicked(dayModel);
        }
    }

}
