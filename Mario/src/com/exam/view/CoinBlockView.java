package com.exam.view;

import java.util.*;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.exam.*;

public class CoinBlockView {
	public static String INTENT_ON_CLICK_FORMAT = "com.gueei.mario.coinBlock.id.%d.click";
	public static String INTENT_OFTEN_FORMAT = "com.exam.view.INTENT_OFTEN_FORMAT";
	public static String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
	public static String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";
	private static final int REFRESH_RATE = 40;
	int cheight;
	private volatile Set<IAnimatable> Children;
	int cwidth;
	private float density;

	private long lastRedrawMillis = 0;
	private static int mWidgetId;
	private static ICoinBlockViewState state;
	
	public static long second = 0;




	//UpdateThread
	UpdateThread thread2;
	
	
	
	
	//Async Task
	
	ViewAsyncTask asynctask = new ViewAsyncTask();
	


	public CoinBlockView(Context context, int widgetId) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);

		density = metrics.density;
		cwidth = (int) (72 * metrics.density);
		cheight = cwidth;

		Children = new HashSet<IAnimatable>();
		mWidgetId = widgetId;
		setState(new InitState(this));




		thread2 = new UpdateThread(mHandler2);
		thread2.start();
		thread2.onStart();
		
		

		




	}


	class UpdateThread extends Thread{
		Handler mHandler;
		boolean sns = false; //Thread를 통제하기 위한 boolean 값
		public void run(){
			while(true){
				if(sns){

					mHandler.sendEmptyMessage(0);
					try{
						Thread.sleep(3000);
					}catch(InterruptedException e){
					}
				}
			}
		}

		//생성자
		public UpdateThread(Handler handler){
			mHandler = handler;
		}

		public void onStart(){
			sns = true;
		}

		public void onStop(){
			sns = false;
		}		

	}




	static Handler mHandler2 = new Handler(){

		RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);


		public void handleMessage(Message msg){

			
			
			//int second = 20;
			//int clicountinit = 3;
			
			//String text = coinBlockIntroActivity.time.getText().toString();
			
			 
			//int second = Integer.parseInt(text);
			//second = coinBlockIntroActivity.second;	
			int clicountinit = Setting.CliCountinit;
			
			Log.d("tag8", Long.toString(second));
			 
			
			second = coinBlockIntroActivity.taskTimer1.time;

   
			if ( second == 0 && clicountinit >=5)
				updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
			else if (second >= 10 && second <= 12)
				updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
			else if (second >= 20 && second <= 22)
				updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
		
			 
			  
			
			if(second >= 5 && second <=10 )			
				updateOftenIntent(rviews, CoinBlockWidgetApp.getApplication());	
			else if (second >= 12 && second <= 20)
				updateOftenIntent(rviews, CoinBlockWidgetApp.getApplication());
			else if (second >= 22 )
				updateOftenIntent(rviews, CoinBlockWidgetApp.getApplication());
			
			 
			
			
		

		}		

	};




	public synchronized void addAnimatable(IAnimatable child)
	{
		Children.add(child);
	}

	public synchronized void removeAnimatable(IAnimatable child)
	{
		Children.remove(child);
	}

	public void createCoin() {
		Children.add(new CoinAnimation(density));
	}

	public static Context getContext() {
		return (CoinBlockWidgetApp.getApplication());
	}

	public float getDensity() {
		return density;
	}

	public int getmWidgetId() {
		return mWidgetId;
	}

	public void OnClick() {
		state.OnClick(this);
	}

	public void OnOften() {
		state.OnOften(this);
//		/scheduleRedraw();
	}


	public void OnEvolve() {
		state.OnEvolve(this);
		Log.d("tag3", "state.OnEvolve");

	}
	
	public void OnInit() {
		state.OnInit(this);
		Log.d("tag3", "state.OnInit");
		
	}
	

	public  void Redraw(Context context) {		// 이 함수는 ㅈ나 많이 루프된다. 입력 안하고 가만있어도 계속 반복되는 듯
		RemoteViews rviews = new RemoteViews(context.getPackageName(), R.layout.coin_block_widget);
		Bitmap canvas = Bitmap.createBitmap(cwidth, cheight, Bitmap.Config.ARGB_8888);

		IAnimatable[] child = new IAnimatable[Children.size()];
		Children.toArray(child);

		for (int i = 0; i < child.length; i++) {
			child[i].Draw(canvas);
			if (child[i].AnimationFinished())
				Children.remove(child[i]);
		}

		state.Draw(this,canvas);
		rviews.setImageViewBitmap(R.id.block, canvas);
		updateClickIntent(rviews);

		Log.d("draw", "Redraw");


		AppWidgetManager.getInstance(context).updateAppWidget(mWidgetId, rviews);

		lastRedrawMillis = SystemClock.uptimeMillis();

		if (state.NeedRedraw() || Children.size() > 0)
			scheduleRedraw();
	}



	void scheduleRedraw() {
		long nextRedraw = lastRedrawMillis + REFRESH_RATE;
		nextRedraw = nextRedraw > SystemClock.uptimeMillis() ? nextRedraw :
			SystemClock.uptimeMillis() + REFRESH_RATE;
		
		Log.d("draw", "scheduleRedraw"+Long.toString(nextRedraw));
		
		scheduleRedrawAt(nextRedraw);
	}

	private  void scheduleRedrawAt(long timeMillis) {
		(new Handler()).postAtTime(new Runnable() {
			public void run() {
				Redraw(CoinBlockWidgetApp.getApplication());
			}
		}, timeMillis);
		
		Log.d("draw", "scheduleRedrawAt");
		
	}

	public  void setState(ICoinBlockViewState newState) {
		state = newState;
		scheduleRedraw();
		
		Log.v("log1","setstate");
	}

	public ICoinBlockViewState getState(){
		return state;
	}

	private void updateClickIntent(RemoteViews rviews)
	{
		Intent intent = new Intent(String.format(INTENT_ON_CLICK_FORMAT, mWidgetId));
		intent.setClass(getContext(), coinBlockWidgetProvider.class);
		intent.putExtra("widgetId", mWidgetId);
		PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		rviews.setOnClickPendingIntent(R.id.widget, pi);

		
	}


	private static void updateOftenIntent(RemoteViews rviews, Context context) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(String.format(INTENT_OFTEN_FORMAT, mWidgetId));
		intent.putExtra("widgetId2", mWidgetId);				


		context.sendBroadcast(intent);



	}

	private static void updateEvolveIntent(RemoteViews rviews, Context context) {
		// TODO Auto-generated method stub				

		
			Intent intent = new Intent(String.format(INTENT_INIT_FORMAT, mWidgetId));
			intent.putExtra("widgetId11", mWidgetId);		

			context.sendBroadcast(intent);
	
		
		
		
			Intent intent2 = new Intent(String.format(INTENT_EVOLVE_FORMAT, mWidgetId));
			intent2.putExtra("widgetId10", mWidgetId);				

			context.sendBroadcast(intent2);
	

		Log.d(coinBlockWidgetProvider.TAG," updateEvolveIntent(Remo(rviews);");


	}
	
	
	private static void updateInitIntent(RemoteViews rviews, Context context) {
		// TODO Auto-generated method stub				

		
			Intent intent = new Intent(String.format(INTENT_INIT_FORMAT, mWidgetId));
			intent.putExtra("widgetId11", mWidgetId);				



			context.sendBroadcast(intent);
		


		Log.d(coinBlockWidgetProvider.TAG," updateEvolveIntent(Remo(rviews);");


	}




}
