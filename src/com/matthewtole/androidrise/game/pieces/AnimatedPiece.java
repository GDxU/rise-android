package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.lib.AnimationCallback;

public class AnimatedPiece extends BitmapPiece {

	@SuppressWarnings("unused")
	private static final String TAG = AnimatedPiece.class.getSimpleName();
	private String folder;
	private int count;
	private int frame;
	private AnimationCallback callback;
	private boolean paused;
	private int speed;
	private int speedCounter;

	public AnimatedPiece(SpriteManager sprites, String folder, int count, int speed) {
		super(sprites);
		this.folder = folder;
		this.count = count;
		this.frame = 0;
		this.speed = speed;
		this.speedCounter = this.speed;
		this.callback = null;
		this.paused = false;
		this.setBitmap(this.makeBitmap());
	}
	
	public void setCallback(AnimationCallback callback) {
		this.callback = callback;
	}

	private String makeBitmap() {	
		return folder + "/" + String.valueOf(this.frame);
	}
	
	public void pause() {
		this.paused = true;
	}
	
	public void play() {
		this.paused = false; 
	}

	@Override
	public void update() {
		super.update();

		if (this.hidden) { return; }
		if (this.paused) { return; }
		
		this.setBitmap(this.makeBitmap());
		
		this.speedCounter -= 1;
		if (this.speedCounter <= 0) {		
			this.frame += 1;
			if (this.frame >= this.count) {
				if (this.callback != null) {
					this.callback.animationFinished(this.folder);
				}
				this.frame = 0;			
			}
			this.speedCounter = this.speed;
		}
	}

	public void reset() {
		this.frame = 0;		
	}	
}
