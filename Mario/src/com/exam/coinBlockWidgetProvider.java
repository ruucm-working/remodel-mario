package com.exam;


import android.bluetooth.BluetoothAdapter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.content.Context;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class coinBlockWidgetProvider extends AppWidgetProvider {

	public static final String TAG = "block";
	public static final String TAG2 = "anim";

	// phone status variables
	private static boolean isDown = false;
	private static boolean isSMSNotRead = false;
	private static boolean isBatteryLow = false;
	private static boolean isHeadset = false;
	private static boolean isPlaneMode = false;
	private static boolean isWifiConnected = false;
	private static boolean isBluetoothActivated = false;
	private static boolean isPowerConnected = false;
	private static boolean isUsbAttached = false;
	private static boolean isThreadCreated = false;
	private int nowBattery;
	private static boolean isAdditionalListenerCreated = false;

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
		
		if(!isAdditionalListenerCreated)
		{
			context.getApplicationContext().registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			context.getApplicationContext().registerReceiver(this, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
			isAdditionalListenerCreated = true;
		}

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
		else if (intent.getAction().startsWith("com.exam.view.INTENT_INIT_FORMAT")){ 
			int id = intent.getIntExtra("widgetId11", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnInit();

			Log.d("tag2","provider - onenvolve");
		}

		// Custom Recevier
		// SMS
		else if (intent.getAction().startsWith("android.provider.Telephony.SMS_RECEIVED"))
		{
			Log.v(TAG, "SMS Received");

			isSMSNotRead = true;
			Toast.makeText(context, "Get SMS", Toast.LENGTH_SHORT).show();
			
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Low battery
		else if (intent.getAction().startsWith("android.intent.action.BATTERY_CHANGED"))
		{
			int bLevel = intent.getIntExtra("level", 0);
			Log.v(TAG, "Battery level changed: " + bLevel);

			if(bLevel < 20)
				isBatteryLow = true;
			else
				isBatteryLow = false;
			
			//Toast.makeText(context, "Battery Changed", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// WiFi
		else if (intent.getAction().startsWith("android.net.wifi.STATE_CHANGE"))
		{
			Log.v(TAG, "Wifi Connect state changed");
			NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			isWifiConnected = netInfo.isConnected();
			Toast.makeText(context, "Wifi status changed", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Plane mode
		else if (intent.getAction().startsWith("android.intent.action.AIRPLANE_MODE"))
		{
			if(Settings.System.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) == 1)
				isPlaneMode = true;
			else
				isPlaneMode = false;

			//Toast.makeText(context, "Plane mode status changed", Toast.LENGTH_SHORT).show();
			Log.v(TAG, "isPlaneMode: " + isPlaneMode);

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Bluetooth
		else if (intent.getAction().startsWith("android.bluetooth.adapter.action.STATE_CHANGED"))
		{
			Log.v(TAG, "Bluetooth");
			BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();
			isBluetoothActivated = mBTAdapter.isEnabled();
			//Toast.makeText(context, "Bluetooth status changed", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Power connected
		else if (intent.getAction().startsWith("android.intent.action.ACTION_POWER_CONNECTED"))
		{
			Log.v(TAG, "Power Connected");
			isPowerConnected = true;
			//Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Power disconnected
		else if (intent.getAction().startsWith("android.intent.action.ACTION_POWER_DISCONNECTED"))
		{
			Log.v(TAG, "Power Disconnected");
			isPowerConnected = false;
			//Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Headset
		else if (intent.getAction().startsWith("android.intent.action.HEADSET_PLUG"))
		{
			Log.v(TAG, "Entering headset");

			if(intent.getIntExtra("state", -1) == 1)
			{
				Toast.makeText(context, "Headset connected", Toast.LENGTH_SHORT).show();
				isHeadset = true;
			}	

			else
			{
				Toast.makeText(context, "Headset disconnected", Toast.LENGTH_SHORT).show();
				isHeadset = false;
			}

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// PC connected (not working)
		else if (intent.getAction().startsWith("android.hardware.usb.action.USB_ACCESSORY_ATTACHED"))
		{
			Log.v(TAG, "USB Attached");
			isUsbAttached = true;
			Toast.makeText(context, "USB attached", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// PC disconnected (I don't sure it working or not)
		else if (intent.getAction().startsWith("android.hardware.usb.action.USB_ACCESSORY_DETACHED"))
		{
			Log.v(TAG, "USB Detached");
			isUsbAttached = false;
			Toast.makeText(context, "USB detached", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
	}
}