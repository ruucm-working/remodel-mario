package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;
import android.widget.*;

import com.exam.*;

public class Lv0_1State implements ICoinBlockViewState {
	
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.egg);
	//진동후의, 하단 드로블
	//Sprite flowerSprite = MediaAssets.getInstance().getSprite(R.drawable.reset_large);
	//진동할때 올라오고, 상단에 남는 드로블
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 3, -3, 2, -2, 1, -1, 0, -0 };		// here
	//Lv0Animation lv0Anim;  
    Lv0OftenAnim lv0ofAnim; 
    Lv0ClickAnim lv0clAnim;    
	boolean fuck = false;   
	CoinBlockView context;

	public Lv0_1State(CoinBlockView viewContext) {
		context = viewContext;
		//lv0Anim = new Lv0Animation();
		lv0clAnim = new Lv0ClickAnim();
		
		//viewContext.addAnimatable(lv0Anim);
		viewContext.addAnimatable(lv0clAnim);
		
		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start(); 
			}
		});
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
		//진동할때의 하단드로블
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,0,
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
		//coinBlockView.removeAnimatable(lv0Anim);
	}

	class Lv0WaitState implements ICoinBlockViewState {
		
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
			
			/*
			
			viewContext.removeAnimatable(lv0ofAnim);
			lv0ofAnim = new Lv0OftenAnim();			
			viewContext.addAnimatable(lv0ofAnim);
			
			*/
			
			snd.seekTo(0);
			snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd.start();
				}
			});
			 
			Setting.CliCount0++;			
			
			Setting.mPref.Ready();			
			Setting.mPref.WriteInt("clicount0", Setting.CliCount0);			
			Setting.mPref.CommitWrite();
		
			Log.v("tag5", "Setting.mPref.WriteIntn"); 
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
			coinBlockView.setState(new Lv0_2State(coinBlockView));
			coinBlockIntroActivity.taskTimer1.isCanceled = true;
			
			Log.d("tag3","OnEvolve");
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			

			//coinBlockView.removeAnimatable(lv0Anim);	
			coinBlockView.removeAnimatable(lv0ofAnim);
			coinBlockView.removeAnimatable(lv0clAnim);
			
			//coinBlockView.setState(new DisabledState(coinBlockView));
			
			Log.d("tag3","OnInit");
		}

		@Override
		public void onLongClick(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class Lv0OftenAnim implements IAnimatable {
		private int blockVib = 0;
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
			//진동할때의 하단드로블
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
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
			 
			//Sprite sp2 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
						
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[spriteVib] * context.getDensity()), 0);
			
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

	@Override
	public void onLongClick(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub
		
	}
}