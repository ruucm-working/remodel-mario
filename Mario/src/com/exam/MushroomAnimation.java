package com.exam;

import android.*;
import android.graphics.*;

public class MushroomAnimation implements IAnimatable {
        private Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
        private int upstep, movestep;
        private float density;
        
        public MushroomAnimation(float Density)
        {
                upstep = 10;
                movestep = 10;
                density = Density;
        }

        public boolean AnimationFinished()
        {
                return movestep<=0;
        }
        
        public void Draw(Bitmap canvas)
        {
        		// 버섯 x,y좌표(right, top)
                int top = -(int)((16-upstep) * 2 * density);
                int right = (int)((10-movestep) * 2  * density);
                SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0);
                
                boolean direction = true;
                // Two stage
                if (upstep > 0){		// 버섯 올라와
                // Draw sprite
                        upstep--;
                }
                else{		// 다 올라왔으면 오른쪽으로
                	if(movestep == 5)
                        direction = true;
                	else if(movestep == 15)
                		direction = false;
                	
                	if(direction == true)
                		movestep ++;
                	else
                		movestep --;
                }
        }
}