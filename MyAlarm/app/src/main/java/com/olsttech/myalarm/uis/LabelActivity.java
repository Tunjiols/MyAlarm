package com.olsttech.myalarm.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import com.olsttech.myalarm.R;
/** 
* class for LabelActivity
*/
public class LabelActivity extends AppCompatActivity implements LabelContract.View, 
                            View.OnClickListener{

    private static final String INIT_LABEL = "initLabel";
    private LabelContract.Presenter presenter;
    private EditText mEditText;
    private TextView mCancel;
    private TextView mSave;
    privte String mLabel;
    
    
    public void startActivity(Context context, int flag, String label, LabelPresenter.LabelCallBack callback){
        Intent intent = new Intent(context, LabelActivity.class);
        intent.putExtra(INIT_LABEL, label);
        intent.setFlags(flag);
        callback.callBack(mLabel);
        
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
        //if(!INIT_LABEL == null)
           // mLabel = null;
    }
    
    @Override
    public void loadView(){
        if(!TextUtils.isEmpty(mEditText.getText()))
            mEditText.setText("");
    }
    
    @Override
    void onClick(View v){
        switch(v.getId()){
            case R.id.save:
                LabelContract.LabelCallBack labelcallback;
                String label;
                if(!TextUtils.isEmpty(mEditText.getText())){
                    label = mEditText.getText().trim();
                    labelcallback.callback(label);
                    //implement close the activity
                    mLabel = label;
                    finish();
                }
                break;
            case R.id.cancel:
                if(!TextUtils.isEmpty(mEditText.getText())){
                    mEditText.setText("");
                    mLabel = null;
                }
                break;
        }
    }
}
