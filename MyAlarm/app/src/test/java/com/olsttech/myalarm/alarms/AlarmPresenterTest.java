package com.olsttech.myalarm.alarms;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;

import com.google.common.collect.Lists;
import com.olsttech.myalarm.data.AlarmDataManager;
import com.olsttech.myalarm.data.AlarmDataManagerApi.DataManagerLoadAlarmCallBack;
import com.olsttech.myalarm.data.AlarmDataManagerApi;
import com.olsttech.myalarm.models.Alarm;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.PublicKey;
import java.util.List;

import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by adetunji on 11/09/2018. Unit tests for the implementation of {@link AlarmPresenter)
 */

public class AlarmPresenterTest {

    private Context context;
    private List<Alarm> mTestAlarmList = Lists.newArrayList( new Alarm(1130,
            "alarmLabel", "alarmDay", "alarmSound", true ));

    @Mock
    private AlarmContract.View mView;

    @Mock
    private AlarmDataManager alarmDataManager = new AlarmDataManager(context);

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<AlarmDataManagerApi.DataManagerLoadAlarmCallBack> mArgumentCaptorCallback;
    private AlarmPresenter mAlarmPresenter;


    @Before
    public void setUpAlarmPresenter(){
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        context = null;
        // Get a reference to the class under test
        mAlarmPresenter = new AlarmPresenter( mView, context);

    }

    @Test
    public void getAllAlarms_ShowTest(){
        //fail("Implement in step 6");
        mAlarmPresenter.getAllAlarms();

        verify(alarmDataManager).getAllAlarms(mArgumentCaptorCallback.capture());
        mArgumentCaptorCallback.getValue().onAlarmLoaded(mTestAlarmList);
        verify(mView).showAlarms(mTestAlarmList);
    }

    @Test
    public void addAlarm_ShowTest(){
       // fail("Implement in step 6");
        mAlarmPresenter.addAlarm();
        verify(mView).showAddAlarm();
    }

    @Test
    public void openEditAlarmScreen_ShowTest(){
        //fail("Implement in step 6");
        mAlarmPresenter.openEditAlarmScreen(mTestAlarmList);
        verify(mView).showAlarmEditScreen(mTestAlarmList);
    }

    @Test
    public void getAlarmRadioBtnStatus_ShowTest(){
        //fail("Implement in step 6");
        mAlarmPresenter.getAlarmRadioBtnStatus();
        verify(mView).showAlarmRadioBtn();
    }

    @Test
    public void getAlarm_ShowTest() {
        fail("Implement in step 6");
    }
}

