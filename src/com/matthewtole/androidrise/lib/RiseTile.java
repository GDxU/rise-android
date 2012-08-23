package com.matthewtole.androidrise.lib;

public class RiseTile {

	private static final int STATE_BLANK = 1;
	private static final int STATE_TILE = 2;
	private static final int STATE_PIECE = 3;

	private static final int PIECE_WORKER = 1;
	private static final int PIECE_TOWER = 2;

	private int state = RiseTile.STATE_BLANK;
	private int pieceType = 0;
	private int pieceColour = -1;
	private int towerHeight = 0;

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
		return ! this.isBlank();
	}
	
	public boolean isTile() {
		return this.state == RiseTile.STATE_TILE;
	}

	public void setTile() {
		this.state = RiseTile.STATE_TILE;
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
		if (! this.isTower()) {
			return false;
		}
		this.towerHeight -= 1;
		if (this.towerHeight <= 0) {
			this.state = STATE_TILE;
		}
		return true;
	}
	
	public boolean buildTower() {
		if (! this.isTower()) {
			return false;
		}
		if (this.towerHeight < 3) {
			this.towerHeight += 1;
			return true;
		}
		return false;
	}
}
