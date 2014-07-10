package com.exam;



import java.io.*;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.facebook.*;
import com.facebook.model.*;


public class coinBlockLoginActivity extends Activity
{
	/** Called when the activity is first created. */
	
	
	  
	 
	
	//facebook	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private Button buttonLoginLogout;
    
    
    
    
    //facebook login profile
    
    String userId ;
    String userFirstName ;
    String userLastName ;
    
    
	
    
    //프레퍼런스 
    public static TextPref mPref;	
  	public static TextPref fbPref;	
  	
  	boolean DialogOn ;
  	
  

	

	

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
			
		
		init();
        dataInit();
        facebookInit(savedInstanceState);
		
        
        //Ready next activity intent
        
        
        
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
        
        
		
      		
      		DialogOn = mPref.ReadBoolean("dialogon", true);
      		
      		userId = fbPref.ReadString("userId", "");
      		userFirstName = fbPref.ReadString("userFirstName", "");
      		userLastName = fbPref.ReadString("userLastName", "");
      		
      		
      		mPref.EndReady();
      		fbPref.EndReady();
      		
      		
      		
        
	} 
	
	
	
		
		
		//Facebook Login
		
		private void init() {
	        buttonLoginLogout = (Button)findViewById(R.id.buttonLoginLogout1);
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
	                
	                /*
	                
	                Intent intent = new Intent(this, coinBlockIntroActivity.class);
		            //intent.putExtra("userId", userId);
		            //intent.putExtra("userFirstName", userFirstName);
		            //intent.putExtra("userLastName", userLastName);
					startActivity(intent); 
	                
					/*
	                if(DialogOn){
	                

		            Intent intent = new Intent(this, coinBlockIntroActivity.class);
		            //intent.putExtra("userId", userId);
		            //intent.putExtra("userFirstName", userFirstName);
		            //intent.putExtra("userLastName", userLastName);
					startActivity(intent); 
	                }
	                
	                else {
	                	Intent intent = new Intent(this, coinBlockIntroActivity2.class);
						startActivity(intent);
	                }
	                
	                */
					Log.d("tag02","userFirstName"+ userFirstName );
		            
					 
					
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
	        
	        
	        private void toIntro() {

	        	Intent intent = new Intent(this, coinBlockIntroActivity.class);
	            //intent.putExtra("userId", userId);
	            //intent.putExtra("userFirstName", userFirstName);
	            //intent.putExtra("userLastName", userLastName);
				startActivity(intent);
	        	
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
	                        
	                        /*
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
	                        */
	                         
	                        /* 
	                        Log.d("tag01"," getId  :  " + user.getId() );
	                        Log.d("tag01"," getFirstName  :  " + user.getFirstName() );
	                        Log.d("tag01"," getLastName  :  " + user.getLastName() );
	                        Log.d("tag01"," getMiddleName  :  " + user.getMiddleName() );
	                        Log.d("tag01"," getBirthday  :  " + user.getBirthday() );
	                        */
	                        
	                        
	                        userId = user.getId() ;
	                        userFirstName = user.getFirstName() ;
	                        userLastName = user.getLastName() ;
	                        
	                        Log.d("tag03","userFirstName2"+userFirstName);
	                        
	                        
	                        fbPref.Ready();
	                        
	                        fbPref.WriteString("userId", userId);
	            			fbPref.WriteString("userFirstName", userFirstName);
	            			fbPref.WriteString("userLastName", userLastName);
	                  		
	                    
	            			Log.d("tag03", "WriteString;");
	                    
	            			
	            			fbPref.CommitWrite();
	            			
	            			toIntro();
	            			finish();
	            			
	            			

	            			
	            			
	                        
	                        
	                    }
	                }).executeAsync();
	            }
	        }

			
		
		
	
	
	
	
}