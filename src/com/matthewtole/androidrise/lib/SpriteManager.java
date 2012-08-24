package com.matthewtole.androidrise.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SpriteManager {
	
	private Context context;
	private HashMap<String, Bitmap> bitmaps;
	
	public SpriteManager(Context context) {
		this.context = context;
		this.bitmaps = new HashMap<String, Bitmap>();
	}
	
	public Bitmap getBitmap(String name) {
		if (this.bitmaps.containsKey(name)) {
			return this.bitmaps.get(name);
		}
		
		Bitmap tmp = this.getBitmapFromAsset("gfx/" + name + ".png");
		if (tmp == null) {
			return null;
		}
		this.bitmaps.put(name, tmp);
		return tmp;
	}
	
	private Bitmap getBitmapFromAsset(String name)
    {
        AssetManager assetManager = this.context.getAssets();

        InputStream istr;
		try {
			istr = assetManager.open(name);
			Bitmap bitmap = BitmapFactory.decodeStream(istr);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return null;
    }
}
