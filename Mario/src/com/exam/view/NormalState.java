package com.exam.view;

import android.graphics.*;
import android.util.*;

import com.exam.*;

class NormalState implements ICoinBlockViewState {
	private static final String TAG = "Setting_TAG";
	boolean isMushroomCreated = false;
	
	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_question);
		// Draw the brick at bottom
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
	}

	public void OnClick(CoinBlockView viewContext) {
		long chance = System.currentTimeMillis() % 8;
		if (chance < 3)
			viewContext.setState(new MushroomState(viewContext));
		else if (chance < 6)
			viewContext.setState(new FlowerState(viewContext));
		else
			viewContext.setState(new CoinState(viewContext));
	}

	public boolean NeedRedraw() {
		return false;
	}

	@Override 
	public void OnEvolve(CoinBlockView viewContext) {
		// TODO Auto-generated method stub
		
		Log.v("tag2", "state-onevolve");
		
		long second = Setting.second; 
		Log.v("tag2", "state-second"+Long.toString(second));
		
		if (second >=10 && second < 20  && isMushroomCreated == false){
		//	Setting.MakeNotification();
			
			isMushroomCreated = true;
			viewContext.setState(new Lv0State(viewContext));
			Log.v("tag2", "lv0");   
			
			// System services not available to activities before onCreate()
			// 때문에 진행이 전혀 되지 않고있다...
			Log.v(TAG, "1");
			Notify n = Notify.getInstance();
			n.MakeNotification("TopMessage", "yeah", "butcher anthem!");
		}
	}

	@Override
	public void OnOften(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}
}