package com.exam;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class coinBlockIntroActivity extends Activity
{
	/** Called when the activity is first created. */
	
	public static TextView time;
	static int checkHandler = 0;
	static long count = 0;
	public static long second = 0;
	
	
	
	private AsyncTask<Void, Integer, Void> mTask;
	
	
	 
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//measuring time
		time = (TextView)findViewById(R.id.time0);
		
		
		
        
        /*
        Button btnStart = (Button)findViewById(R.id.btn_start);
        Button btnPause = (Button)findViewById(R.id.btn_pause); 
        Button btnStop = (Button)findViewById(R.id.btn_stop);       
        
		
		
	
        
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

*/
        
        
        /*

		// Run service
		Intent intent = new Intent(this, Notify.class);
		startService(intent);
		
		*/
          	      
        
        
        
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
			
			
		case R.id.btn_start:
			
			
				mTask = new AsyncTask<Void, Integer, Void>()
				{
			    	private boolean isCanceled = false;
			    	
			    	@Override
			    	protected void onPreExecute()
			    	{
			    		publishProgress(0);
			    		isCanceled = false;
			    	}
			    	
					@Override
					protected Void doInBackground(Void... params)
					{
						for(int i = 1 ;! isCanceled ; i++)
						{
						//while(! isCanceled){
							try
							{
								publishProgress(1);
								Thread.sleep(100);
							}
							catch(InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					//}
						 
						return null;
					}

					@Override
					protected void onProgressUpdate(Integer... progress)
					{
						count ++;
						second = getSecond(count);
						time.setText( second + "초 " + count%10 );
						//time.setText(Long.toString(second));				
						} 
					
					@Override
					protected void onPostExecute(Void result)
					{
						Toast.makeText(coinBlockIntroActivity.this, "onPostExcute", Toast.LENGTH_SHORT).show();
						//mButton.setText("start");
					}
					
					@Override
					protected void onCancelled()
					{
						isCanceled = true;
						publishProgress(0);
						Toast.makeText(coinBlockIntroActivity.this, "cancled", Toast.LENGTH_SHORT).show();
					}
				};				
				
				mTask.execute();
			
			break;
		
		case R.id.btn_pause:
			mTask.cancel(false);
			break;
			
		case R.id.btn_stop:
			//thread.onStop();				
			count = 0; //시간값 초기화
			time.setText("");
			break;
			
		}
	}
	
	
	
	
	
		
		public static long getSecond(long milli){
			long secondValue = 0;
			secondValue = milli / 10;
			return secondValue;
		}
		
		
	
	
	
	
}