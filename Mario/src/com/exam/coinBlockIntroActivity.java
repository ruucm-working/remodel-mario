package com.exam;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class coinBlockIntroActivity extends Activity
{
	/** Called when the activity is first created. */
	
	static TextView time;
	ThreadTime thread;
	static int checkHandler = 0;
	static long count = 0;
	
	public static long second;
	
	 
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//measuring time
		time = (TextView)findViewById(R.id.time0);
        Button btnStart = (Button)findViewById(R.id.btn_start);
        Button btnPause = (Button)findViewById(R.id.btn_pause); 
        Button btnStop = (Button)findViewById(R.id.btn_stop);       
        thread = new ThreadTime(mHandler);
        thread.start();
		
		
	
        
    	//시작 버튼
        btnStart.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			thread.onStart();
    		}
        });
        //일시정지 버튼
        btnPause.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			thread.onStop();				
    		}
        });   
        //정지 버튼
        btnStop.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			thread.onStop();				
    			count = 0; //시간값 초기화
    			time.setText("");
    		}
        });       
        
        

		// Run service
		Intent intent = new Intent(this, Notify.class);
		startService(intent);
		
		
		
	}
	
	
	
 
	
	
	
	
	

	public void mOnClick(View v)
	{
		switch(v.getId())
		{
		case R.id.setbutton:    		
			Intent intent = new Intent(this, Setting.class);
			startActivity(intent); 		
			break;
			
			
		case R.id.reset1:    		
				
			break;
		}
	}
	
	
	
	
	
	
	 static Handler mHandler = new Handler(){
	    	
			public void handleMessage(Message msg){
				Log.v("StopWatch", "Handler" + count);
				count ++;
				second = getSecond(count);
				time.setText( second + "초 " + count%10 );
			}		
			
		};
		
		public static long getSecond(long milli){
			long secondValue = 0;
			secondValue = milli / 10;
			return secondValue;
		}
		
		
		class ThreadTime extends Thread{
			Handler mHandler;
			boolean sns = false; //Thread를 통제하기 위한 boolean 값
			public void run(){
				while(true){
					if(sns){
						Log.v("StopWatch", "ThreadTime");
						mHandler.sendEmptyMessage(0);
						try{
							Thread.sleep(100);
						}catch(InterruptedException e){
						}
					}
				}
			}
			
			//생성자
			public ThreadTime(Handler handler){
				mHandler = handler;
			}
			
			public void onStart(){
				sns = true;
			}
			
			public void onStop(){
				sns = false;
			}		
			
		}
	
	
	
	
}