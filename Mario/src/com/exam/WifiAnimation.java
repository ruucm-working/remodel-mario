package com.exam;

import com.exam.view.*;

import android.content.*;
import android.graphics.*;
import android.util.*;

public class WifiAnimation implements IAnimatable {
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.wifi_sample4);
	
	//private Context context;
	
	private int[] widthModifier = { 3, -3, 2, -2, 1, -1, 0, -0 };
	
	
	public WifiAnimation( Sprite sp) {
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
	
	
	private int spriteVib = 0;

	public boolean AnimationFinished() {
		return false;
	}

	public void Draw(Bitmap canvas) {			
		// Draw the brick at bottom
		//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
		//진동할때의 하단드로블 

		//Sprite sp2 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);

		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
				-(int)(widthModifier[spriteVib] * CoinBlockView.getInstance().getDensity()), 0);

		if (spriteVib < 7) 
			spriteVib++;
		/*
		if (blockVib >= 7){
			context.setState(new Lv0WaitState(context));
			Log.v("tag4", "blockVib >= heightModifier.length)"+Integer.toString(blockVib));
		}
		 */
	}	
	
	
	
}