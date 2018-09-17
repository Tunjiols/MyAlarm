package com.olsttech.myalarm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olsttech.myalarm.R;

import java.util.List;

/**
 * Created by adetunji on 20/08/2018.class HourRecyclerAdapter
 */

public class HourRecyclerAdapter extends RecyclerView.Adapter<HourRecyclerAdapter.ViewHolder> {
    protected Context mContext;
    private List<String> hourTime;

    private String[] hourTimes;
   
    
    public HourRecyclerAdapter(Context context, List<String> time){
        this.mContext 	    = context;
        this.hourTime = time;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewholder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_view, parent, false);
        viewholder = new ViewHolder(view);
        return  viewholder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mTime.setText(hourTime.get(position));
    }

    @Override
    public int getItemCount() {
        if (hourTime != null)
            return this.hourTime.size();
        else
            return 0;
    }


    /*******************************************************************************************/
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
                //DayModel dayModel = mDayList.get(position);
                //mDayClickListener.onDayClicked(dayModel);
        }
    }

}
