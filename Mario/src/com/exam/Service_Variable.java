package com.exam;

import java.io.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;

import com.exam.view.*;

public class Service_Variable extends Service
{
	
	
	protected boolean mRunning = true;
	
	
	//프레퍼런스 
    public static TextPref mPref;	
  	public static TextPref fbPref;	
	
	
	
	//static variables
  	
  	
  	static boolean init = false;
	public static boolean lv0_1;
	public static boolean lv0_2;
	public static  boolean lv1 ;
	public static  boolean lv2 ;
	
	static int clicountinit ;
	static int clicount0 ;
	static int clicount0_2 ;
	static int clicount1 ;
	static int clicount2 ;
	
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{

		
		File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SsdamSsdam"); // dir : 생성하고자 하는 경로
		if(!saveDir.exists()) 
		{
			saveDir.mkdirs();
		}


		try {
			mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
			fbPref = new TextPref("mnt/sdcard/SsdamSsdam/facebookprofile.txt");
			

		} catch (Exception e) { 
			e.printStackTrace();
		}        		
  		
		
	
	}

	@Override
	public void onDestroy()
	{
		mRunning = false;
	}

	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			
			
			mPref.Ready();  
			  
	  		Log.d("CoinBlockView", "init1"+init);
	  		
	  		
	  		init = mPref.ReadBoolean("initstate", false);	
	  		lv0_1 = mPref.ReadBoolean("lv0_1state", false);
	  		lv0_2 = mPref.ReadBoolean("lv0_2state", false);
	  		lv1 = mPref.ReadBoolean("lv1state", false);
	  		lv2 = mPref.ReadBoolean("lv2state", false);
	  		
	  		Log.d("CoinBlockView", "init2"+init);
	  		
	  		
	  		clicountinit = mPref.ReadInt("clicountinit", 0);
	  		clicount0 = mPref.ReadInt("clicount0", 0);
	  		clicount0_2 = mPref.ReadInt("clicount0_2", 0);
	  		clicount1 = mPref.ReadInt("clicount1", 0);
	  		clicount2 = mPref.ReadInt("clicount2", 0);
	  		
	  		
	  		
	  		
	  		//fbPref.EndReady();
	  		mPref.EndReady();
			
			
			
		};
	};

	// 서비스 작동내용
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{

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
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					
						
					
					
				}
			}
		}).start();

		return START_STICKY_COMPATIBILITY;
	}
}