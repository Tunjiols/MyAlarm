package com.olsttech.myalarm.uis;

import com.olsttech.myalarm.models.DayModel;

import java.util.ArrayList;
import java.util.List;

public class LabelPresenter implements LabelContract.Presenter{

    private LabelContract.View mView;
    public LabelPresenter(LabelContract.View view){
        this.mView = view;
    }

    @Override
    public void loadlabelEditScreen(){
        mView.loadView();
    }
}
    