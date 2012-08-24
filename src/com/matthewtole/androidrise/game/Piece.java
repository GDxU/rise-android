package com.matthewtole.androidrise.game;

import android.graphics.Canvas;

import com.matthewtole.androidrise.lib.GridLocation;
import com.matthewtole.androidrise.lib.ScreenLocation;

public class Piece {
	
	protected enum PieceState { DEFAULT, MOVING }

	@SuppressWarnings("unused")
	private static final String TAG = Piece.class.getSimpleName();

	protected SpriteManager sprites;
	protected int x = 0;
	protected int y = 0;
	protected String bitmap = "";
	private PieceState state;
	private ScreenLocation target;
	private int MOVE_SPEED = 3;

	public Piece(SpriteManager sprites) {
		this.sprites = sprites;
	}
	
	public void setLocation(GridLocation loc) {
		this.setLocation(loc, true);
	}
	
	public void setLocation(GridLocation loc, boolean instant) {
		if (instant) {
			this.x = loc.getScreenX();
			this.y = loc.getScreenY();
		}
		else {
			this.target = new ScreenLocation(loc);
			this.state = PieceState.MOVING;
		}
	}
	
	public void setLocation(ScreenLocation loc, boolean instant) {
		if (instant) {
			this.x = loc.getScreenX();
			this.y = loc.getScreenY();
		}
		else {
			this.target = loc;
			this.state = PieceState.MOVING;
		}
	}

	public void draw(Canvas canvas) {
		if (bitmap.length() > 0) {
			canvas.drawBitmap(this.sprites.getBitmap(this.bitmap), this.x,
					this.y, null);
		}
	}
	
	public void update() {
		if (this.state == PieceState.MOVING) {
			this.move();			
		}
	}
	
	private void move() {		
		if (this.x < this.target.getScreenX()) {
			if (Math.abs(this.x - this.target.getScreenX()) < this.MOVE_SPEED ) {
				this.x = this.target.getScreenX();
			}
			else {
				this.x += this.MOVE_SPEED;
			}
		}
		else if (this.x > this.target.getScreenX()) {
			if (Math.abs(this.x - this.target.getScreenX()) < this.MOVE_SPEED ) {
				this.x = this.target.getScreenX();
			}
			else {
				this.x -= this.MOVE_SPEED;
			}
		}
		
		if (this.y < this.target.getScreenY()) {
			if (Math.abs(this.y - this.target.getScreenY()) < this.MOVE_SPEED ) {
				this.y = this.target.getScreenY();
			}
			else {
				this.y += this.MOVE_SPEED;
			}
		}
		else if (this.y > this.target.getScreenY()) {
			if (Math.abs(this.y - this.target.getScreenY()) < this.MOVE_SPEED ) {
				this.y = this.target.getScreenY();
			}
			else {
				this.y -= this.MOVE_SPEED;
			}
		}
	}
}
