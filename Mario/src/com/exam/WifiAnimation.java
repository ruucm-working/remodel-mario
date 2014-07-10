package com.exam;

import android.graphics.Bitmap;
import android.util.Log;

public class WifiAnimation implements IAnimatable {
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.wifi_sample4);
	
	public WifiAnimation() {
		Log.v("WIFI", "object created");
		
	}

	public boolean AnimationFinished() {
		return false;
	}

	public void Draw(Bitmap canvas) {		
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0);
	}
}