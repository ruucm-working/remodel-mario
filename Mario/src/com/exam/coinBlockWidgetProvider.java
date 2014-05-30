package com.exam;


import android.appwidget.*;
import android.content.*;
import android.util.*;

public class coinBlockWidgetProvider extends AppWidgetProvider {

	public static final String TAG = "block";
	public static final String TAG2 = "anim";

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		for (int x : appWidgetIds) {
			((CoinBlockWidgetApp) context.getApplicationContext()).DeleteWidget(x);
		}
	}
	
	@Override
	public void onEnabled(Context context)
	{
		Log.i(TAG, "======================= onEnabled() =======================");
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		for (int i=0; i<appWidgetIds.length; i++)
		{
			((CoinBlockWidgetApp) context.getApplicationContext()).UpdateWidget(appWidgetIds[i]);
			Log.d(TAG,"onUpdate"+appWidgetIds);
		}
		
		Log.d(coinBlockWidgetProvider.TAG,"onUpdate;");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		Log.d(TAG,"onReceive");

		if (intent.getAction().startsWith("com.gueei")) {
			int id = intent.getIntExtra("widgetId", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnClick();
			Log.d(TAG,"com.gueei");
		}
		else if (intent.getAction().startsWith("com.exam.view.INTENT_OFTEN_FORMAT")){
			int id = intent.getIntExtra("widgetId2", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnOften();         

		}
		else if (intent.getAction().startsWith("com.exam.view.INTENT_EVOLVE_FORMAT")){ 
			int id = intent.getIntExtra("widgetId10", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnEvolve();
			Log.d("tag2","provider - onenvolve");
		}
	}
}