package com.olsttech.myalarm.alarms;

import com.olsttech.myalarm.data.AlarmDataManagerApi.DataManagerLoadAlarmCallBack;
import com.olsttech.myalarm.data.AlarmDataManagerApi;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by adetunji on 11/09/2018. Unit tests for the implementation of {@link AlarmPresenter)
 */

public class AlarmPresenterTest {

    private AlarmDataManagerApi mAlarmDataManagerApi;

    @Mock
    private AlarmContract.View mView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<DataManagerLoadAlarmCallBack> mArgumentCaptorCallback;
    private AlarmPresenter mAlarmPresenter;


    @Before
    private void setUpAlarmPresenter(){
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        //mAlarmPresenter = new AlarmPresenter( mView, );
    }
}

