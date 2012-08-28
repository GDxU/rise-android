package com.matthewtole.androidrise.game.pieces;

import android.graphics.Canvas;

import com.matthewtole.androidrise.game.Common;
import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.lib.GridLocation;
import com.matthewtole.androidrise.lib.ScreenLocation;
import com.matthewtole.androidrise.lib.SimpleQueue;

public class Piece {

	@SuppressWarnings("unused")
	private static final String TAG = Piece.class.getSimpleName();

	protected SpriteManager sprites;
	protected ScreenLocation location;
	protected GridLocation gridLocation;

	protected boolean isMoving = false;
	private ScreenLocation target;
	private int MOVE_DURATION = 400;
	private SimpleQueue<ScreenLocation> movePoints;

	protected boolean hidden = false;

	public Piece(SpriteManager sprites) {
		this.sprites = sprites;
		this.movePoints = new SimpleQueue<ScreenLocation>();
	}

	public void setLocation(GridLocation loc) {
		this.gridLocation = loc;
		this.setLocation(loc, true);
	}

	public void setLocation(GridLocation loc, boolean instant) {		
		if (instant) {
			this.location = new ScreenLocation(loc);
		} else {
			this.target = new ScreenLocation(loc);
			this.calculateMovePoints(loc);
			this.isMoving = true;
		}
		this.gridLocation = loc;
	}

	private void calculateMovePoints(GridLocation loc) {
		float targetX = target.getScreenX();
		float targetY = target.getScreenY();
		float currentX = this.getLocation().getScreenX();
		float currentY = this.getLocation().getScreenY();

		float frameCount = this.MOVE_DURATION / Common.FPS - 2;
		for (float f = 1; f < frameCount; f += 1) {
			float thisX = currentX + (((targetX - currentX) / frameCount) * f);
			float thisY = currentY + (((targetY - currentY) / frameCount) * f);
			ScreenLocation s = new ScreenLocation(thisX, thisY);
			this.movePoints.put(s);
		}

	}

	public GridLocation getLocation() {
		return this.gridLocation;
	}

	public void draw(Canvas canvas) {
	}

	public void update() {
		if (this.isMoving) {
			this.move();
		}
	}

	protected void move() {
		if (this.movePoints.isEmpty()) {
			this.isMoving = false;
			this.setLocation(this.target.getScreenX(), this.target.getScreenY());
			return;
		}
		ScreenLocation loc = this.movePoints.get();
		this.setLocation(loc.getScreenX(), loc.getScreenY());
	}

	protected void setLocation(float x, float y) {
		this.location = new ScreenLocation(x, y);
	}

	public void hide() {
		this.hidden = true;
	}

	public void show() {
		this.hidden = false;
	}
}
