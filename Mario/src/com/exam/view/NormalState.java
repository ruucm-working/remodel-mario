package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.os.*;
import android.util.*;

import com.exam.*;
import com.exam.view.Lv1State.*;

class NormalState implements ICoinBlockViewState {
	
	Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
	
	

	
	
	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		Log.d(coinBlockWidgetProvider.TAG2,"Draw(rviews);");
		SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter); 
		//if (animStage >= heightModifier.length)
			viewContext.setState(new NormalWaitState(viewContext));
	}

	
	 
	
	private class NormalWaitState implements ICoinBlockViewState {
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_question);
		MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;

		public NormalWaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;
			
			
			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == NormalWaitState.class)
					{
						//mViewContext.addAnimatable(lv1Anim);
						mViewContext.setState(new OftenState(mViewContext, sp1));
						
						//lv1Anim.Draw2(Bitmap.createBitmap(mViewContext.cwidth, mViewContext.cheight, Bitmap.Config.ARGB_8888));
						//mViewContext.scheduleRedraw();
						
						
						
					}
				} 
			}, 5000); 
			
			
		}

		@Override
		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void OnClick(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean NeedRedraw() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void OnEvolve(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
		}
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
	public void OnOften(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void OnEvolve(CoinBlockView viewContext) {
		// TODO Auto-generated method stub
		
		

		viewContext.setState(new Lv1State(viewContext));
		
		
	}




}
	
	
