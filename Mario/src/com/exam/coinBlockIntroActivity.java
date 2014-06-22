package com.exam;



import java.io.*;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;


public class coinBlockIntroActivity extends Activity implements OnClickListener
{
	/** Called when the activity is first created. */
	
	
	
	static boolean initstate;
	
	
	
	//액티비티간 통신을 위한
	final static int SETTING = 0;
	
	
	
	TextView welcome ; 
	
	
	TextView InitStateText ; 
	TextView Lv0StateText ; 
	   
	
	
	//프레퍼런스 
	public static TextPref mPref;
  	public static TextPref fbPref;
	 
	
  	/*
	//facebook	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private Button buttonLoginLogout;
    
    */
  	
  	
   // static String userId ;
	
	
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
		
		//setContentView(R.layout.main);
		
		
		instance = this;
		
		//프레퍼런스 읽어오기   
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
  		mPref.Ready();
		fbPref.Ready();
		Log.d("tag03", "fbPref.Ready();");
		

		//set Main Background Image & Text
		
		
		String userId = fbPref.ReadString("userId", "");
  		String userFirstName = fbPref.ReadString("userFirstName", "");
  		String userLastName = fbPref.ReadString("userLastName", "");
		
		initstate = mPref.ReadBoolean("initstate", false);
		boolean lv0state = mPref.ReadBoolean("lv0state", false);
		
        	if(!initstate){
    			setContentView(R.layout.main); 
    			
    			
    	  		
    			welcome = (TextView)findViewById(R.id.welcome);		
    			welcome.setText(userFirstName+" "+userLastName+" 님 환영합니다 위젯을 시작하려면 Set-up 버튼을 누르세요");
    			
        	}
        	
        	else  {
    			setContentView(R.layout.initstate);
    			InitStateText = (TextView)findViewById(R.id.initstatetxt);
    			InitStateText.setText("상자를 열어라");
    		}
	
        	/*
        	
    		else if (initstate && !lv0state) {
    			setContentView(R.layout.initstate);
    			Log.d("tag02","setContentView");
    			InitStateText = (TextView)findViewById(R.id.initstatetxt);
    			InitStateText.setText("상자를 열어라");
    			Log.d("tag02","setText");
    		}
        	
        	
    		else if (lv0state) {
    			setContentView(R.layout.lv0state);    			
    			Lv0StateText = (TextView)findViewById(R.id.lv0statetext);
    			Lv0StateText.setText("알을 애인처럼 다뤄서 부화시켜라");
    			
    		}
    		    		

        */	 
		
  		
  		
  		fbPref.EndReady();
  		
  		
  		Log.d("tag02","EndReady");
  		
  		
  		
		

  		Log.d("tag02","welcome");
		
        
		
