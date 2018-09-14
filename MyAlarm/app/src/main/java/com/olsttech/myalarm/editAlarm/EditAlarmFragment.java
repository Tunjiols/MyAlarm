package com.olsttech.myalarm.editAlarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olsttech.myalarm.R;

/**
 * Created by adetunji on 01/09/2018.EditAlarmFragment
 */

public class EditAlarmFragment extends Fragment implements EditAlarmContract.View,
                    View.OnClickListener{

    private TextView mCancel;
    private TextView mSave;
    private TextView mRepeat_value;
    private TextView mLabel_value;
    private TextView mSound_value;
    private Button mSnoozeBtn;
    
    private EditAlarmPresenter mEditAlarmPresenter;

    public static EditAlarmFragment newInstance() {
        mEditAlarmPresenter = new EditAlarmPresenter(this);
        return new EditAlarmFragment();
    }

    public EditAlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addalarm, container, false);
            mCancel = rootView.findViewById(R.id.cancel);
            mSave = rootView.findViewById(R.id.save);
            mRepeat_value = rootView.findViewById(R.id.repeat_value);
            mLabel_value = rootView.findViewById(R.id.label_value);
            mSound_value = rootView.findViewById(R.id.sound_value);
            mSnoozeBtn = rootView.findViewById(R.id.snoozeBtn);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        return rootView;
    }

    @Override
    public void showRepeatScreen() {

    }

    @Override
    public void showLabelScreen() {

    }

    @Override
    public void showSoundsListScreen() {

    }
    
    @Override
    public boolean setSnooze(){
        return true;
    }
    
    public void onClick(View v){
        switch(v.getId()){
            case R.id.cancel:
                
                break;
            case R.id.save:
                
                break;
            case R.id.label_layout:
            
                break;
            case R.id.sound_layout:
                
                break;
            case R.id.snooze_layout:
                
                break
        }
    
    }
}
