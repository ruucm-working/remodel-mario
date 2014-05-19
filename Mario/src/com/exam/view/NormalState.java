package com.exam.view;

import android.content.*;
import android.graphics.*;
import android.util.*;

import com.exam.*;

class NormalState implements ICoinBlockViewState {
	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// 여기서 벽돌을 그린다
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_question);
		// Draw the brick at bottom
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
	}

	public void OnClick(CoinBlockView viewContext) {
		long chance = System.currentTimeMillis() % 8;
		if (chance < 8){
			viewContext.setState(new MushroomState(viewContext));		
		}
		/*
		else if (chance < 6)
			viewContext.setState(new FlowerState(viewContext));
		else
			viewContext.setState(new CoinState(viewContext));
		*/
		Log.d(coinBlockWidgetProvider.TAG,"OnClickif (chance < 3){");

		
	}
	
	
	
	
	public void OnEvolve(CoinBlockView viewContext) {
		
			viewContext.setState(new MushroomState(viewContext));
			Log.d(coinBlockWidgetProvider.TAG,"OnEvolve");

			
		
	}
	
	

	public boolean NeedRedraw() {
		return false;
	}
}