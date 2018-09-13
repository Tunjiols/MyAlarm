package com.olsttech.myalarm.addAlarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ShowRepeatActivity extends AppCompatActivity implements ShowRepeatContract.View{

    private ListView mDayListView;
    private List<Srting> mDayList;
    private ShowRepeatContract.Presenter presenter;
    
    public void startActivity(Context context, int flag){
        Intent intent = new Intent(context, ShowRepeatActivity.class);
        intent.setFlag(flag);
        
        context.startActivity(intent);
    }
    
    @Override
    public void onCreate(@Nullable Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.);
        
        bindView();
        initSetup();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    private void bindView(){
        mDayListView = (ListView) findViewById(R.id.listview);
    }
    
    private void initSetup(){
        presenter.setView();
    }
    
    @Override
    public void showView(@NonNull List<DayModel> getDayList){
        DayClickListener dayClickListener = DayClickListener(){
            @Override
            public onDayClicked(DayModel dayModel){
                 if(daymodel.getChecked == false)
                     daymodel.setChecked(true);
                 else
                     daymodel.setChecked(false);
            }
        }
        
        DayListAdapter dayListAdapter = new DayListAdpater(getDayList, dayClickListener);
        mDayListView.setApater(dayListAdapter);
    }
    
    private static List<String> getDayList(){
        return new ArrayList<String>(){"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Staturday"}
    }
    
    private class DayListAdapter extends ArrayAdapter<DayListAdapter.ViewHolder>{
        
        private List<String> mDayList;
        private DayClickListener mDayClickListener;

        public DayListAdapter(List<String> dayList, DayClickListener dayClickListener){
            this.mDayList = dayList;
            this.mDayClickListener = dayClickListener;
        }
        
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflate.from(parent.getContext());
            inflater.inflate(R.layout., parent, false);
            
            ViewHolder viewHolder = new ViewHolder(view, mDayClickListener);
            viewHolder.mText.setText(mDayList.get(position).getDay());
            viewHolder.mCheckBox.setBoolean(mDayList.get(position).getChecked());
            
            return view;
        }
        
        private class ViewHolder implements View.OnClickListener {
        
            public TextView mText;
            public CheckBox mCheckBox;
            
            public ViewHolder(View view, DayClickListener dayClickListener){
                mText = (TextView)view.findViewById(R.id.text);
                mCheckBox = (CheckBox)view.findViewById(R.id.checkbox);
                
                view.setOnClickListener(this);
            }
            
            @Override
            public void onClick(View v){
                int position = v.getAdapterPosition();
                DayModel dayModel = v.getItem(position);
                //dayModel = new DayModel(v.get(positon).getText(), v.get(position).get);
                dayClickListener.onDayClicked(dayModel);
            }
        }
    } 
    
    
    interface DayClickListener{
        void onDayClicked(DayModel dayModel);
    }
}
