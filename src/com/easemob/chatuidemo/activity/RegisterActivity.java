/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.R;
import com.easemob.exceptions.EaseMobException;

/**
 * 注册页
 * 
 */
public class RegisterActivity extends BaseActivity {
	private EditText userNameEditText;
	private EditText passwordEditText;
	private EditText emailEditText;
	private EditText confirmPwdEditText;
	CheckBox isChecked;
	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mContext =this;
		super.onCreate(savedInstanceState);
		initView();
		setListener();
	}

	private void setListener() {
		setOnRegisterListener();
		setOnAgreementListener();
	}

	/**
	 * 用户协议的监听
	 * */
	private void setOnAgreementListener() {
		findViewById(R.id.tv_agreement).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegisterActivity.this,AgreementActivity.class));
			}
		});
	}

	/**
	 * 注册
	 *
	 */
	private void setOnRegisterListener() {
		findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String username = userNameEditText.getText().toString().trim();
				final String pwd = passwordEditText.getText().toString().trim();
				String confirm_pwd = confirmPwdEditText.getText().toString().trim();
				String email = emailEditText.getText().toString().trim();
				if (TextUtils.isEmpty(username)) {
					Toast.makeText(mContext, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
					userNameEditText.requestFocus();
					return;
				}else if(!username.matches("1[\\d]{10}")){
					Toast.makeText(mContext, getResources().getString(R.string.username_should_legal), Toast.LENGTH_SHORT).show();
					userNameEditText.requestFocus();
					return;
				}
				else if (TextUtils.isEmpty(pwd)) {
					Toast.makeText(mContext, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
					passwordEditText.requestFocus();
					return;
				} else if (TextUtils.isEmpty(confirm_pwd)) {
					Toast.makeText(mContext, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
					confirmPwdEditText.requestFocus();
					return;
				} else if (!pwd.equals(confirm_pwd)) {
					Toast.makeText(mContext, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
					return;
				}else if(!isChecked.isChecked()){
					Toast.makeText(mContext, getResources().getString(R.string.hint1), Toast.LENGTH_SHORT).show();
					isChecked.requestFocus();
					return;
				}

				if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
					final ProgressDialog pd = new ProgressDialog(mContext);
					pd.setMessage(getResources().getString(R.string.Is_the_registered));
					pd.show();


					new Thread(new Runnable() {
						public void run() {
							try {
								// 调用sdk注册方法
								EMChatManager.getInstance().createAccountOnServer(username, pwd);
								runOnUiThread(new Runnable() {
									public void run() {
										if (!RegisterActivity.this.isFinishing())
											pd.dismiss();
										// 保存用户名
										DemoApplication.getInstance().setUserName(username);
										Toast.makeText(getApplicationContext(), getResources().getString(R.string.hint), Toast.LENGTH_SHORT).show();
										finish();
									}
								});
							} catch (final EaseMobException e) {
								runOnUiThread(new Runnable() {
									public void run() {
										if (!RegisterActivity.this.isFinishing())
											pd.dismiss();
										int errorCode=e.getErrorCode();
										if(errorCode==EMError.NONETWORK_ERROR){
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
										}else if(errorCode == EMError.USER_ALREADY_EXISTS){
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
										}else if(errorCode == EMError.UNAUTHORIZED){
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
										}else if(errorCode == EMError.ILLEGAL_USER_NAME){
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
										}else{
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed) + e.getMessage(), Toast.LENGTH_SHORT).show();
										}
									}
								});
							}
						}
					}).start();
					Intent intent = new Intent(RegisterActivity.this,RegisterActivity2.class);
					startActivity(intent);
					pd.dismiss();
				}
			}
		});
	}

	private void initView() {
		setContentView(R.layout.activity_register);
		userNameEditText = (EditText) findViewById(R.id.username);
		emailEditText = (EditText) findViewById(R.id.etEmail);
		passwordEditText = (EditText) findViewById(R.id.password);
		confirmPwdEditText = (EditText) findViewById(R.id.confirm_password);
		isChecked = (CheckBox) findViewById(R.id.isChecked);
	}



	public void back(View view) {
		finish();
	}

}
