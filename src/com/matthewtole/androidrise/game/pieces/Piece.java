package com.matthewtole.androidrise.game.pieces;

import android.graphics.Canvas;

import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.lib.GridLocation;
import com.matthewtole.androidrise.lib.ScreenLocation;

public class Piece {

	protected enum PieceState {
		DEFAULT, MOVING, ANIMATING
	}

	@SuppressWarnings("unused")
	private static final String TAG = Piece.class.getSimpleName();

	protected SpriteManager sprites;
	protected ScreenLocation location;
	protected GridLocation gridLocation;

	protected boolean isMoving = false;
	private ScreenLocation target;
	private int MOVE_SPEED = 5;

	protected boolean hidden = false;

	public Piece(SpriteManager sprites) {
		this.sprites = sprites;
	}

	public void setLocation(GridLocation loc) {
		this.gridLocation = loc;
		this.setLocation(loc, true);
	}

	public void setLocation(GridLocation loc, boolean instant) {
		this.gridLocation = loc;
		if (instant) {
			this.location = new ScreenLocation(loc);
		} else {
			this.target = new ScreenLocation(loc);
			this.isMoving = true;
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

		float x = this.location.getScreenX();
		float y = this.location.getScreenY();

		if (x < this.target.getScreenX()) {
			if (Math.abs(x - this.target.getScreenX()) < this.MOVE_SPEED) {
				x = this.target.getScreenX();
			} else {
				x += this.MOVE_SPEED;
			}
		} else if (x > this.target.getScreenX()) {
			if (Math.abs(x - this.target.getScreenX()) < this.MOVE_SPEED) {
				x = this.target.getScreenX();
			} else {
				x -= this.MOVE_SPEED;
			}
		}

		if (y < this.target.getScreenY()) {
			if (Math.abs(y - this.target.getScreenY()) < this.MOVE_SPEED) {
				y = this.target.getScreenY();
			} else {
				y += this.MOVE_SPEED;
			}
		} else if (y > this.target.getScreenY()) {
			if (Math.abs(y - this.target.getScreenY()) < this.MOVE_SPEED) {
				y = this.target.getScreenY();
			} else {
				y -= this.MOVE_SPEED;
			}
		}

		if (x == this.target.getScreenX() && y == this.target.getScreenY()) {
			this.isMoving = false;
		}

		this.setLocation(x, y);
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
