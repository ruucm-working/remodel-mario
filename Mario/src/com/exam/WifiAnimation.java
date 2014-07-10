package com.exam;

import com.exam.view.*;

import android.content.*;
import android.graphics.*;
import android.util.*;

public class WifiAnimation implements IAnimatable {
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.wifi_sample4);
	
	//private Context context;
	
	private int[] widthModifier = { 3, -3, 2, -2, 1, -1, 0, -0 };
	
	
	public WifiAnimation( ) {
		Log.v("WIFI", "object created");
		//context = context1;
	}
	
	/*

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
	
	*/
	
	
	
	
	
	private int flowerRaise = 4;
	//private int flowerRaise2 = 4;

	public boolean AnimationFinished() {
		return false;
	}

	public void Draw(Bitmap canvas) {
		
		Log.v("WIFI", "Draw Wifi0-1");
		SpriteHelper.DrawSprite(canvas, sp, 0,
						SpriteHelper.DrawPosition.BottomCenter, 16, -(int) 32);
		
		Log.v("WIFI", "DrawSprite Wifi0-1");
		
		Sprite bottom2 = MediaAssets.getInstance().getSprite(R.drawable.eggs_break);
		SpriteHelper.DrawSprite(canvas, bottom2, 0, SpriteHelper.DrawPosition.BottomCenter);

		// Draw the flower
		if (flowerRaise < 8) {
			flowerRaise++; 
		}
	}
	
	
	
}