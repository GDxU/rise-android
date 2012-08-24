package com.matthewtole.androidrise.game;

import com.matthewtole.androidrise.game.enums.GamePlayer;
import com.matthewtole.androidrise.lib.Utils;

public class Tower extends Piece {
	
	private int level = 1;
	private GamePlayer player;

	public Tower(SpriteManager sprites, GamePlayer player) {
		super(sprites);
		this.bitmap = Utils.playerString(player) + "_tower" + String.valueOf(this.level);
		this.player = player;
	}

	public void removeLevel() {
		level -= 1;
		this.bitmap = Utils.playerString(this.player) + "_tower" + String.valueOf(this.level);
	}
}
