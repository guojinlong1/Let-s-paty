package com.easemob.chatuidemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.easemob.chatuidemo.R;

public class AgreementActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        setListener();
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
