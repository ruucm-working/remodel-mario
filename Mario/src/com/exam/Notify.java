package com.exam;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;

public class Notify extends Service
{
	protected boolean mRunning;
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		Log.d("service2","onCreate");
	}

	@Override
	public void onDestroy()
	{
		Log.d("service2","onDestroy");
		mRunning = false;
	}

	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(Notify.this);
			builder.setSmallIcon(R.drawable.brick_question);
			builder.setTicker("Mario");
			builder.setContentTitle("Hello");
			builder.setContentText("butcher anthem !!");
			builder.setAutoCancel(true); // 알림바에서 자동 삭제
			builder.setVibrate(new long[] {1000,2000});	// 쉬고, 울리고, 쉬고, 울리고...
			// 진동이 되려면 AndroidManifest.xml에 진동 권한을 줘야 한다.

			// 알람 클릭시 MainActivity를 화면에 띄운다
			Intent intent = new Intent(getApplicationContext(),coinBlockIntroActivity.class);
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext()
					, 0
					, intent
					, Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			builder.setContentIntent(pIntent);
			manager.notify(1, builder.build());
		};
	};

	// 서비스 작동내용
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d("service2","onStartCommand 실행");

		// handler 통한 Thread 이용
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				if(Setting.second < 10)
					mRunning = true;
				else
					mRunning = false;
				
				while(mRunning)
				{
					try
					{
						Thread.sleep(10000);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.sendEmptyMessage(0);		// call handleMessage
				}
			}
		}).start();

		return START_STICKY_COMPATIBILITY;
	}
}