		/*
		
		init();
        dataInit();
        facebookInit(savedInstanceState);
		
         */
       
        
        //welcome.setText(coinBlockLoginActivity.userFirstName+" "+coinBlockLoginActivity.userLastName+" 님 환영합니다 위젯을 시작하려면 Set-up 버튼을 누르세요" );
        
        
		
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

*/
        
        
        /*

		// Run service
		Intent intent = new Intent(this, Notify.class);
		startService(intent);
		
		*/
          	      
        
        
        
	} 
	
	
	
	
	
	
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
			startActivityForResult(intent, SETTING ); 		
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
		
		/*
		
		//Facebook Login
		
		private void init() {
	        buttonLoginLogout = (Button)findViewById(R.id.buttonLoginLogout);
	    }
	 
	    @SuppressLint("NewApi")
	    private void dataInit() {
	        //ActionBar Init
	        getActionBar().setDisplayShowHomeEnabled(false);
	        getActionBar().setTitle(R.string.board_detail_activity_title);
	    }
	    
	    private void facebookInit(Bundle savedInstanceState) {
	        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
	        
	        Session session = Session.getActiveSession();
	        if (session == null) {
	            if (savedInstanceState != null) {
	                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
	            }
	            if (session == null) {
	                session = new Session(this);
	            }
	            Session.setActiveSession(session);
	            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	            }
	        }
	        
	        updateView();
	    }
	 
	     @Override
	        public void onStart() {
	            super.onStart();
	            Session.getActiveSession().addCallback(statusCallback);
	        }
	 
	        @Override
	        public void onStop() {
	            super.onStop();
	            Session.getActiveSession().removeCallback(statusCallback);
	        }
	 
	        @Override
	        public void onActivityResult(int requestCode, int resultCode, Intent data) {
	            super.onActivityResult(requestCode, resultCode, data);
	            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	        }
	 
	        @Override
	        protected void onSaveInstanceState(Bundle outState) {
	            super.onSaveInstanceState(outState);
	            Session session = Session.getActiveSession();
	            Session.saveSession(session, outState);
	        }
	 
	        private void updateView() {
	            Session session = Session.getActiveSession();
	            if (session.isOpened()) {
	                buttonLoginLogout.setText("로그아웃");
	                buttonLoginLogout.setOnClickListener(new OnClickListener() {
	                    public void onClick(View view) { onClickLogout(); }
	                });
	            } else {
	                buttonLoginLogout.setText("로그인");
	                buttonLoginLogout.setOnClickListener(new OnClickListener() {
	                    public void onClick(View view) { onClickLogin(); }
	                });
	            }
	        }
	 
	        private void onClickLogin() {
	            Session session = Session.getActiveSession();
	            if (!session.isOpened() && !session.isClosed()) {
	                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	            } else {
	                Session.openActiveSession(this, true, statusCallback);
	            }
	        }
	  
	        private void onClickLogout() {
	            Session session = Session.getActiveSession();
	            if (!session.isClosed()) {
	                session.closeAndClearTokenInformation();
	            }
	        }
	 
	        private class SessionStatusCallback implements Session.StatusCallback {
	            @Override
	            public void call(Session session, SessionState state, Exception exception) {
	                updateView();    
	                getFaceBookMe(session);
	            }
	        }
	        
	        
	        private void getFaceBookMe(Session session){
	        	 
	            if(session.isOpened()){
	                Request.newMeRequest(session, new Request.GraphUserCallback() {
	     
	                    @Override
	                    public void onCompleted(GraphUser user, Response response) {
	                        response.getError();
	                        
	                        
	                        System.err.println(" getId  :  " + user.getId());
	                        System.err.println(" getFirstName  :  " + user.getFirstName());
	                        System.err.println(" getLastName  :  " + user.getLastName());
	                        System.err.println(" getMiddleName  :  " + user.getMiddleName());
	                        System.err.println(" getBirthday  :  " + user.getBirthday());
	                        //System.err.println(" getLink  :  " + user.getLink());
	                        //System.err.println(" getName  :  " + user.getName());
	                        //System.err.println(" getUsername :  " + user.getUsername());
	                        //System.err.println(" getLocation :  " + user.getLocation());
	                        //System.err.println("getRawResponse  :  " + response.getRawResponse());
	                       
	                         
	                        Log.d("tag01"," getId  :  " + user.getId() );
	                        Log.d("tag01"," getFirstName  :  " + user.getFirstName() );
	                        Log.d("tag01"," getLastName  :  " + user.getLastName() );
	                        Log.d("tag01"," getMiddleName  :  " + user.getMiddleName() );
	                        Log.d("tag01"," getBirthday  :  " + user.getBirthday() );
	                         
	                        
	                        
	                       
	                        
	                    }
	                }).executeAsync();
	            }
	        }

			*/
		
		
		@SuppressLint("NewApi")
		protected void onActivityResult (int requestCode, int resultCode, Intent data) {
			switch (requestCode){
			case SETTING:
				Log.d("tag02","onActivityResult");
				
				
				
				//if(!initstate)
				setContentView(R.layout.main);
				
				
				
				LinearLayout a = (LinearLayout)findViewById(R.id.mainlinear);
				Log.d("tag03","LinearLayout");
				
				
				a.setBackgroundResource(R.drawable.background);
				//a.setBackground( (Drawable)getResources().getDrawable(R.drawable.coin_background2));
				Log.d("tag03","setBackgroundResource");
				
				
				//coinBlockIntroActivity b = new coinBlockIntroActivity();
				//b.setBackground( (Drawable)getResources().getDrawable(R.drawable.coin_background2));
						
				
				InitStateText = (TextView)findViewById(R.id.welcome);
				InitStateText.setText("상자를 열어라");
				
			}
			
			
		}
		
		
	
	
	
	
}