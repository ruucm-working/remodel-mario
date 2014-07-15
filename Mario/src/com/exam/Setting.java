package com.exam;

import java.io.*;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class Setting extends Activity { 
	private static final String TAG = "Setting_TAG";
	private static final boolean DEVELOPER_MODE = true;

	
	
	
	//facebook profile
	String userFirstName ;
	String userLastName ;
	
	
	//count click
	
	TextView clicountinit;
	TextView clicount0_1;
	TextView clicount0_2;
	TextView clicount1;
	TextView clicount2;
	TextView clisp0;
	TextView clisp1;
	TextView clisp2;
	
	
	
	//Mesuring Time
	static long count = 0;
	static TextView time;	
	//public static long second = 60;

	//프레퍼런스 값들
	 public static TextPref mPref;		
	 public static TextPref fbPref;	
	String stNum1;
	String stNum2;
	
	int spTag1;
	int spTag2;
	int spTag3;
	Boolean checked[] = new Boolean[20];
	
	
	
	
	
	//스피너 변수들
	ArrayAdapter<CharSequence> adspin1;
	ArrayAdapter<CharSequence> adspin2;
	ArrayAdapter<CharSequence> adspin3;
	boolean mInitSpinner;
	
	
	//for dialog
	
	boolean preinit;
	
	//static variables	
	public static boolean init = false;
	public static boolean lv0_1 = false;
	public static boolean lv0_2 = false;
	public static boolean lv1 = false;
	public static boolean lv2 = false;

	
	public static int CliCountinit;
	public static int CliCount0_1;
	public static int CliCount0_2;
	public static int CliCount1;
	public static int CliCount2;
	
	public static int nowBattery = -1;
	
	float CliSp0;
	
	
	
	

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void onCreate(Bundle savedInstanceState) {

		if (DEVELOPER_MODE) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectDiskReads()
			.detectDiskWrites()
			.detectNetwork()
			.penaltyLog()
			.build());
		}		
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.settingpage);             
		Log.d(TAG, "setting view"); 
		 
		//time = (TextView)findViewById(R.id.time);
		
		
	
		
		Log.d(TAG, "time01");
		Log.d("tag3", "time01");

		//프레퍼런스 읽어오기   
		File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SsdamSsdam"); // dir : 생성하고자 하는 경로
		if(!saveDir.exists()) 
		{
			saveDir.mkdirs();
		}


		try {
			mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
			fbPref = new TextPref("mnt/sdcard/SsdamSsdam/facebookprofile.txt");
			
			Log.d("Setting", "fbPref");

		} catch (Exception e) { 
			e.printStackTrace();
		}       


		mPref.Ready();
		fbPref.Ready();
		
		

		TextView Num1;
		TextView Num2;
		Num1 = (TextView)findViewById(R.id.input01);		
		stNum1 = mPref.ReadString("stNum1","0");
		Num1.setText(stNum1);

		spTag1 = mPref.ReadInt("Tag1", 0);
		spTag2 = mPref.ReadInt("Tag2", 0);
		spTag3 = mPref.ReadInt("Tag3", 0); 
		
		
		CliCountinit = mPref.ReadInt("clicountinit", 0);
		CliCount0_1 = mPref.ReadInt("clicount0_1", 0);
		CliCount0_2 = mPref.ReadInt("clicount0_2", 0);
		CliCount1 = mPref.ReadInt("clicount1", 0);
		CliCount2 = mPref.ReadInt("clicount2", 0);
		
		
		clicountinit = (TextView)findViewById(R.id.clicountinit);		
		clicountinit.setText( CliCountinit + "번 ");
		clicount0_1 = (TextView)findViewById(R.id.clicount0);		
		clicount0_1.setText( clicount0_1 + "번 ");
		clicount0_2 = (TextView)findViewById(R.id.clicount0_2);		
		clicount0_2.setText( CliCount0_2 + "번 ");
		clicount1 = (TextView)findViewById(R.id.clicount1);		
		clicount1.setText( CliCount1 + "번 ");
		clicount2 = (TextView)findViewById(R.id.clicount2);		
		clicount2.setText( CliCount2 + "번 ");


		CliSp0 =  getSecondperCount(600);	
		clisp0 = (TextView)findViewById(R.id.clisp0);		
		clisp0.setText( "(" + CliSp0 + "/m)");
		
		

		Log.d("Setting","init1"+init);
		
		
		
		
		//state's preference
		preinit = mPref.ReadBoolean("preinitstate", true);
		init = mPref.ReadBoolean("initstate", false);
		lv0_1 = mPref.ReadBoolean("lv0_1state", false);
		lv0_2 = mPref.ReadBoolean("lv0_2state", false);
		lv1 = mPref.ReadBoolean("lv1state", false);
		lv2 = mPref.ReadBoolean("lv2state", false);
		Log.d("Setting","init2"+init);
		
		
		
		checked[0] = mPref.ReadBoolean("checked0", false);
		checked[1] = mPref.ReadBoolean("checked1", false);
		checked[2] = mPref.ReadBoolean("checked2", false);
		checked[3] = mPref.ReadBoolean("checked3", false);
		checked[4] = mPref.ReadBoolean("checked4", false);
		checked[5] = mPref.ReadBoolean("checked5", false);
		checked[6] = mPref.ReadBoolean("checked6", false);
		checked[7] = mPref.ReadBoolean("checked7", false);
		checked[8] = mPref.ReadBoolean("checked8", false);
		checked[9] = mPref.ReadBoolean("checked9", false);
		checked[10] = mPref.ReadBoolean("checked10", false);
		checked[11] = mPref.ReadBoolean("checked11", false);
		checked[12] = mPref.ReadBoolean("checked12", false);
		checked[13] = mPref.ReadBoolean("checked13", false);

		
		userFirstName = fbPref.ReadString("userFirstName", "");
		Log.d("Setting", "fbPref.ReadString();");
		userLastName = fbPref.ReadString("userLastName", "");
		Log.d("Setting", "fbPref.userLastName();");
		
		
		mPref.EndReady();
		fbPref.EndReady();

		
		Log.d("Setting", "init"+init);
		
		//체크박스 값에 따라 체크해주기

		if(checked[0]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind1);			
			bo.setChecked(true);
		}
		if(checked[1]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind2);
			bo.setChecked(true);
		}
		if(checked[2]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind3);
			bo.setChecked(true);
		}
		if(checked[3]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind4);
			bo.setChecked(true);
		}
		if(checked[4]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind5);
			bo.setChecked(true);
		}
		if(checked[5]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind6);
			bo.setChecked(true);
		}
		if(checked[6]){
			CheckBox bo = (CheckBox)findViewById(R.id.for1);
			bo.setChecked(true);
		}
		if(checked[7]){
			CheckBox bo = (CheckBox)findViewById(R.id.for2);
			bo.setChecked(true);
		}
		if(checked[8]){
			CheckBox bo = (CheckBox)findViewById(R.id.for3);
			bo.setChecked(true);
		}
		if(checked[9]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry1);
			bo.setChecked(true);
		}
		if(checked[10]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry2);
			bo.setChecked(true);
		}
		if(checked[11]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry3);
			bo.setChecked(true);
		}
		if(checked[12]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry4);
			bo.setChecked(true);
		}
		if(checked[13]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry5);
			bo.setChecked(true);
		}

		Spinner spin1 = (Spinner)findViewById(R.id.myspinner1);
		spin1.setPrompt("안녕스피너");

		adspin1 = ArrayAdapter.createFromResource(this, R.array.nation, 
				android.R.layout.simple_spinner_item);
		adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin1.setAdapter(adspin1);

		
		Log.d("Setting", "setAdapter.EndReady();");
		
		spin1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				//�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return;
				}

				Toast.makeText(Setting.this,adspin1.getItem(position) + "를 선택했지 난.",
						Toast.LENGTH_SHORT).show();
				//프레퍼런스에 기록하기
				spTag1 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		//스피너 초기값지정
		spin1.setSelection(spTag1);

		Spinner spin2 = (Spinner)findViewById(R.id.myspinner2);
		spin2.setPrompt("안녕스피너");

		adspin2 = ArrayAdapter.createFromResource(this, R.array.vintage, 
				android.R.layout.simple_spinner_item);
		adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin2.setAdapter(adspin2);

		spin2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				//�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return;
				}
				//프레퍼런스에 기록하기
				spTag2 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		Log.d("Setting", "onNothingSelected.EndReady();");
		
		//스피너 초기값지정
		spin2.setSelection(spTag2);

		Spinner spin3 = (Spinner)findViewById(R.id.myspinner3);
		spin3.setPrompt("안녕스피너");

		adspin3 = ArrayAdapter.createFromResource(this, R.array.vintage, 
				android.R.layout.simple_spinner_item);
		adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin3.setAdapter(adspin3);

		Log.d("Setting", "spin3.setAdapter.EndReady();");
		
		spin3.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				//�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return; 
				}
				//프레퍼런스에 기록하기
				spTag3 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
			
			
			
		});
		
		Log.d("Setting", "setOnItemSelectedListener.setAdapter.EndReady();");
		//스피너 초기값지정
		spin3.setSelection(spTag3);
		Log.d("Setting", "spin3.setAdapter.EndReady();");
		

	} 


	public static long getSecond(long milli){
		long secondValue = 0;
		secondValue = milli / 10; 
		return secondValue;
	}
	
	public static long getMinute(long milli){
		long secondValue = 0;
		secondValue = milli / 600;
		return secondValue;
	}
	
	
	public static float getMinuteperCount(int clickcount){
		
		float clisp = 0 ;
		
		if (count > 10){
		clisp = clickcount/count*600;
		}		
		
		
		return clisp;
	}
	
	public static float getSecondperCount(int clickcount){
		
		float clisp = 0 ;
		
		if (count > 10){
		clisp = clickcount/count*10;
		}		
		
		
		return clisp;
	}
	

	public void onPause() {
		super.onPause();
		/* 
		mPref.BulkWriteReady(1000);
		mPref.BulkWrite("Name", Name);
		mPref.BulkWrite("StNum", Integer.toString(StNum));
		mPref.CommitWrite();
		//*/
	}

	public void mOnClick(View v){
		Log.d(TAG,"monclick");

		switch(v.getId()){    	

		case R.id.ok:    		
			Intent intent = new Intent();    		
			setResult(RESULT_OK,intent)	;
			Log.d(TAG,"setOK");

			mPref.Ready();

			TextView txtname1 = (TextView)findViewById(R.id.input01);
			stNum1 = txtname1.getText().toString();

			mPref.WriteString("stNum1", stNum1);
			mPref.WriteString("stNum2", stNum2);

			mPref.WriteInt("Tag1", spTag1);
			mPref.WriteInt("Tag2", spTag2);
			mPref.WriteInt("Tag3", spTag3);
			mPref.WriteInt("clicountinit", CliCountinit);
			mPref.WriteInt("clicount0_1", CliCount0_1);
			mPref.WriteInt("clicount0_2", CliCount0_2);
			mPref.WriteInt("clicount1", CliCount1); 
			mPref.WriteInt("clicount2", CliCount2);

			
			mPref.WriteBoolean("checked0", checked[0]);
			mPref.WriteBoolean("checked1", checked[1]);
			mPref.WriteBoolean("checked2", checked[2]);
			mPref.WriteBoolean("checked3", checked[3]);
			mPref.WriteBoolean("checked4", checked[4]);
			mPref.WriteBoolean("checked5", checked[5]);
			mPref.WriteBoolean("checked6", checked[6]);
			mPref.WriteBoolean("checked7", checked[7]);
			mPref.WriteBoolean("checked8", checked[8]);
			mPref.WriteBoolean("checked9", checked[9]);
			mPref.WriteBoolean("checked10", checked[10]);
			mPref.WriteBoolean("checked11", checked[11]);
			mPref.WriteBoolean("checked12", checked[12]);
			mPref.WriteBoolean("checked13", checked[13]);
			
			
			mPref.WriteBoolean("lv0_1state", lv0_1);
			mPref.WriteBoolean("lv0_2state", lv0_2);
			mPref.WriteBoolean("lv1state", lv1);
			mPref.WriteBoolean("lv2state", lv2);
			
			

			mPref.CommitWrite();
			 
			
			if (preinit){
				DialogSimple();
				preinit = false ;
				init = true;
				mPref.Ready();
				mPref.WriteBoolean("preinitstate", preinit);
				mPref.WriteBoolean("initstate", init);
				mPref.CommitWrite();
			}
			else 
				finish();
			
			
			
			//finish();
			
			break;

		case R.id.cancled:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.reset3:
			Spinner spin2 = (Spinner)findViewById(R.id.myspinner2);
			spin2.setSelection(0);
			Spinner spin3 = (Spinner)findViewById(R.id.myspinner3);
			spin3.setSelection(0);
			break;

	
			
		case R.id.reset4:
			 
			CliCountinit = 0;
			CliCount0_1 = 0;
			CliCount0_2 = 0;
			CliCount1 = 0;
			CliCount2 = 0;
			
			clicountinit.setText( CliCountinit + "번 ");
			clicount0_1.setText( clicount0_1 + "번 ");
			clicount0_2.setText( CliCount0_2 + "번 ");
			clicount1.setText( CliCount1 + "번 ");
			clicount2.setText( CliCount2 + "번 ");
			
			break;

		case R.id.kind1:
			checked[0] = !checked[0];
			break;
		case R.id.kind2:
			checked[1] = !checked[1];
			break;
		case R.id.kind3:
			checked[2] = !checked[2];
			break;
		case R.id.kind4:
			checked[3] = !checked[3];
			break;
		case R.id.kind5:
			checked[4] = !checked[4];
			break;
		case R.id.kind6:
			checked[5] = !checked[5];
			break;
		case R.id.for1:
			checked[6] = !checked[6];
			break;
		case R.id.for2:
			checked[7] = !checked[7];
			break;
		case R.id.for3:
			checked[8] = !checked[8];
			break;
		case R.id.dry1:
			checked[9] = !checked[9];
			break;
		case R.id.dry2:
			checked[10] = !checked[10];
			Log.d(TAG,"checked[10]!");
			break;
		case R.id.dry3:
			checked[11] = !checked[11];
			break;
		case R.id.dry4:
			checked[12] = !checked[12];
			break;
		case R.id.dry5:
			checked[13] = !checked[13];			
			break;
		}
	}
	
	
	
	
	private void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage(userFirstName +" "+ userLastName+" 님 당신의 위젯은 아직 깨어나지 않았습니다. 홈화면에서 위젯을 설치하고, 위젯을 자극해서 알을 받으세요! 핸드폰의 기종와 IMEI 번호를 반영해 알이 배정될 예정입니다.").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'Yes' Button
                    	
                    	finish();
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                    	
                    	
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("반갑습니다");
        // Icon for AlertDialog
        alert.setIcon(R.drawable.icon);
        alert.show();
    }
	
	
	
}