package com.matthewtole.androidrise.game.pieces;

import android.util.Log;

import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.game.enums.GamePlayer;
import com.matthewtole.androidrise.lib.Utils;

public class WorkerDisc extends BitmapPiece {

	private static final String TAG = WorkerDisc.class.getSimpleName();

	public WorkerDisc(SpriteManager sprites, GamePlayer player) {
		super(sprites);

		this.bitmap = Utils.playerString(player) + "_worker";
		Log.d(TAG, this.bitmap);
	}	
}
