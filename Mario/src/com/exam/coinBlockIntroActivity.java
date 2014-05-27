package com.exam;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;

public class coinBlockIntroActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void mOnClick(View v){
		switch(v.getId()){
		case R.id.setbutton:    		
			Intent intent = new Intent(this, Setting.class);
			startActivity(intent); 		
			break;
		}
	}
}