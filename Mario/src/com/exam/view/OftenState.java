package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;

import com.exam.*;

public class OftenState implements ICoinBlockViewState {
	private Sprite sp1 = null ;
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };		// here
	Lv1Animation lv1Anim;
	CoinBlockView context;

	public OftenState(CoinBlockView viewContext, Sprite sprite) {
		context = viewContext;
		lv1Anim = new Lv1Animation();
		
		sp1 = sprite;
	
		
		viewContext.addAnimatable(lv1Anim);	 
			
		
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
		
		
		
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
				-(int)(heightModifier[animStage] * viewContext.getDensity()), 0);
	
		animStage++;
		if (animStage >= heightModifier.length)
			viewContext.setState(new FlowerWaitState(viewContext));
	}

	public boolean NeedRedraw() {
		return true;
	}

	public void OnClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub
	}

	private class FlowerWaitState implements ICoinBlockViewState {
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_question);
		
		MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;

		public FlowerWaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;
			
			
			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == FlowerWaitState.class)
					{
						mViewContext.setState(new OftenState(mViewContext, sp1));
						
					}
				}
			}, 5000);
			
			
		}

		public void OnClick(CoinBlockView viewContext) {
			viewContext.removeAnimatable(lv1Anim);
			snd.seekTo(0);
			snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd.start();
				}
			});
			viewContext.setState(new DisabledState(viewContext));
		}

		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
		}

		public boolean NeedRedraw() {
			return false;
		}

		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
			
			if (Setting.second >= 20 && Setting.second <25)	{
				coinBlockView.setState(new Lv1State(coinBlockView));
				Log.v("tag2", "waitstate- onevolve");
			}
		
			
			
		}

		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
		}



	}

	private class Lv1Animation implements IAnimatable {
		Sprite flowerSprite = null;
		
		private int flowerRaise = 4;
		private int flowerRaise2 = 4;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp1, sp1.NextFrame(),
							SpriteHelper.DrawPosition.BottomCenter, 0, -(int) (flowerRaise * 4 * context.getDensity()));
			
	
			// Draw the flower
			if (flowerRaise < 8) {
				flowerRaise++;
			}
			
			
		}
		
		
		public void Draw2(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, flowerSprite, flowerSprite.NextFrame(),
							SpriteHelper.DrawPosition.BottomCenter, 0, -(int) (flowerRaise2 * 4 * context.getDensity()));
			
			Log.d(coinBlockWidgetProvider.TAG2,"SpriteHelper(rviews);");
			
			// Draw the flower
			if (flowerRaise2 < 8) {
				flowerRaise2++;
			}
			
			Log.d(coinBlockWidgetProvider.TAG2,"flowerRaise(rviews);");
		}
		
		
	}
	

	

	@Override
	public void OnEvolve(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub

		Log.v("tag2", "often- OnEvolve");
		if (Setting.second >= 20)	{
			coinBlockView.setState(new Lv1State(coinBlockView));
			Log.v("tag2", "lv1");
		}
	
		
		
	}

	@Override
	public void OnOften(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}
	
	
	


}
