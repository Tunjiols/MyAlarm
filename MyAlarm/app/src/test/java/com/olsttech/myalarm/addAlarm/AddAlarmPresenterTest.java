package com.olsttech.myalarm.addAlarm;

import android.content.Context;

import com.olsttech.myalarm.data.AlarmDataManagerApi;
import com.olsttech.myalarm.models.Alarm;
import com.olsttech.myalarm.models.SoundModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import org.mockito.MockitoAnnotations;

public class AddAlarmPresenterTest {

    @Mock
    private AddAlarmContract.View mView;

    @Mock
    Context context;

    @Mock
    private AlarmDataManagerApi.AlarmManagerPresenter mAlarmDataManagerApi;

    private AddAlarmPresenter addAlarmPresenter;

    @Captor
    AddAlarmContract.SaveAlarmCallBack onAlarmsave;

    @Captor
    private ArgumentCaptor<AlarmDataManagerApi.OnSavedAlarmListener>  onSavedAlarmListenerArgumentCaptor;

    @Before
    public void setUpAddAlarmPresenterTest(){
        MockitoAnnotations.initMocks(this   );

        addAlarmPresenter = new AddAlarmPresenter(mView, context);
    }

    @Test
    public void setRepeatTest() {
        addAlarmPresenter.setRepeat();
       verify(mView).showRepeatScreen();
    }

    @Test
    public void addAlarmLabelTest() {
        addAlarmPresenter.addAlarmLabel();
       verify( mView).showLabelScreen();
    }

    @Test
    public void setSoundTest() {
        addAlarmPresenter.setSound(any(SoundModel.class));
        verify(mView).showSoundsListScreen(any(SoundModel.class));
    }

    @Test
    public void setAlarmTimeTest() {
        fail("intented to fail");
    }

    @Test
    public void setSnoozeTest() {
        addAlarmPresenter.setSnooze(any(boolean.class));
        verify(mView).setSnooze(any(boolean.class));

    }

    @Test
    public void saveAlarmTest() {
        //fail("inteneted to fail");
        Alarm alarm = new Alarm();
        //save alarm to repo
        verify(mAlarmDataManagerApi).saveAlarm(alarm, onSavedAlarmListenerArgumentCaptor.capture());
        onSavedAlarmListenerArgumentCaptor.getValue().onSuccess(anyString());

        verify(onAlarmsave).onAlarmSaveCallBack(true);

        verify(onAlarmsave).onAlarmSaveCallBack(false);
    }
}
