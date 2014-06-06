package com.exam;

import android.app.*;
import android.content.*;
import android.media.*;
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
					// notificaation 10초마다 무한루프되는듯. 빠른 시일내에 수정예정
					try
					{
						Thread.sleep(1000000); 
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.sendEmptyMessage(0);		// call handleMessage
					MediaPlayer music = MediaPlayer.create(getApplicationContext(), R.raw.notify_sound);	
					music.setLooping(false);
					music.start();		// play music for 5 sec
					
					Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					long[] pattern = { 0, 1000, 200, 1000 };	// vibrate pattern (0ms delay, 1000ms vibrate, 200ms rest, 1000ms vibrate...)
					vibe.vibrate(pattern, -1);		// vibrate for 2 sec
				}
			}
		}).start();

		return START_STICKY_COMPATIBILITY;
	}
}