package com.exam;

import com.exam.view.*;

import android.os.*;
import android.util.*;
import android.widget.*;

/** AsyncTask<A, B, C> 
 *    - A : parameters' type of doInBackground
 *    - B : parameters' type of onProgressUpdate
 *    - C : parameters' type of onPostExecute */
public class TaskTimer extends AsyncTask<String, String, String> {
    
	
	public static boolean isCanceled = false;
	
	
    private static final String RESULT_SUCCESS   = "1";
    private static final String RESULT_FAIL      = "0";
    
    private static final int TEXT_COLOR_NORMAL   = 0xFF000000;
    private static final int TEXT_COLOR_FINISHED = 0xFFFF0000;
    
    TextView timer = null;
    private static int time       = 0;
    
    public void setTextView1(int textViewId) {
        timer = (TextView)coinBlockIntroActivity.getInstance()
                .findViewById(textViewId);
    }
    
    public void setTime(int time) {
        this.time = time; 
    }
    
    public void SetTimer()
    {
    	time++;
    }
    
    public void SetTimer(int n)
    {
    	time += n;
    }
    
    public int GetTime()
    {
    	return time;
    }
    
    
  //프레퍼런스 
    public static TextPref mPref;	
    
    /** this method is executed right BEFORE doInBackground()
     *  on the main thread (UI thread) */
    @Override
    protected void onPreExecute() { 
    	Log.d("TaskTimer", "onPreExecute");
        timer.setText("" + time);
        timer.setTextColor(TEXT_COLOR_NORMAL);
        Log.d("TaskTimer", "setTextColor");
        
        
        try {
  			mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
  			//fbPref = new TextPref("mnt/sdcard/SsdamSsdam/facebookprofile.txt");
  		} catch (Exception e) { 
  			e.printStackTrace();
  		}      
  		
  		
  		 
        Log.d("TaskTimer", "onPreExecute"+Long.toString(time));
        
         
    }
 
    /** this method is executed BETWEEN onPreExecute() and onPostExecute()
     *  on another thread (that's why it's called asynchronous) */
    // you should do network tasks here, not in main thread (abandoned)
    // you're not allowed to modify UI
    @Override
    protected String doInBackground(String... params) {
    	
    	 Log.d("TaskTimer", "doInBackground");
    	
        while(time >= 0 && !isCanceled) { 
            try {
                Thread.sleep(1000);         // one second sleep
                time++;                     // decrement time
                Log.d("TaskTimer", Long.toString(time));
                
                
                mPref.Ready(); 
                mPref.WriteInt("time", time);			
    			mPref.CommitWrite();
    			
    			Log.d("TaskTimer", "CommitWrite"+Long.toString(time));
                
                
                publishProgress();          // trigger onProgressUpdate()
                
                
                /*
                //for CoinBlockView updateEvolveIntent                
                if(((CoinBlockWidgetApp) context.getApplicationContext()) != null) {    
                	Log.v("tag8","for CoinBlock"+time);
                	CoinBlockView.second = (long)time;                	
                }
                
                */
                
            } catch(InterruptedException e) {
                Log.i("GUN", Log.getStackTraceString(e));
                return RESULT_FAIL;
            }
        }
        return RESULT_SUCCESS;
    }
    
    /** this method is used by doInBackground
     *  because it's called on the main thread (UI thread),
     *  you can directly modify UI */
    @Override
    protected void onProgressUpdate(String... value) {
        // modify timer's text (remained time)
        timer.setText("" + time);
        
        Log.d("TaskTimer", "onProgressUpdate"+Long.toString(time));
        
        // play beep sound
        //MediaPlayer.create(coinBlockIntroActivity.getInstance(), R.raw.beep).start();
    }
    
    /** this method is executed right AFTER doInBackground()
     *  on the main thread (UI thread) */
    @Override
    protected void onPostExecute(String result) {
        if(RESULT_SUCCESS.equals(result))
            timer.setTextColor(TEXT_COLOR_FINISHED);
        
        Log.d("TaskTimer", "onPostExecute"+Long.toString(time));
        
        
    }

}