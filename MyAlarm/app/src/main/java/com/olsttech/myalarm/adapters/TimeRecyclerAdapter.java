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
 * Created by adetunji on 20/08/2018.class TimeRecyclerAdapter
 */

public class TimeRecyclerAdapter extends RecyclerView.Adapter<TimeRecyclerAdapter.HourViewHolder> {
    protected Context mContext;
    private List<String> hourTime;
    private OnTimeFocusListener mOnTimeFocusListener;

   public TimeRecyclerAdapter(Context context, List<String> time, OnTimeFocusListener onTimeFocusListener){
        this.mContext 	    = context;
        this.hourTime = time;
        this.mOnTimeFocusListener = onTimeFocusListener;
    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HourViewHolder viewholder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_view, parent, false);
        viewholder = new HourViewHolder(view, mOnTimeFocusListener);
        return  viewholder;
    }


    @Override
    public void onBindViewHolder(final HourViewHolder holder, final int position) {
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
    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTime;
        private OnTimeFocusListener mOnTimeFocusListener;

        public HourViewHolder(final View itemView, OnTimeFocusListener onTimeFocusListener){
            super(itemView);
            this.mOnTimeFocusListener = onTimeFocusListener;
            mTime = (TextView)itemView.findViewById(R.id.hour_time);
            mTime.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnTimeFocusListener.onTimeFocus(hourTime.get(position));
        }
    }
    public interface OnTimeFocusListener{
        void onTimeFocus(String getFocusTime);
    }
}
