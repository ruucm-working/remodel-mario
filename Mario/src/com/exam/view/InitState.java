package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;
import android.widget.*;

import com.exam.*;

public class InitState implements ICoinBlockViewState {
	
	Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
	//진동할때 올라오고, 상단에 남는 드로블
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 6, -6, 4, -4, 2, -2, 0, 0 };	// here
	initAnimation initAnim;
    InitOftenAnim initofAnim;
    InitClickAnim initclAnim;
	boolean fuck = false;
	CoinBlockView context; 
	 
	
	
	
	

	public InitState(CoinBlockView viewContext) {
		context = viewContext;
		
		setContentView(R.drawable.background,"상자를 열어라");
		
		initAnim = new initAnimation();
		initofAnim = new InitOftenAnim();
		
		viewContext.addAnimatable(initAnim);
		//viewContext.addAnimatable(initofAnim);
		
		
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
			viewContext.setState(new InitWaitState(viewContext));
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
		
		coinBlockView.removeAnimatable(initAnim);
		
		
	}
	
	
	

	class InitWaitState implements ICoinBlockViewState {
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
		//진동후의, 하단 드로블
		final MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;

		public InitWaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;
			
			
			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == InitWaitState.class)
					{
						//mViewContext.addAnimatable(initAnim);
				
						Log.v("tag2", "lv0-run");
						
						/*
						if (Setting.second >= 10 && Setting.second <45)	{
							
							mViewContext.removeAnimatable(initAnim);							
							mViewContext.setState(new DisabledState(mViewContext));
							mViewContext.setState(new Lv1State(mViewContext));
							
							Log.v("tag3", "Lv0WaitState-setState"); 
							 
							
							
						}
						 */
						
						//mViewContext.setState(new OftenState(mViewContext, flowerSprite)); 
						Log.v("tag3", "mViewContext.setState(new OftenState");
						
						//initAnim.Draw2(Bitmap.createBitmap(mViewContext.cwidth, mViewContext.cheight, Bitmap.Config.ARGB_8888));
						//mViewContext.scheduleRedraw();
						
					}
				}
			}, 3000);
			
			
		}

		public void OnClick(CoinBlockView viewContext) {
			
			
			
			viewContext.removeAnimatable(initclAnim);
			
			initclAnim = new InitClickAnim();			
			viewContext.addAnimatable(initclAnim);
			
			
			snd.seekTo(0);
			snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd.start();
				}
			});
			
			
			
			 
			Setting.CliCountinit++;			
			
			Setting.mPref.Ready();			
			Setting.mPref.WriteInt("clicountinit", Setting.CliCountinit);			
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
			coinBlockView.removeAnimatable(initAnim);
			
			initAnim = new initAnimation();			
			coinBlockView.addAnimatable(initAnim);
			
			*/
			
			
			
			//애니매이션 2 - 성공
			
			coinBlockView.removeAnimatable(initofAnim);
			initofAnim = new InitOftenAnim();			
			coinBlockView.addAnimatable(initofAnim);
			
			 
			
			
			 
			
  

			
		}
		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			coinBlockView.setState(new Lv0_1State(coinBlockView));
			 
			
			
			coinBlockIntroActivity.taskTimer1.setTextView1(R.id.time0);
			//coinBlockIntroActivity.taskTimer1.setTime(0);
			coinBlockIntroActivity.taskTimer1.execute("");
			
			
			//coinBlockIntroActivity.setContentView(R.layout.main);
			
			
			/*
			 
			
			//Setting.InitState = false;	
			Setting.Lv0State = true;	
			
			Setting.mPref.Ready();			
			//Setting.mPref.WriteBoolean("initstate", Setting.InitState);	
			Setting.mPref.WriteBoolean("lv0state", Setting.Lv0State);
			Setting.mPref.CommitWrite();
			*/
			
			
			setContentView(R.drawable.background0, "껒여껒여껒여껒여");
			
			
		
			
			Log.d("tag3","OnEvolve");
			
			 
		}

	

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			

			coinBlockView.removeAnimatable(initAnim);	
			coinBlockView.removeAnimatable(initofAnim);
			coinBlockView.removeAnimatable(initclAnim);
			
			//coinBlockView.setState(new DisabledState(coinBlockView));
			
			Log.d("tag3","OnInit");
			 
			
		}



	}

	private class initAnimation implements IAnimatable {
		
		//진동할때 올라오고, 상단에 남는 드로블
		
		private int flowerRaise = 4;


		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			
			
		}

		
	}
	
	private class InitOftenAnim implements IAnimatable {
		

		private int blockVib = 0;
		
		
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			
			
			// Draw the brick at bottom
			
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
	
	

	private class InitClickAnim implements IAnimatable {
		

		private int blockVib = 0;
		
		
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			
			
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
			//진동할때의 하단드로블
			
			SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,
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
	
	
	public void setContentView(int drawbleid, String txt) {
		
		
		coinBlockIntroActivity instance = coinBlockIntroActivity.getInstance();	
		
		//instance.setContentView(R.layout.main);		
		
		
		/*
		
		//set time
		TextView time = (TextView)instance.findViewById(R.id.time0);
		time.setText(Long.toString(instance.second));
		
		*/
		
		//set newstate's background img
		LinearLayout a = (LinearLayout)instance.findViewById(R.id.mainlinear);			
		a.setBackgroundResource(drawbleid);
		 
		//set newstate's text
		TextView statetxt = (TextView)instance.findViewById(R.id.welcome);		
		statetxt.setText(txt);
		
    }

	
	


}
