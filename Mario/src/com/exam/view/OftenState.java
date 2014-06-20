package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;

import com.exam.*;
import com.exam.view.Lv0_1State.Lv0WaitState;

public class OftenState implements ICoinBlockViewState {
	private Sprite sp1 = null ;
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };		// here
	OftenAnimation oftenAnim;
	CoinBlockView context;

	public OftenState(CoinBlockView viewContext, Sprite sprite) {
		context = viewContext;
		oftenAnim = new OftenAnimation();
		sp1 = sprite;

		viewContext.addAnimatable(oftenAnim);	 
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);

		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
				-(int)(heightModifier[animStage] * viewContext.getDensity()), 0);

		animStage++;
		if (animStage >= heightModifier.length)
			viewContext.setState(new OftenWaitState(viewContext));
	}

	public boolean NeedRedraw() {
		return true;
	}

	public void OnClick(CoinBlockView viewContext) {

		// TODO Auto-generated method stub  
	}

	private class OftenWaitState implements ICoinBlockViewState {
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);

		MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;

		public OftenWaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;

			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == OftenWaitState.class)
					{
						if (coinBlockIntroActivity.second >= 20 && coinBlockIntroActivity.second <25)	{	

							//mViewContext.removeAnimatable(Lv0State.lv0Anim);
							//mViewContext.removeAnimatable(oftenAnim);
							mViewContext.setState(new Lv0_1State(mViewContext));


							Log.v("tag3","Setting.second >= 20 && Setting.second <");

						}
						mViewContext.setState(new OftenState(mViewContext, sp1));
					}
				}
			}, 5000);
		}

		public void OnClick(CoinBlockView viewContext) {
			viewContext.removeAnimatable(oftenAnim);
			Log.v("tag3", "OnClick- removeAnimatable");
			snd.seekTo(0);
			snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd.start();
				}
			});
			viewContext.setState(new DisabledState(viewContext));

			Log.v("tag3", "OnClick- OnEvolve");

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
			Log.v("tag2", "waitstate- OnEvolve");

			Log.v("tag2", "waitstate- removeAnimatable");

			/*
			if (Setting.second >= 20 && Setting.second <25)	{
				coinBlockView.removeAnimatable(oftenAnim);
				coinBlockView.setState(new DisabledState(coinBlockView));
				//coinBlockView.setState(new Lv1State(coinBlockView));
				Log.v("tag2", "waitstate- onevolve");
			}
			 */ 
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
		public void onLongClick(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub

		}
	}

	private class OftenAnimation implements IAnimatable {
		private int flowerRaise = 4;
		private int flowerRaise2 = 4;

		public boolean AnimationFinished() {
			return false;
		} 

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp1, sp1.NextFrame(),
					SpriteHelper.DrawPosition.BottomCenter, 0, -(int) (flowerRaise * 4 * context.getDensity()));

			// Draw the flower
			if (flowerRaise < 8)
				flowerRaise++;
		}
	}

	@Override
	public void OnEvolve(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub

		Log.v("tag2", "often- OnEvolve");
		if (coinBlockIntroActivity.second >= 20 && coinBlockIntroActivity.second <25) {
			// added

			//coinBlockView.setState(new Lv1State(coinBlockView));
			Log.v("tag2", "lv1");
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
	public void onLongClick(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub

	}
}