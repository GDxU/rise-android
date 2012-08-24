package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;

public class AnimatedPiece extends BitmapPiece {

	@SuppressWarnings("unused")
	private static final String TAG = AnimatedPiece.class.getSimpleName();
	private String folder;
	private int count;
	private int frame;

	public AnimatedPiece(SpriteManager sprites, String folder, int count) {
		super(sprites);
		this.folder = folder;
		this.count = count;
		this.frame = 0;
		this.setBitmap(this.makeBitmap());
	}

	private String makeBitmap() {	
		return folder + "/" + String.valueOf(this.frame);
	}

	@Override
	public void update() {
		super.update();

		if (this.hidden) { return; }
		
		this.frame += 1;
		if (this.frame >= this.count) {
			this.frame = 0;
		}
		this.setBitmap(this.makeBitmap());
	}
	
	
}
