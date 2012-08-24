package com.matthewtole.androidrise.game;

import com.matthewtole.androidrise.game.enums.GamePlayer;
import com.matthewtole.androidrise.lib.Utils;


public class Worker extends Piece {

	public Worker(SpriteManager sprites, GamePlayer player) {
		super(sprites);
		this.bitmap = Utils.playerString(player) + "_worker";
	}
}
