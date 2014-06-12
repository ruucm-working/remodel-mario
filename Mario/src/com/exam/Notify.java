package com.exam;

import java.util.Arrays;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;

public class Notify extends Service
{
	private final int NOTIFY_CNT = 3;
	
	protected boolean mRunning;
	private long notify_time[] = new long[] {10, 20, 30};
	private boolean notify_activated[] = new boolean[NOTIFY_CNT];
	private int notify_index = 0;
	private TaskTimer taskTimer1 = new TaskTimer();
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		Log.d("service2","onCreate");
		Arrays.fill(notify_activated, false);	// 배열을 false로 채움
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
				Log.v("TA9", NOTIFY_CNT + " vs " + notify_index);
				
				while(NOTIFY_CNT > notify_index)
				{
					try
					{
						Thread.sleep(2000);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Log.v("TA9", NOTIFY_CNT + " vs " + notify_index);
					if(NOTIFY_CNT > notify_index)
					{
						Log.v("TA9", "Enter");
						// YOU MUST MODIFY HERE
						int tasktime = taskTimer1.time;
						
						Log.v("TA9", tasktime + "초 경과");
						
						if(tasktime > notify_time[notify_index] && notify_activated[notify_index] == false)
						{
							mHandler.sendEmptyMessage(0);		// call handleMessage
							notify_activated[notify_index++] = true;
						}
					}
				}
			}
		}).start();

		return START_STICKY_COMPATIBILITY;
	}
}