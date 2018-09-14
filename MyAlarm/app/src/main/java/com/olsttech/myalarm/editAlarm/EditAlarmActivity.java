package com.olsttech.myalarm.editAlarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.models.Alarm;

/**
 * Created by adetunji on 01/09/2018.EditAlarmActivity
 */

public class EditAlarmActivity extends AppCompatActivity {

    private  static Alarm CLICKED_ALARM;
    public static void startActivity(Context context, Alarm clickedAlarm) {
        Intent intent = new Intent(context, EditAlarmActivity.class);
        CLICKED_ALARM = clickedAlarm;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addalarm);

        if (null == savedInstanceState) {
            initFragment(EditAlarmFragment.newInstance(CLICKED_ALARM));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initFragment(Fragment editFragment) {
        // Add the AddNoteFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, editFragment);
        transaction.commit();
    }
/**
    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
    */
}
