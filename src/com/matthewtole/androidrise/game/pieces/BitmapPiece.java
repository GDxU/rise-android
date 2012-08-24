package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;

import android.graphics.Canvas;

public class BitmapPiece extends Piece {
	
	protected String bitmap = "";

	public BitmapPiece(SpriteManager sprites) {
		super(sprites);
	}

	@Override
	public void draw(Canvas canvas) {
		if (this.hidden) {
			return;
		}
		
		super.draw(canvas);		
		if (bitmap.length() > 0) {
			canvas.drawBitmap(this.sprites.getBitmap(this.bitmap),
					this.location.getScreenX(), this.location.getScreenY(),
					null);
		}
	}
	
	

}
