package com.exam;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.widget.*;

import com.exam.view.*;
import com.facebook.widget.*;

public class Service_Notify extends Service
{
	NotificationCompat.Builder builder;
	NotificationCompat.Builder const_builder;

	final int NOTIFY_CNT = 3;
	protected boolean mRunning = true;

	private long notify_time[] = new long[] {10, 20, 30};
	private boolean notify_activated[] = new boolean[NOTIFY_CNT];
	private int notify_index = 0;

	//프레퍼런스 
	public static TextPref mPref;



	boolean init = false;
	boolean lv0_1 = false;
	boolean lv0_2 = false;
	boolean lv1 = false;
	boolean lv2 = false;


	int CliCountinit;
	int CliCount0_1;
	int CliCount0_2;
	int CliCount1;
	int CliCount2;
	int time;


	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		Log.d("Service_Notify","onCreate");
		builder = new NotificationCompat.Builder(Service_Notify.this);
		const_builder = new NotificationCompat.Builder(Service_Notify.this);
	}

	@Override
	public void onDestroy()
	{
		Log.d("Service_Notify","onDestroy");
		mRunning = false;
	}
	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			Log.d("Service_Notify","Run constHandler");
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			Log.d("Service_Notify","NotificationManager 실행");

			builder.setSmallIcon(R.drawable.brick_question);
			builder.setTicker("쓰담쓰담위젯");
			builder.setContentTitle("imyourbabe");

			builder.setAutoCancel(true); // 알림바에서 자동 삭제
			builder.setVibrate(new long[] {1000,2000});	// 쉬고, 울리고, 쉬고, 울리고...
			// 진동이 되려면 AndroidManifest.xml에 진동 권한을 줘야 한다.

			Log.d("Service_Notify","setVibrate 실행");


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
	/*
	Handler mConstHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			
			
			Log.d("Service_Notify","handleMessag2e 실행");
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
			try {
				manager.cancel(2);		// 기존에 constNotification 있으면 삭제
			} catch(Exception e) {
				Log.d("Service_Notify", "Const에서 에러 났다");
			}

			Log.d("Service_Notify","NotificationManager 실행");

			const_builder.setSmallIcon(R.drawable.samsung_sample);
			const_builder.setTicker("쓰담쓰담위젯");
			const_builder.setContentTitle("쓰담쓰담위젯");
			
			if(Setting.nowBattery == -1)
				const_builder.setContentText("배터리 잔량을 확인 중입니다.");
			else	
				const_builder.setContentText("배터리 잔량: " + Setting.nowBattery + "%");

			const_builder.setAutoCancel(false); // 알림바에서 자동 삭제
			Log.d("Service_Notify","const configuration complete");


			// 알람 클릭시 MainActivity를 화면에 띄운다
			Intent intent = new Intent(getApplicationContext(),coinBlockIntroActivity.class);
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext()
					, 0
					, intent 
					, Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			const_builder.setContentIntent(pIntent);
			manager.notify(2, const_builder.build());
			
			
			
		};
	};
*/
	// 서비스 작동내용
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		//mConstHandler.sendEmptyMessage(0);
		Log.d("Service_Notify","onStartCommand 실행");

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
						Log.d("Service_Notify","mHandler 실행");
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//mHandler.sendEmptyMessage(0);
					try {
						mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");

					} catch (Exception e) { 
						e.printStackTrace();
					}
					mPref.Ready();

					init = mPref.ReadBoolean("initstate", false);	
					lv0_1 = mPref.ReadBoolean("lv0_1state", false);
					lv0_2 = mPref.ReadBoolean("lv0_2state", false);
					lv1 = mPref.ReadBoolean("lv1state", false);
					lv2 = mPref.ReadBoolean("lv2state", false);

					CliCountinit = mPref.ReadInt("clicountinit", 0);
					CliCount0_1 = mPref.ReadInt("clicount0_1", 0);
					CliCount0_2 = mPref.ReadInt("clicount0_2", 0);
					CliCount1 = mPref.ReadInt("clicount1", 0);
					CliCount2 = mPref.ReadInt("clicount2", 0);

					time = mPref.ReadInt("time", 0);
					mPref.EndReady();

					/*
					int second = coinBlockIntroActivity.taskTimer1.GetTime();

					int clicountinit = CoinBlockView.CliCountInit;					
					int clicount0 = CoinBlockView.CliCount0_1;
					int clicount0_2 = CoinBlockView.CliCount0_2;
					int clicount1 = CoinBlockView.CliCount1;
					int clicount2 = CoinBlockView.CliCount2;
					 */

					if (time <= 12 && CliCount0_1 == 2  && lv0_1){
						mHandler.sendEmptyMessage(0);
						builder.setContentText("drawer : '알이 조금 움직인것 같습니다'");
					}

					if (time <= 22 && CliCount1 == 2 && lv1){
						mHandler.sendEmptyMessage(0);
						builder.setContentText("drawer : '새대가리가 조금 움직인것 같습니다'");
					}

					if (time <= 32 && CliCount2 ==2 && lv2){
						mHandler.sendEmptyMessage(0);
						builder.setContentText("drawer : '새가 약간 부들부들 떱니다'");
					}
				}
			}
		}).start();

		return START_STICKY_COMPATIBILITY;
	}
}