package com.exam;

import java.util.*;
import java.util.regex.*;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.media.*;
import android.util.*;

public class MediaAssets {
        private Hashtable<Integer, MediaPlayer> soundPlayers = new Hashtable<Integer, MediaPlayer>();
        private Hashtable<Integer, Sprite> sprites = new Hashtable<Integer, Sprite>();
        private Resources resources;

        private MediaAssets() {
        }

        private static MediaAssets singleton;

        public static MediaAssets getInstance() {
                if (singleton == null) {
                        singleton = new MediaAssets();
                        singleton.prepareAssets();
                }
                return singleton;
        }

        private void prepareAssets() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = true;
                Context context = CoinBlockWidgetApp.getApplication();
                resources = context.getResources();
        }

        // Lazy loading
        public Sprite getSprite(int resId) {
                Sprite output = sprites.get(resId);
                Log.v("tag10", "output "+output);
                if (output == null) {
                        try {
                                long start = System.currentTimeMillis();
                                output = CreateSprite(resId);
                                sprites.put(resId, output);
                                Log.d("coinBlock", System.currentTimeMillis() - start + " lazy load " + resId);
                        } catch (Exception e) {
                                output = Sprite.Null;
                        }
                }
                return output;
        }

        public MediaPlayer getSoundPlayer(int resId) {
                MediaPlayer output = soundPlayers.get(resId);
                if (output == null) {
                        try {
                                output = CreateSoundPlayer(resId);
                                soundPlayers.put(resId, output);
                        } catch (Exception e) {
                                output = null;
                        }
                }
                return output;
        }

        private MediaPlayer CreateSoundPlayer(int resId) {
                return MediaPlayer.create(CoinBlockWidgetApp.getApplication(), resId);
        }

        private Sprite CreateSprite(int resId) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = true;
                Bitmap bitmap = BitmapFactory.decodeResource(resources, resId, options);
                int frame = parseNumberOfFrame(resId);
                Log.v("tag11", "parseNumberOfFrame "+frame);
                int bwidth = bitmap.getWidth() / frame;
                Log.v("tag11", "bwidth "+bwidth);
                int bheight = bitmap.getHeight();
                Log.v("tag11", "bheight "+bheight);
                int[][] pixels = new int[frame][bwidth * bheight];
                for (int i = 0; i < frame; i++) {
                        bitmap.getPixels(pixels[i], 0, bwidth, bwidth * i, 0, bwidth, bheight);
                }
                Sprite newSprite = new Sprite(pixels, bwidth, bheight, frame);
                Log.v("tag11", "newSprite "+newSprite);
                return newSprite;
        }

        private int parseNumberOfFrame(int resId) {
                String spriteName = resources.getResourceEntryName(resId);
                Log.v("tag12", "spriteName "+spriteName);
                int numOfFrame = 1; 
                try {  
                        Pattern p = Pattern.compile("\\d+$", Pattern.CASE_INSENSITIVE);
                        Log.v("tag12", "p "+p);
                        Matcher m = p.matcher(spriteName);
                        Log.v("tag12", "m "+m);
                        Log.v("tag12", "m.find()1 "+m.find());
                        if (m.find()) {
                        		Log.v("tag12", "m.find()2 "+m.find());
                                String strFrame = m.group();
                                Log.v("tag12", "strFrame "+strFrame);
                                numOfFrame = Integer.parseInt(strFrame);
                        }
                } catch (Exception e) {
                        numOfFrame = 1;
                }
                return Math.max(1, numOfFrame);
                
        }
}