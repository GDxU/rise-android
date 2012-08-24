package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.game.enums.GamePlayer;
import com.matthewtole.androidrise.lib.Utils;

public class Tower extends BitmapPiece {
	
	private int level = 1;
	private GamePlayer player;

	public Tower(SpriteManager sprites, GamePlayer player) {
		super(sprites);
		this.setBitmap(Utils.playerString(player) + "_tower" + String.valueOf(this.level));
		this.player = player;
	}

	public void removeLevel() {
		level -= 1;
		this.setBitmap(Utils.playerString(player) + "_tower" + String.valueOf(this.level));
	}
}
