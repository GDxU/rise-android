package com.matthewtole.androidrise.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.matthewtole.androidrise.game.enums.GamePlayer;
import com.matthewtole.androidrise.lib.Utils;

public class Worker extends Piece {

	private boolean selected;
	private Paint highlighter;
	private GamePlayer player;

	public Worker(SpriteManager sprites, GamePlayer player) {
		super(sprites);
		this.player = player;
		
		this.bitmap = Utils.playerString(this.player) + "_worker";
		this.highlighter = new Paint();
		this.highlighter.setColor(Color.LTGRAY);
		this.highlighter.setStyle(Style.STROKE);
		this.highlighter.setStrokeWidth(4);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (this.selected) {
			canvas.drawCircle(this.location.getScreenX()
					+ Common.TILE_WIDTH_HALF, this.location.getScreenY()
					+ Common.TILE_HEIGHT_HALF, 30, highlighter);

		}
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public GamePlayer getPlayer() {
		return this.player;
	}

}
