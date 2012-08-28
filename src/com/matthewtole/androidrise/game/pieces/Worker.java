package com.matthewtole.androidrise.game.pieces;

import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.game.enums.GamePlayer;
import com.matthewtole.androidrise.lib.AnimationCallback;

public class Worker extends CompositePiece implements AnimationCallback {

	private GamePlayer player;

	public Worker(SpriteManager sprites, GamePlayer player) {
		super(sprites);
		this.player = player;

		this.addPiece(new AnimatedPiece(sprites, "highlight2", 4, 2));
		this.addPiece(new WorkerDisc(sprites, player));		
		this.hidePiece(0);
		((AnimatedPiece)this.getPiece(0)).setCallback(this);
		((AnimatedPiece)this.getPiece(0)).pause();		
	}

	public void setSelected(boolean selected) {
		if (selected) {
			this.showPiece(0);
			((AnimatedPiece)this.getPiece(0)).play();
		} else {
			this.hidePiece(0);
			((AnimatedPiece)this.getPiece(0)).reset();
		}
	}

	public GamePlayer getPlayer() {
		return this.player;
	}

	public void animationFinished(String folder) {
		((AnimatedPiece)this.getPiece(0)).pause();		
	}

}
