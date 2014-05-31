package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;

import com.exam.*;

public class Lv0State implements ICoinBlockViewState {
	
	Sprite flowerSprite = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
	//진동할때 올라오고, 상단에 남는 드로블
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 6, -6, 4, -4, 2, -2, 0, 0 };	// here
	Lv0Animation lv0Anim;
    Lv0OftenAnim lv0ofAnim;
    Lv0ClickAnim lv0clAnim;
	boolean fuck = false;
	CoinBlockView context;

	public Lv0State(CoinBlockView viewContext) {
		context = viewContext;
		lv0Anim = new Lv0Animation();
		lv0ofAnim = new Lv0OftenAnim();
		
		viewContext.addAnimatable(lv0Anim);
		//viewContext.addAnimatable(lv0ofAnim);
		
		
		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start();
			}
		});
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
		//진동할때의 하단드로블
		SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,0,
				-(int)(heightModifier[animStage] * viewContext.getDensity()));
		
	
		animStage++; 
		
		Log.v("tag3", "animstage");
		
		if (animStage >= heightModifier.length)
			viewContext.setState(new Lv0WaitState(viewContext));
	}

	public boolean NeedRedraw() {
		return true;
	}

	public void OnClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub 
	}
	


	

	@Override
	public void OnOften(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void OnEvolve(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	
		
		
	}
	

	@Override
	public void OnInit(CoinBlockView coinBlockView) {
		
		coinBlockView.removeAnimatable(lv0Anim);
		
		
	}
	
	
	

	class Lv0WaitState implements ICoinBlockViewState {
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
		//진동후의, 하단 드로블
		final MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;

		public Lv0WaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;
			
			
			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == Lv0WaitState.class)
					{
						//mViewContext.addAnimatable(lv0Anim);
				
						Log.v("tag2", "lv0-run");
						
						/*
						if (Setting.second >= 10 && Setting.second <45)	{
							
							mViewContext.removeAnimatable(lv0Anim);							
							mViewContext.setState(new DisabledState(mViewContext));
							mViewContext.setState(new Lv1State(mViewContext));
							
							Log.v("tag3", "Lv0WaitState-setState"); 
							 
							
							
						}
						 */
						
						//mViewContext.setState(new OftenState(mViewContext, flowerSprite)); 
						Log.v("tag3", "mViewContext.setState(new OftenState");
						
						//lv0Anim.Draw2(Bitmap.createBitmap(mViewContext.cwidth, mViewContext.cheight, Bitmap.Config.ARGB_8888));
						//mViewContext.scheduleRedraw();
						
					}
				}
			}, 3000);
			
			
		}

		public void OnClick(CoinBlockView viewContext) {
			
			
			
			viewContext.removeAnimatable(lv0clAnim);
			
			lv0clAnim = new Lv0ClickAnim();			
			viewContext.addAnimatable(lv0clAnim);
			
			
			snd.seekTo(0);
			snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd.start();
				}
			});
			
			
			
			
			
		}

		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
		}

		public boolean NeedRedraw() { 
			return false;
		}

		
		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			
		
			
			
			/*
			
			//애니매이션 1 - 성공 (버섯올라오기)
			coinBlockView.removeAnimatable(lv0Anim);
			
			lv0Anim = new Lv0Animation();			
			coinBlockView.addAnimatable(lv0Anim);
			
			*/
			
			
			
			//애니매이션 2 - 성공
			
			coinBlockView.removeAnimatable(lv0ofAnim);
			lv0ofAnim = new Lv0OftenAnim();			
			coinBlockView.addAnimatable(lv0ofAnim);
			
			 
			
			
			 
			


			
		}
		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			coinBlockView.setState(new Lv1State(coinBlockView));
			
			
			Log.d("tag3","OnEvolve");
			
			
		}

	

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			

			coinBlockView.removeAnimatable(lv0Anim);	
			coinBlockView.removeAnimatable(lv0ofAnim);
			coinBlockView.removeAnimatable(lv0clAnim);
			
			//coinBlockView.setState(new DisabledState(coinBlockView));
			
			Log.d("tag3","OnInit");
			 
			
		}



	}

	private class Lv0Animation implements IAnimatable {
		
		//진동할때 올라오고, 상단에 남는 드로블
		
		private int flowerRaise = 4;


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
			
			Log.v("tag4", "flowerRaise "+Integer.toString(flowerRaise));
			
			
		}

		
	}
	
	private class Lv0OftenAnim implements IAnimatable {
		

		private int blockVib = 0;
		
		
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			
			
			// Draw the brick at bottom
			Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
			//진동할때의 하단드로블
			SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);
			

						if (blockVib < 7) { 
							blockVib++;
						}
						
			
			Log.v("tag4", "blockVib"+Integer.toString(blockVib));
			
			/*
			if (blockVib >= 7){
				context.setState(new Lv0WaitState(context));
				Log.v("tag4", "blockVib >= heightModifier.length)"+Integer.toString(blockVib));
			}
			
			*/
			
			
			
			
		}

		
	}
	
	

	private class Lv0ClickAnim implements IAnimatable {
		

		private int spriteVib = 0;
		
		
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			
			
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
			//진동할때의 하단드로블
			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.Center,
					-(int)(widthModifier[spriteVib] * context.getDensity()),-2);
			

						if (spriteVib < 7) { 
							spriteVib++;
						}
						
		
			/*
			if (blockVib >= 7){
				context.setState(new Lv0WaitState(context));
				Log.v("tag4", "blockVib >= heightModifier.length)"+Integer.toString(blockVib));
			}
			
			*/
			
			
			
			
		}

		
	}
	
	

	
	


}
