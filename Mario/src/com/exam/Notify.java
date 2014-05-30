package com.exam;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Notify extends Activity
{
	private static final String TAG = "Setting_TAG";

	// Singleton
	private static Notify instance = null;

	// Notification variables
	private static NotificationManager mNotificationManager;
	final private int NOTI_ID = 5517;

	private Notify() {}

	public static Notify getInstance()
	{
		if(instance == null)
			instance = new Notify();
		return instance;
	}

	public void MakeNotification(String top, String title, String text)
	{
		/*
		Context t = getApplication(); 
		Log.v(TAG, "1-1");
		//Intent intent = new Intent(getApplicationContext(), coinBlockIntroActivity.class);
		Intent intent = new Intent(getApplication(), coinBlockIntroActivity.class);

		Log.v(TAG, "1-2");
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Notification notification = new Notification();

		notification.flags = Notification.FLAG_AUTO_CANCEL;		// delete notification on click
		notification.icon = R.drawable.flowers_sprites_4; 		// notification image
		notification.when = System.currentTimeMillis();			// time on notification
		notification.number = 10; 	// number of unidentified notifications??

		notification.tickerText = top; 
		notification.setLatestEventInfo(getApplication(), title, text, pendingIntent);
		//notification.setLatestEventInfo(getApplicationContext(), title, text, pendingIntent);
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOTI_ID, notification);
		 */

		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		Log.v(TAG, "1-1");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, coinBlockIntroActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

		Log.v(TAG, "1-2");
		Notification.Builder mBuilder = new Notification.Builder(this);
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setTicker("Notification.Builder");
		mBuilder.setWhen(System.currentTimeMillis());
		mBuilder.setNumber(10);
		mBuilder.setContentTitle("Notification.Builder Title");
		mBuilder.setContentText("Notification.Builder Massage");
		mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		mBuilder.setContentIntent(pendingIntent);
		mBuilder.setAutoCancel(true);

		Log.v(TAG, "1-3");
		mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

		Log.v(TAG, "1-4");
		nm.notify(111, mBuilder.build());
	}
}