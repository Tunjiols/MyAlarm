package com.olsttech.myalarm.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.olsttech.myalarm.R;
import com.olsttech.myalarm.utils.AlarmConstants;

/**
* class for LabelActivity
*/
public class LabelActivity extends AppCompatActivity implements LabelContract.View, 
                            View.OnClickListener{


    private LabelContract.Presenter presenter;
    private EditText mEditText;
    private TextView mCancel;
    private TextView mSave;
    private static String mLabel;

    private static LabelContract.LabelCallBack mCallback;
    
    
    public static void startActivity(Context context, int flag, String label, LabelContract.LabelCallBack callback){
        Intent intent = new Intent(context, LabelActivity.class);
        intent.putExtra(AlarmConstants.INIT_LABEL, label);
        intent.setFlags(flag);

        mCallback = callback;
        
        context.startActivity(intent);
    }
    
    @Override
    public void onCreate(@Nullable Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.label_activity);
        
        bindView();
        initSetup();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    private void bindView(){

        mEditText = (EditText) findViewById(R.id.edit_text);
        mCancel = (TextView) findViewById(R.id.cancel);
        mSave = (TextView) findViewById(R.id.save);
    }
    
    private void initSetup(){
        presenter = new LabelPresenter(this);
        presenter.loadlabelEditScreen();
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);

    }
    
    @Override
    public void loadView(){
        mEditText.setText(getIntent().getStringExtra(AlarmConstants.INIT_LABEL));//get preset label
    }
    
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.save:
                getText();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    private void getText(){
        String label = mEditText.getText().toString().trim();
        if(!TextUtils.isEmpty(label)
                && !label.equals(getIntent().getStringExtra(AlarmConstants.INIT_LABEL))){
            mCallback.callBack(label);//return the label value
            //mLabel = label;
            finish();
        }
    }
}
