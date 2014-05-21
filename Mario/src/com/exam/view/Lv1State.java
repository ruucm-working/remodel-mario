package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;

import com.exam.*;

class Lv1State implements ICoinBlockViewState {
	public static final int DISABLE_PERIOD = 5000;

	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.samsung_sample);
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_bump);
	CoinBlockView mViewContext;
	
	public Lv1State(CoinBlockView viewContext) {
		this.mViewContext = viewContext;
		
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
	}

	public void OnClick(CoinBlockView viewContext) { 
		if (snd.isPlaying()) return;
		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start();
			}
		});
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

