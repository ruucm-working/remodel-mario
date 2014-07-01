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
		Log.v("WIFI", "Entering wifi class");
		
		// Draw the brick at bottom
		//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
		//진동할때의 하단드로블

		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0);
	}
}