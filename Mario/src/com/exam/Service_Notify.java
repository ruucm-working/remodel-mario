package com.exam;

import com.exam.view.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;

public class Service_Notify extends Service
{
	
	
	NotificationCompat.Builder builder;
	
	final int NOTIFY_CNT = 3;
	protected boolean mRunning = true;
	
	private long notify_time[] = new long[] {10, 20, 30};
	private boolean notify_activated[] = new boolean[NOTIFY_CNT];
	private int notify_index = 0;
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{

		builder = new NotificationCompat.Builder(Service_Notify.this);
	
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

			
			Log.d("service2","NotificationManager 실행");
			
			
			builder.setSmallIcon(R.drawable.brick_question);
			builder.setTicker("쓰담쓰담위젯");
			builder.setContentTitle("imyourbabe");
			
			builder.setAutoCancel(true); // 알림바에서 자동 삭제
			builder.setVibrate(new long[] {1000,2000});	// 쉬고, 울리고, 쉬고, 울리고...
			// 진동이 되려면 AndroidManifest.xml에 진동 권한을 줘야 한다.
			
			
			Log.d("service2","setVibrate 실행");
			
			

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
				while(mRunning)
				{
					
					
					try
					{
						Thread.sleep(2000);
						Log.d("service2","mHandler 실행");
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					//mHandler.sendEmptyMessage(0);
					
					int second = coinBlockIntroActivity.taskTimer1.GetTime();
					
					
					int clicountinit = CoinBlockView.CliCountInit;
					int clicount0 = CoinBlockView.CliCount0_1;
					int clicount0_2 = CoinBlockView.CliCount0_2;
					int clicount1 = CoinBlockView.CliCount1;
					int clicount2 = CoinBlockView.CliCount2;
					
					
					
						
						if (second <= 12 && clicount0 == 2  && CoinBlockView.lv0_1){
							mHandler.sendEmptyMessage(0);
							
							builder.setContentText("drawer : '알이 조금 움직인것 같습니다'");
							
						} 
						
						
						if (second <= 22 && clicount1 == 2 && CoinBlockView.lv1){
							mHandler.sendEmptyMessage(0);
							
							builder.setContentText("drawer : '새대가리가 조금 움직인것 같습니다'");
							
						} 
						
						
						if (second <= 32 && clicount2 ==2 && CoinBlockView.lv2){
							mHandler.sendEmptyMessage(0);
							
							builder.setContentText("drawer : '새가 약간 부들부들 떱니다'");
							
						} 
						
						
						
					
					
				}
			}
		}).start();

		return START_STICKY_COMPATIBILITY;
	}
}