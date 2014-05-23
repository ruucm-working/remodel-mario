package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;

import com.exam.*;

public class OftenState implements ICoinBlockViewState {
	Sprite sp1 ;
	private int animStage = 0;
	private int[] heightModifier = { 12, 8, 4, 2 };
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
		SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter, 0,
						-(int) (heightModifier[animStage] * viewContext.getDensity()));
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
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
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
			
		}

		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
		}



	}

	private class Lv1Animation implements IAnimatable {
		Sprite flowerSprite = MediaAssets.getInstance().getSprite(R.drawable.samsung_sample);
		private int flowerRaise = 4;
		private int flowerRaise2 = 4;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, flowerSprite, flowerSprite.NextFrame(),
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
		
	}

	@Override
	public void OnOften(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}
	
	
	


}
