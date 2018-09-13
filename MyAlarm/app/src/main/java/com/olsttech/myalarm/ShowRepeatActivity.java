package com.olsttech.myalarm.addAlarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ShowRepeatActivity extends AppCompatActivity{

    private ListView mDayListView;
    private List<Srting> mDayList;
    
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
            return view;
        }
        
        private class ViewHolder implements View.OnClickListener {
        
            private TextView mText;
            private CheckBox mCheckBox;
            
            public ViewHolder(View view, DayClickListener dayClickListener){
                mText = view.findViewById(R.id.text);
                mCheckBox = view.findViewById(R.id.checkbox);
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
    
    private class DayModel{
        private String mDay;
        private boolean mChecked;
        
        public DayModel(String day, boolean checked){
            this.mDay = day;
            this.mChecked = checked;
        }
        
        public String getDay(){
            return mDay;
        }
        
        public void setDay(String day){
            this.mDay = day;
        }
        
        public boolean getChecked(){
            return mChecked;
        }
        
        public void setChecked(boolean checked){
            this.mChecked = checked;
        }
        
    }
    
    interface DayClickListener{
        void onDayClicked(DayModel dayModel);
    }
}
