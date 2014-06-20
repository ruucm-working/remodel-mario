package com.exam;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class coinBlockIntroActivity extends Activity implements OnClickListener
{
	/** Called when the activity is first created. */

	public static TextView time;
	static int checkHandler = 0;
	static long count = 0;
	public static long second = 0;
	int tasktime ;

	//Async Task
	private AsyncTask<Void, Integer, Void> mTask;
	private Button mButton;

	private long time1;


	//Async Task
	private static coinBlockIntroActivity instance;
	public static  TaskTimer taskTimer1 = new TaskTimer();


	public static coinBlockIntroActivity getInstance() {
		return instance;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		instance = this;

		//measuring time
		//time = (TextView)findViewById(R.id.time0);

		//mButton = (Button) findViewById(R.id.btn_stop);        
		//mButton.setOnClickListener(this);

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

        /*

		// Run service
		Intent intent = new Intent(this, Notify.class);
		startService(intent);

		 */
	} 

	@Override	
	public void onClick(View v)
	{
		if(mButton.getText().equals("start"))
		{
			Log.v("tag7", "equals(start");

			mTask = new AsyncTask<Void, Integer, Void>()
					{
				private boolean isCanceled = false;

				@Override
				protected void onPreExecute()
				{
					//publishProgress(0);
					isCanceled = false;
				}

				@Override 
				protected Void doInBackground(Void... params)
				{
					Log.v("tag7", "doInBackground");

					for(int i = 1 ; i <= 50 && !isCanceled ; i++)
					{
						Log.v("tag7", "for"+i);

						try
						{
							publishProgress(i);
							Thread.sleep(100);
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					/* 
					while(true)

					{

					if(isCanceled == true) break;

					try
					{
						publishProgress(1);
						Thread.sleep(100);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					}*/
					return null;
				}

				@Override
				protected void onProgressUpdate(Integer... progress)
				{
					//mProgress.setProgress(progress[0]);
					count ++;
					second = getSecond(count);
					time.setText( second + "초 " + count%10 );
				}

				@Override
				protected void onPostExecute(Void result)
				{
					//Toast.makeText(coinBlockIntroActivity.this, "onPostExecute", Toast.LENGTH_SHORT).show();
					//mButton.setText("start");
				}

				@Override
				protected void onCancelled()
				{
					Log.v("tag7", "onCancelled");
					isCanceled = true;
					//publishProgress(0);
					//Toast.makeText(coinBlockIntroActivity.this, "onCancelled", Toast.LENGTH_SHORT).show();
				}
					};

					mTask.execute();
					mButton.setText("cancel");
		}
		else if(mButton.getText().equals("cancel"))
		{

			Log.v("tag7", "equals(cancel");
			mTask.cancel(false);
			mButton.setText("start");
		}
	}

	public void mOnClick(View v)
	{
		switch(v.getId())
		{
		case R.id.setbutton:    		
			Intent intent = new Intent(this, Setting.class);
			startActivity(intent); 		
			break;

		case R.id.btn_start: 
			if(taskTimer1.isCanceled == false){
				TaskTimer taskTimer1 = new TaskTimer();
				taskTimer1.setTextView1(R.id.time0);
				//taskTimer1.setTime(0);
				taskTimer1.execute("");
				//taskTimer1.execute("");
			}
			else
				taskTimer1.isCanceled = false;


			/*
				//taskTimer1.isCanceled = false;

				TaskTimer taskTimer2 = new TaskTimer();
				taskTimer2.setTextView1(R.id.time0);
		        taskTimer2.setTime(0);
		        taskTimer2.execute("");

			 */

			/*
				TaskTimer taskTimer1 = new TaskTimer();
				taskTimer1.setTextView1(R.id.time0);
		        //taskTimer1.setTime(0);
		        taskTimer1.execute(""); 

			 */
			break;

		case R.id.btn_pause: 
			//thread.onStop();		
			tasktime = taskTimer1.GetTime();
			Log.v("tag9", "tasktiem" +Integer.toString(tasktime));
			taskTimer1.isCanceled = true;

			//taskTimer1.cancel(false);
			break;

		case R.id.btn_stop:
			if(taskTimer1.isCanceled == false){
				TaskTimer taskTimer1 = new TaskTimer();
				taskTimer1.setTextView1(R.id.time0);
				taskTimer1.setTime(0);
				taskTimer1.timer.setText("0");
				//taskTimer1.execute("");
			}
			else
				taskTimer1.isCanceled = false;
			break;


		case R.id.reset1:
			break;
		}
	}

	public static long getSecond(long milli){
		long secondValue = 0;
		secondValue = milli / 10;
		return secondValue;
	}
}