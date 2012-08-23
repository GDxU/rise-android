package com.matthewtole.androidrise.lib;

public class RiseTile implements Cloneable {

	private static final int STATE_BLANK = 1;
	private static final int STATE_TILE = 2;
	private static final int STATE_PIECE = 3;

	private static final int PIECE_WORKER = 1;
	private static final int PIECE_TOWER = 2;

	private int state = RiseTile.STATE_BLANK;
	private int pieceType = 0;
	private int pieceColour = -1;
	private int towerHeight = 0;
	private boolean selected = false;
	private int x;
	private int y;

	public RiseTile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public RiseTile clone() {
		try {
			RiseTile cloned = (RiseTile) super.clone();
			return cloned;
		} catch (CloneNotSupportedException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return super.equals(o);
		}
		RiseTile otherTile = (RiseTile) o;
		return (otherTile.getX() == this.getX())
				&& (otherTile.getY() == this.getY());
	}

	public boolean isPiece() {
		return this.state == RiseTile.STATE_PIECE;
	}

	public int pieceColour() {
		return this.pieceColour;
	}

	public int towerHeight() {
		return this.towerHeight;
	}

	public boolean isTower() {
		return this.isPiece() && this.pieceType == RiseTile.PIECE_TOWER;
	}

	public boolean isWorker() {
		return this.isPiece() && this.pieceType == RiseTile.PIECE_WORKER;
	}

	public boolean isWorker(int colour) {
		return this.isWorker() && this.pieceColour() == colour;
	}

	public boolean isBlank() {
		return this.state == RiseTile.STATE_BLANK;
	}

	public boolean isNotBlank() {
		return !this.isBlank();
	}

	public boolean isTile() {
		return this.state == RiseTile.STATE_TILE;
	}

	public void setTile() {
		this.state = RiseTile.STATE_TILE;
		this.unselect();
	}

	public void setWorker(int player) {
		this.state = RiseTile.STATE_PIECE;
		this.pieceType = RiseTile.PIECE_WORKER;
		this.pieceColour = player;
	}

	public boolean isTower(int colour) {
		return this.isTower() && this.pieceColour() == colour;
	}

	public boolean demolishTower() {
		if (!this.isTower()) {
			return false;
		}
		this.towerHeight -= 1;
		if (this.towerHeight <= 0) {
			this.state = STATE_TILE;
		}
		return true;
	}

	public boolean buildTower() {
		if (!this.isTower()) {
			return false;
		}
		if (this.towerHeight < 3) {
			this.towerHeight += 1;
			return true;
		}
		return false;
	}

	public void select() {
		this.selected = true;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void clear() {
		this.selected = false;
		this.state = RiseTile.STATE_BLANK;
	}

	public void unselect() {
		this.selected = false;
	}

	public void setTower(int colour, int height) {
		this.state = RiseTile.STATE_PIECE;
		this.pieceType = RiseTile.PIECE_TOWER;
		this.towerHeight = height;
		this.pieceColour = colour;

	}
}
