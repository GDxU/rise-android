package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;

import android.graphics.Canvas;

public class BitmapPiece extends Piece {
	
	private String bitmap = "";
	
	public BitmapPiece(SpriteManager sprites) {
		super(sprites);
	}

	public BitmapPiece(SpriteManager sprites, String bitmap) {
		super(sprites);
		this.setBitmap(bitmap);
	}

	protected void setBitmap(String bitmap) {
		this.bitmap = bitmap;		
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
