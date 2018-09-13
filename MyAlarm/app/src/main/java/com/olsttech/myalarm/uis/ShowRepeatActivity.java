package com.olsttech.myalarm.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.models.DayModel;

import java.util.List;

public class ShowRepeatActivity extends AppCompatActivity implements ShowRepeatContract.View{

    private RecyclerView mDayListView;
    private List<String> mDayList;
    private ShowRepeatContract.Presenter presenter;
    
    public void startActivity(Context context, int flag){
        Intent intent = new Intent(context, ShowRepeatActivity.class);
        intent.setFlags(flag);
        
        context.startActivity(intent);
    }
    
    @Override
    public void onCreate(@Nullable Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.showreepeat_activity);
        
        bindView();
        initSetup();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    private void bindView(){

        mDayListView = (RecyclerView) findViewById(R.id.recyclayout);
    }
    
    private void initSetup(){
        presenter = new ShowRepeatPresenter(this);
        presenter.setView();
    }
    
    @Override
    public void showView(@NonNull List<DayModel> getDayList){
        DayClickListener dayClickListener = new DayClickListener(){
            @Override
            public void onDayClicked(DayModel dayModel){
                 if(!dayModel.getChecked())
                     dayModel.setChecked(true);
                 else
                     dayModel.setChecked(false);
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getParent());
        DayListAdapter dayListAdapter = new DayListAdapter(getDayList, dayClickListener);
        mDayListView.setLayoutManager(linearLayoutManager);
        mDayListView.setAdapter(dayListAdapter);
    }
    
    private class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {
        
        private List<DayModel> mDayList;
        private DayClickListener mDayClickListener;


        public DayListAdapter(List<DayModel> dayList, DayClickListener dayClickListener){
            this.mDayList = dayList;
            this.mDayClickListener = dayClickListener;
        }

        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         * {@link (ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ViewHolder, int)
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.showrepeat_list, parent, false);
            return new ViewHolder(view, mDayClickListener);
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         */
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

            viewHolder.mText.setText(mDayList.get(position).getDay());
            viewHolder.mCheckBox.setChecked(mDayList.get(position).getChecked());
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return 0;
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
            public TextView mText;
            public CheckBox mCheckBox;
            private DayClickListener mDayClickListener;
            
            public ViewHolder(final View view, DayClickListener dayClickListener){
                super(view);
                this.mDayClickListener = dayClickListener;
                mText = (TextView)view.findViewById(R.id.text);
                mCheckBox = (CheckBox)view.findViewById(R.id.checkBox);
                
                view.setOnClickListener(this);
            }
            
            @Override
            public void onClick(View v){
                int position = getAdapterPosition();
                DayModel dayModel = mDayList.get(position);
                mDayClickListener.onDayClicked(dayModel);
            }
        }
    } 
    
    
    interface DayClickListener{
        void onDayClicked(DayModel dayModel);
    }
}
