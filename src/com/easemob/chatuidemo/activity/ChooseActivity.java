package com.easemob.chatuidemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easemob.chatuidemo.R;

public class ChooseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        setListener();
    }

    private void setListener() {
        setOnLoginListener();
        setOnRegisterListener();
    }

    private void setOnRegisterListener() {
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setOnLoginListener() {
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
