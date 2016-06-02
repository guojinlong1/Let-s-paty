package com.easemob.chatuidemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easemob.chatuidemo.R;

public class RegisterActivity2 extends Activity {
    EditText etverify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        initView();
        setListener();
    }

    private void initView() {
        etverify = (EditText) findViewById(R.id.verify);
    }

    private void setListener() {
        setVerifyListener();
        setBackListener();
    }

    private void setBackListener() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setVerifyListener() {
        findViewById(R.id.btn_ensure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etverify!=null){
                    Intent intent = new Intent(RegisterActivity2.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
