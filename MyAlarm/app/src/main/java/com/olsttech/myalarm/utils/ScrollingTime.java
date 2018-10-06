package com.olsttech.myalarm.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.olsttech.myalarm.editAlarm.EditAlarmContract;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.functions.Action1;

public class ScrollingTime {

    private RecyclerView mRecyclerView;
    private TimeTracking mTimeTrackings;

    public ScrollingTime(RecyclerView recyclerView, final TimeTracking timeTrackings ) {
        this.mRecyclerView = recyclerView;
        this.mTimeTrackings = timeTrackings;
    }


    public void scrollView(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0){
                    //mRecyclerView.scrollBy(0, 1);
                }else if (dy < 0){
                    //mRecyclerView.scrollBy(0, -1);
                }

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();

               // int name =recyclerView.getLayoutManager().getFocusedChild().getId();
                TimeTracking.NowVisible visible = new TimeTracking.NowVisible(dy, dx);
                //TimeTracking timeTracking = mTimeTrackings;

                mTimeTrackings = new TimeTracking(
                        new Action1<TimeTracking.NowVisible>() {
                            @Override
                            public void call(TimeTracking.NowVisible visible) {
                                int position = visible.getFirstVisible();
                                int count = recyclerView.getLayoutManager().getItemCount();
                                int v = recyclerView.getLayoutManager().getPaddingTop();
                                View vv = mRecyclerView.getLayoutManager().getChildAt(3);

                                //TODO get my outputs

                                //int name = recyclerView.getLayoutManager().getFocusedChild();//.getName();
                                //mAlarmTime = 0000;
                               // Log.e("TimeTrac get1stVisible", String.valueOf(position));
                              //  Log.e("TimeTrac Adapter_count", String.valueOf(count));
                              // Log.e("TimeTrac viewId", String.valueOf(vv.getId()));
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable s) {
                              //  Log.e("Error getting the time", s.getMessage());
                            }
                        }
                );

                mTimeTrackings.postViewEvent(visible);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                //---------------------------------------------------

            }

            /**
             * Callback method to be invoked when RecyclerView's scroll state changes.
             *
             * @param recyclerView The RecyclerView whose scroll state has changed.
             * @param newState     The updated scroll state. One of {@link #SCROLL_STATE_IDLE},
             *                     {@link #SCROLL_STATE_DRAGGING} or {@link #SCROLL_STATE_SETTLING}.
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
}
