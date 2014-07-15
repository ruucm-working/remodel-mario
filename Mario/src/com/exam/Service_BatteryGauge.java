package com.exam;


import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.widget.*;

public class Service_BatteryGauge extends Service {
	
	NotificationCompat.Builder const_builder;	
	private static boolean isBatteryLow = false;
	
	
	
	
	public int onStartCommand (Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		Log.d("battersv","onStartCommand");
		
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(mBRBattery, filter);
		return START_STICKY;
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	
	public void onCreate()
	{
		
		const_builder = new NotificationCompat.Builder(Service_BatteryGauge.this);
	}
	
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBRBattery);
	}

	BroadcastReceiver mBRBattery = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			
			
			int bLevel = intent.getIntExtra("level", 0);
			//Log.v(TAG, "Battery level changed: " + bLevel);
			
			
			
			
			Setting.nowBattery = bLevel;

			if(bLevel < 20)
				isBatteryLow = true;
			else
				isBatteryLow = false;
			
			
			
			String action = intent.getAction();
			
			
			Log.d("battersv","onReceive");
			
			
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
			Intent nintent = new Intent(getApplicationContext(),coinBlockIntroActivity.class);
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext()
					, 0
					, nintent 
					, Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			const_builder.setContentIntent(pIntent);
			manager.notify(2, const_builder.build());
			
			
			
			/*
			
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				
				
				
				int scale, level, ratio;
				scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
				level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
				ratio = level * 100 / scale;
				
				
				Log.d("battersv","ratio ");

				RemoteViews remote = new RemoteViews(context.getPackageName(), 
						R.layout.batterygauge);
				remote.setTextViewText(R.id.gauge, "" + ratio + "%");
				
				Log.d("battersv","setTextViewText ");

				
				AppWidgetManager wm = AppWidgetManager.getInstance(
						Service_BatteryGauge.this);
				ComponentName widget = new ComponentName(context, BatteryGauge.class);
				wm.updateAppWidget(widget, remote);
				
				
				
				
			}
			*/
		}
	};
};
