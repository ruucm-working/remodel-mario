package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;
import com.exam.*;
import com.exam.view.InitState.*;
import com.exam.view.Lv0_1State.*;

public class Lv1State implements ICoinBlockViewState {
	
	CoinBlockView context;
	
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.samsung);
	Sprite bottom = MediaAssets.getInstance().getSprite(R.drawable.egg_break);
	
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	MediaPlayer snd1 = MediaAssets.getInstance().getSoundPlayer(R.raw.notify_sound);
	
	Lv1OftenAnim lv1ofAnim;// here 
	Lv1Animation lv1Anim; 
	Lv1ClickAnim lv1clAnim;
	Lv1DblClickAnim lv1dblAnim;
	
	private int animStage = 0; 
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 6, -6, 4, -4, 2, -2, 0, 0 };	// here

	public Lv1State(CoinBlockView viewContext) {
		context = viewContext; 
		lv1Anim = new Lv1Animation();
		viewContext.addAnimatable(lv1Anim);
		snd.seekTo(0); 
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start();
			}
		});
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		
		//진동할때의 하단드로블
		SpriteHelper.DrawSprite(canvas, bottom, 0, SpriteHelper.DrawPosition.BottomCenter,0,
				-(int)(heightModifier[animStage] * viewContext.getDensity()));
		
	
		animStage++;
		if (animStage >= heightModifier.length)
			viewContext.setState(new Lv1WaitState(viewContext));
	}

	public boolean NeedRedraw() {
		return true;
	} 
	public void OnClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub 
	}

	private class Lv1WaitState implements ICoinBlockViewState {
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.egg_break);
		//진동후의, 하단 드로블
		MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;

		public Lv1WaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;
			
			
			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == Lv1WaitState.class)
					{
						//mViewContext.addAnimatable(lv1Anim);
				
						//mViewContext.setState(new OftenState(mViewContext, sp)); 
						
						//lv1Anim.Draw2(Bitmap.createBitmap(mViewContext.cwidth, mViewContext.cheight, Bitmap.Config.ARGB_8888));
						//mViewContext.scheduleRedraw();
					}
				}
			}, 5000);
		}

		public void OnClick(CoinBlockView viewContext) {
			viewContext.removeAnimatable(lv1Anim);
			viewContext.removeAnimatable(lv1ofAnim);
			viewContext.removeAnimatable(lv1clAnim);
			
			lv1clAnim = new Lv1ClickAnim();			
			viewContext.addAnimatable(lv1clAnim);
			
			snd.seekTo(0);
			snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd.start();
				}
			});
			
			Setting.CliCount1++;
			Setting.mPref.Ready();			
			Setting.mPref.WriteInt("clicount1", Setting.CliCount1);			
			Setting.mPref.CommitWrite();
		}

		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			//SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
		}

		public boolean NeedRedraw() { 
			return false;
		}

		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			coinBlockView.setState(new Lv2State(coinBlockView));
			
		}
 
		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			coinBlockView.removeAnimatable(lv1Anim);
			coinBlockView.removeAnimatable(lv1clAnim);
			coinBlockView.removeAnimatable(lv1ofAnim);
			lv1ofAnim = new Lv1OftenAnim();			
			coinBlockView.addAnimatable(lv1ofAnim);
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			coinBlockView.removeAnimatable(lv1Anim);	
			coinBlockView.removeAnimatable(lv1ofAnim);
			coinBlockView.removeAnimatable(lv1clAnim);
		}

		@Override
		public void OnDoubleClick(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void OnShake(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			
		}
	}

	private class Lv1Animation implements IAnimatable {
		//진동할때 올라오고, 상단에 남는 드로블
		private int flowerRaise = 4;
		//private int flowerRaise2 = 4;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp, 0,
							SpriteHelper.DrawPosition.BottomCenter, 0, -(int) (flowerRaise * 4 * context.getDensity()));
			
			Sprite bottom2 = MediaAssets.getInstance().getSprite(R.drawable.eggs_break);
			SpriteHelper.DrawSprite(canvas, bottom2, 0, SpriteHelper.DrawPosition.BottomCenter);
	
			// Draw the flower
			if (flowerRaise < 8) {
				flowerRaise++; 
			}
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

	@Override
	public void OnInit(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}
	
	private class Lv1OftenAnim implements IAnimatable {
		private int blockVib = 0;
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
			//진동할때의 하단드로블
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);
			
			if (blockVib < 7)
				blockVib++;
		
			Log.v("tag4", "blockVib"+Integer.toString(blockVib));
			
			/*
			if (blockVib >= 7){
				context.setState(new Lv0WaitState(context));
				Log.v("tag4", "blockVib >= heightModifier.length)"+Integer.toString(blockVib));
			}
			*/
		}
	}
	
	private class Lv1ClickAnim implements IAnimatable {
		private int spriteVib = 0;
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
			//진동할때의 하단드로블
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[spriteVib] * context.getDensity()), 0 );
			
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
	
	private class Lv1DblClickAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 16, -16, 8, -8, 4, -4, 0, 0 };	// here

		public Lv1DblClickAnim()
		{
			Log.v("DOUBLECLICK","butcher & sum");
		}

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
			//진동할때의 하단드로블

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7) { 
				blockVib++;
			}

			/*
			if (blockVib >= 7){
				context.setState(new Lv0WaitState(context));
				Log.v("tag4", "blockVib >= heightModifier.length)"+Integer.toString(blockVib));
			}
			 */
		}
	}
	
	@Override
	public void OnDoubleClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub
		viewContext.removeAnimatable(lv1dblAnim);

		lv1dblAnim = new Lv1DblClickAnim();			
		viewContext.addAnimatable(lv1dblAnim);

		snd1.seekTo(0);
		snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd1.start();
			}
		});

		Log.v("DOUBLECLICK", "Entering Doubleclick");

		Setting.CliDblClick++;
		Setting.mPref.Ready();
		Setting.mPref.WriteInt("dblclick", Setting.CliDblClick);			
		Setting.mPref.CommitWrite();
	}

	@Override
	public void OnShake(CoinBlockView viewContext) {
		// TODO Auto-generated method stub
		
	}
}