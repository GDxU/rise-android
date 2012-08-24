package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;

public class StaticPiece extends BitmapPiece {

	public StaticPiece(SpriteManager sprites, String bitmap) {
		super(sprites);
		this.bitmap = bitmap;
	}

}
