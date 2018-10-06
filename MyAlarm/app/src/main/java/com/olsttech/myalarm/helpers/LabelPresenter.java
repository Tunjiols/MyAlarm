package com.olsttech.myalarm.helpers;

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
    