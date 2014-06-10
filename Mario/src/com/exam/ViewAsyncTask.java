package com.exam;

import com.exam.view.*;

import android.media.*;
import android.os.*;
import android.util.*;
import android.widget.*;

/** AsyncTask<A, B, C> 
 *    - A : parameters' type of doInBackground
 *    - B : parameters' type of onProgressUpdate
 *    - C : parameters' type of onPostExecute */
public class ViewAsyncTask extends AsyncTask<String, String, String> {
    
    private static final String RESULT_SUCCESS   = "1";
    private static final String RESULT_FAIL      = "0";
    
    private static final int TEXT_COLOR_NORMAL   = 0xFF000000;
    private static final int TEXT_COLOR_FINISHED = 0xFFFF0000;
    
    private TextView timer = null;
    private int time       = -1;
    
    public void setTextView1(int textViewId) {
        timer = (TextView)coinBlockIntroActivity.getInstance()
                .findViewById(textViewId);
    }
    
    public void setTime(int time) {
        this.time = time;
    }
    
    /** this method is executed right BEFORE doInBackground()
     *  on the main thread (UI thread) */
    @Override
    protected void onPreExecute() {
        timer.setText("" + time);
        timer.setTextColor(TEXT_COLOR_NORMAL);
        
        
        
    }

    /** this method is executed BETWEEN onPreExecute() and onPostExecute()
     *  on another thread (that's why it's called asynchronous) */
    // you should do network tasks here, not in main thread (abandoned)
    // you're not allowed to modify UI
    @Override
    protected String doInBackground(String... params) {
        while(time >= 0) { 
            try {
                Thread.sleep(1000);         // one second sleep
                time++;                     // decrement time
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
        // play beep sound
        //MediaPlayer.create(coinBlockIntroActivity.getInstance(), R.raw.beep).start();
    }
    
    /** this method is executed right AFTER doInBackground()
     *  on the main thread (UI thread) */
    @Override
    protected void onPostExecute(String result) {
        if(RESULT_SUCCESS.equals(result))
            timer.setTextColor(TEXT_COLOR_FINISHED);
    }

}