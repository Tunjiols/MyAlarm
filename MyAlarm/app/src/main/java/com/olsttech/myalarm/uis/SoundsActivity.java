package com.olsttech.myalarm.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.models.SoundModel;

import java.util.List;

public class SoundsActivity extends AppCompatActivity implements SoundsContract.View, View.OnClickListener{

    private RecyclerView mSoundListView;
    private List<SoundModel> mSoundsList;
    private SoundsContract.Presenter presenter;

    private TextView mCancel;
    private TextView mSave;

    private SoundModel mSound;
    private static SoundModel mSetSound;

    private static SoundsContract.SoundsCallBack mSoundCallback;
    
    public static void startActivity(Context context, int flag, SoundModel setSound, SoundsContract.SoundsCallBack soundCallback){
        Intent intent = new Intent(context, SoundsActivity.class);
        intent.setFlags(flag);
        mSetSound = setSound;
        mSoundCallback = soundCallback;
        
        context.startActivity(intent);
    }
    
    @Override
    public void onCreate(@Nullable Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.sounds_activity);
        
        bindView();
        initSetup();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    private void bindView(){

        mSoundListView = (RecyclerView) findViewById(R.id.recyclayout);
        mCancel = (TextView) findViewById(R.id.cancel);
        mSave = (TextView) findViewById(R.id.save);
    }
    
    private void initSetup(){
        presenter = new SoundsPresenter(this);
        presenter.getSounds(mSetSound); //get sounds from database

        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }
    
    @Override
    public void loadView(@NonNull final List<SoundModel> soundsList){
        SoundsContract.ClickListener soundClickListener = new SoundsContract.ClickListener(){
            @Override
            public void onSoundSelect(SoundModel soundModel){
                 if(!soundModel.getChecked()) {
                     soundModel.setChecked(true);

                     //loop through all the sounds and check only the clicked sound
                     for (SoundModel checkedSound : soundsList){
                         if (!checkedSound.equals(soundModel)){
                             checkedSound.setChecked(false);
                         }
                     }
                     mSound = soundModel;
                 }
                 else
                     soundModel.setChecked(false);
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getParent());
        SoundsListAdapter soundsListAdapter = new SoundsListAdapter(soundsList, soundClickListener);
        mSoundListView.setLayoutManager(linearLayoutManager);
        mSoundListView.setAdapter(soundsListAdapter);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save:
                if (null != mSound) {
                    mSoundCallback.callBack(mSound);
                    finish();
                }else {
                    mSoundCallback.callBack(mSetSound);
                    finish();
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    /********************************************************************************************/
    private class SoundsListAdapter extends RecyclerView.Adapter<SoundsListAdapter.ViewHolder> {
        
        private List<SoundModel> mSoundsList;
        private SoundsContract.ClickListener mSoundClickListener;


        public SoundsListAdapter(List<SoundModel> soundsList, SoundsContract.ClickListener soundClickListener){
            this.mSoundsList = soundsList;
            this.mSoundClickListener = soundClickListener;
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
            View view = inflater.inflate(R.layout.sounds_list, parent, false);
            return new ViewHolder(view, mSoundClickListener);
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         */
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
            viewHolder.mText.setText(mSoundsList.get(position).getSound());
            viewHolder.mCheckBox.setChecked(mSoundsList.get(position).getChecked());
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            if (null != mSoundsList)
                return mSoundsList.size();
            else
                return 0;
        }


        /*****************************************************************************************/
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
            public TextView mText;
            public CheckBox mCheckBox;
            private SoundsContract.ClickListener mSoundClickListener;
            
            public ViewHolder(final View view, SoundsContract.ClickListener soundClickListener){
                super(view);
                this.mSoundClickListener = soundClickListener;
                mText = (TextView)view.findViewById(R.id.text);
                mCheckBox = (CheckBox)view.findViewById(R.id.checkBox);

                mCheckBox.setOnClickListener(this);
            }
            
            @Override
            public void onClick(View v){
                int position = getAdapterPosition();
                SoundModel soundModel = mSoundsList.get(position);
                mSoundClickListener.onSoundSelect(soundModel);
            }
        }
    } 
}
