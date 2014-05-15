package com.exam;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class coinBlockWidgetProvider extends AppWidgetProvider {
	
	public static final String TAG = "block";

	
        @Override
        public void onDeleted(Context context, int[] appWidgetIds) {
                super.onDeleted(context, appWidgetIds);
                for (int x : appWidgetIds) {
                        ((CoinBlockWidgetApp) context.getApplicationContext()).DeleteWidget(x);
                }
        }

        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
                super.onUpdate(context, appWidgetManager, appWidgetIds);

                for (int i=0; i<appWidgetIds.length; i++)
                {
                        ((CoinBlockWidgetApp) context.getApplicationContext()).UpdateWidget(appWidgetIds[i]);
                        Log.d(TAG,"onUpdate"+appWidgetIds.length);
                }
        }

        @Override
        public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                if (intent.getAction().startsWith("com.gueei")) {
                        int id = intent.getIntExtra("widgetId", 0);
                        ((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnClick();
                        Log.d(TAG,"com.gueei");
                        
                }
                else if (intent.getAction().startsWith("com.often")){
                	
                }
                else if (intent.getAction().startsWith("com.evolve")){ 
                	int id = intent.getIntExtra("widgetId", 0);
                    ((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnEvolve();         
                    
                    Log.d(TAG,"com.evolve");
                }
                
                
                
                
                
        }
}