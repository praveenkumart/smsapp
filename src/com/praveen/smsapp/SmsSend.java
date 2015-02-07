package com.praveen.smsapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SmsSend extends ActionBarActivity {
	EditText contact, mes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sms_send);
		contact= (EditText) findViewById(R.id.send_edit_number);
		mes=(EditText) findViewById(R.id.send_edit_message);

	}
	//	public void sendSms(View v) {
	//
	//		String num=contact.getText().toString();
	//		String message=mes.getText().toString();  
	//
	//
	//		sendSmsMessage();
	//
	//
	//		Toast.makeText(getApplicationContext(), "message:"+message+"to"+num+" success", Toast.LENGTH_SHORT).show();
	//
	//	}
	//
	//	protected void sendSmsMessage(){
	//
	//
	//		try {
	//
	//			String num=contact.getText().toString();
	//			String message=mes.getText().toString();  
	//
	//
	//			SmsManager smsManager=SmsManager.getDefault();
	//			smsManager.sendTextMessage(num, null, message, null,null);
	//			//	smsManager.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent);
	//
	//			Toast.makeText(this, "sms is send", Toast.LENGTH_SHORT).show();
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			Toast.makeText(this, "Sending faild", Toast.LENGTH_SHORT).show();
	//
	//			e.printStackTrace();
	//		}
	//
	//	}
	// another method
	public void sendSms(View v) {

		String num=contact.getText().toString();
		String message=mes.getText().toString();  
		if(num.equals(""))
		{
			Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
		}
		else{
			sendingSms(num,message);
			Toast.makeText(getApplicationContext(), "message:"+message+"to"+num+" success", Toast.LENGTH_SHORT).show();
		}

	}
	public void sendingSms(String num,String message) {

		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(SENT), 0);
		PendingIntent piDeliver=PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(DELIVERED), 0);

		registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent",Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure",Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service",Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",Toast.LENGTH_SHORT).show();
					break;
				}

			}
		}, new IntentFilter(SENT));
		registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(),"SMS delivered",Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(),"SMS not delivered", Toast.LENGTH_SHORT)
					.show();
					break;
				}

			}
		}, new IntentFilter(DELIVERED));
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(num, null, message, pi, piDeliver);
		Toast.makeText(getBaseContext(), "Toast below sndtxt",Toast.LENGTH_SHORT).show();
	}

}
