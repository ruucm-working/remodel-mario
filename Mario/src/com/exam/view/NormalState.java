package com.exam.view;

import android.graphics.*;
import android.util.*;

import com.exam.*;

class NormalState implements ICoinBlockViewState {
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
		
		//long second = coinBlockIntroActivity.second;
		long second = 0;
		
		if (second >=10 && second < 20  && isMushroomCreated == false){
			isMushroomCreated = true;
			viewContext.setState(new InitState(viewContext));
			Log.v("tag2", "lv0");   
		}
		
		
	}

	@Override
	public void OnOften(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnInit(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnDoubleClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnShake(CoinBlockView viewContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnWifi(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}
}