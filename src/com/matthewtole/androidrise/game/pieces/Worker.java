package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.game.enums.GamePlayer;

public class Worker extends CompositePiece {

	private GamePlayer player;

	public Worker(SpriteManager sprites, GamePlayer player) {
		super(sprites);
		this.player = player;

		this.addPiece(new WorkerDisc(sprites, player));
		this.addPiece(new AnimatedPiece(sprites, "dots", 6));
		this.hidePiece(1);

	}

	public void setSelected(boolean selected) {
		if (selected) {
			this.showPiece(1);
		} else {
			this.hidePiece(1);
		}
	}
	
	public GamePlayer getPlayer() {
		return this.player;
	}

}
