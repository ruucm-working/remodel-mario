package com.exam.view;

import android.graphics.Bitmap;

interface ICoinBlockViewState {
	public void Draw(CoinBlockView viewContext, Bitmap canvas);

	public void OnClick(CoinBlockView viewContext);
	
	public void OnDoubleClick(CoinBlockView viewContext);
	
	public void OnShake(CoinBlockView viewContext);

	public boolean NeedRedraw();

	public void OnEvolve(CoinBlockView viewContext);

	public void OnOften(CoinBlockView coinBlockView);

	public void OnInit(CoinBlockView coinBlockView);

}
