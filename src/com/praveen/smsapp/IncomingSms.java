package com.praveen.smsapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.view.Gravity;
import android.widget.RemoteViews;
import android.widget.Toast;


public class IncomingSms extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {


		try {
			final Bundle bundle = intent.getExtras();

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();

					String senderNum = phoneNumber+"";
					String message = currentMessage.getDisplayMessageBody()+"";

					//Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, Toast.LENGTH_LONG).show();
					Toast toast=Toast.makeText(context, " " + message, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();


					NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
					//Create Intent to launch this Activity again if the notification is clicked.
					Intent myIntent = new Intent(context, MainActivity.class);
					myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0, myIntent,
							PendingIntent.FLAG_UPDATE_CURRENT);
					builder.setContentIntent(myPendingIntent);
					//builder.setOngoing(true);


					// Sets the ticker text
					builder.setTicker("new message");

					// Sets the small icon for the ticker
					builder.setSmallIcon(R.drawable.ic_launcher);



					// Cancel the notification when clicked
					builder.setAutoCancel(false);

					// Build the notification
					Notification notification = builder.build();



					// Inflate the notification layout as RemoteViews
					RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);

					// Set text on a TextView in the RemoteViews programmatically.
					final String time = "Dummy String 1";
					final String text = "Dummy String 2";
					contentView.setTextViewText(R.id.textView1,  message);

					/* Workaround: Need to set the content view here directly on the notification.
					 * NotificationCompatBuilder contains a bug that prevents this from working on platform
					 * versions HoneyComb.
					 * See https://code.google.com/p/android/issues/detail?id=30495
					 */
					notification.contentView = contentView;


					// Add a big content view to the notification if supported.
					// Support for expanded notifications was added in API level 16.
					// (The normal contentView is shown when the notification is collapsed, when expanded the
					// big content view set here is displayed.)



					// START_INCLUDE(notify)
					// Use the NotificationManager to show the notification
					NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
					nm.notify(0, notification);

				} // end for loop
			} // bundle is null

		} catch (Exception e) {
			e.printStackTrace();

		}
	}



}

