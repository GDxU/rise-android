package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.game.enums.GamePlayer;
import com.matthewtole.androidrise.lib.Utils;

public class WorkerDisc extends BitmapPiece {

	@SuppressWarnings("unused")
	private static final String TAG = WorkerDisc.class.getSimpleName();

	public WorkerDisc(SpriteManager sprites, GamePlayer player) {
		super(sprites);
		this.setBitmap(Utils.playerString(player) + "_worker");
	}	
}
