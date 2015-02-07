package com.praveen.smsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void sendSms(View v){
	
		startActivity(new Intent(this,SmsSend.class));
	}
	public void receiveSms(View v){
		
		startActivity(new Intent(this,SmsReceive.class));


	}
}