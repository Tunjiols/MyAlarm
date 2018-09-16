package com.olsttech.myalarm.addAlarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.olsttech.myalarm.R;

/**
 * Created by adetunji on 01/09/2018.AddAlarmActivity
 */

public class AddAlarmActivity extends AppCompatActivity{

    private static AddAlarmContract.SaveAlarmCallBack mOnAlarmSave;

    public static void startActivity(Context context, AddAlarmContract.SaveAlarmCallBack onAlarmsave){
        Intent intent = new Intent(context, AddAlarmActivity.class);
        mOnAlarmSave = onAlarmsave;
        context.startActivity(intent);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addalarm);

        if (null == savedInstanceState) {
            initFragment(AddAlarmFragment.newInstance(mOnAlarmSave));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // if (getFragmentManager().getBackStackEntryCount() == 0)
       //     this.finish();
       // else
       //     getFragmentManager().popBackStack();

    }

    private void initFragment(Fragment addFragment) {
        // Add the AddNoteFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, addFragment);
        transaction.commit();
    }
/**
    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
    */
}
