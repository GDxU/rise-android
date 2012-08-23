package com.matthewtole.androidrise.lib;

public class RiseGame {

	public static final int BLUE = 0;
	public static final int RED = 1;

	private static final int TURN_STATE_NOTHING = 1;

	private RiseTile[][] board;
	private int turn;
	private int turnState;
	private int moveCounter;
	private int[] availableWorkers;
	private int availableTiles;
	private int[] towerCounts;

	public RiseGame() {
		this.board = new RiseTile[60][60];
		for (int x = 0; x < 60; x += 1) {
			for (int y = 0; y < 60; y += 1) {
				this.board[x][y] = new RiseTile();
			}
		}

		this.availableWorkers = new int[2];
		this.towerCounts = new int[2];
	}

	public void setup() {
		this.turn = RiseGame.RED;
		this.availableTiles = 60;
		this.availableWorkers[RiseGame.RED] = 30;
		this.availableWorkers[RiseGame.BLUE] = 30;
		this.towerCounts[RiseGame.RED] = 0;
		this.towerCounts[RiseGame.BLUE] = 0;
		this.moveCounter = 1;
		this.turnState = TURN_STATE_NOTHING;

		this.board[0][1].setTile();
		this.board[1][0].setTile();
		this.board[1][1].setWorker(RiseGame.RED);
		this.board[1][2].setTile();
		this.board[2][1].setTile();
		this.board[3][1].setTile();
		this.board[4][1].setTile();
		this.board[5][1].setTile();
		this.board[6][1].setTile();
		this.board[7][1].setTile();
		this.board[8][1].setWorker(RiseGame.BLUE);
		this.board[9][1].setTile();

	}

	public boolean hasPiece(int x, int y) {
		if (!validLocation(x, y)) {
			return false;
		}
		return this.getTile(x, y).isPiece();
	}

	private boolean validLocation(int x, int y) {
		if (x < 0 || y < 0) {
			return false;
		}
		if (x >= 60 || y >= 60) {
			return false;
		}
		return true;
	}

	public int pieceColour(int x, int y) {
		if (!validLocation(x, y)) {
			return -1;
		}
		if (!hasPiece(x, y)) {
			return -1;
		}
		return this.getTile(x, y).pieceColour();
	}

	public int towerHeight(int x, int y) {
		if (!validLocation(x, y) || !hasPiece(x, y) || !hasTower(x, y)) {
			return 0;
		}
		return this.getTile(x, y).towerHeight();
	}

	private RiseTile getTile(int x, int y) {
		if (!validLocation(x, y)) {
			return null;
		}
		return this.board[x][y];
	}

	public int towerColour(int x, int y) {
		if (!validLocation(x, y) || !hasPiece(x, y) || !hasTower(x, y)) {
			return 0;
		}
		return this.pieceColour(x, y);
	}

	public boolean hasTower(int x, int y) {
		if (!validLocation(x, y) || !hasPiece(x, y)) {
			return false;
		}
		return this.getTile(x, y).isTower();
	}

	public boolean hasTile(int x, int y) {
		if (!validLocation(x, y)) {
			return false;
		}
		return !this.getTile(x, y).isBlank();
	}

	public static String colourName(int pieceColour) {
		return pieceColour == BLUE ? "blue" : "red";
	}

	public boolean doAction(int x, int y, int player) {
		if (this.turn != player) {
			return false;
		}

		if (!this.validLocation(x, y)) {
			return false;
		}

		if (this.turnState == RiseGame.TURN_STATE_NOTHING) {
			// PLACE TILE
			if (this.getTile(x, y).isBlank() && this.availableTiles > 0) {
				if (this.hasNeighbour(x, y)) {
					this.getTile(x, y).setTile();
					this.availableTiles -= 1;
					this.moveMade();
					return true;
				} else {
					return false;
				}
			}
			// ADD WORKER
			if (this.getTile(x, y).isTile() & this.availableWorkers[player] > 0) {
				if (this.hasNeighbourWorker(x, y, player)) {
					this.getTile(x, y).setWorker(player);
					this.availableWorkers[player] -= 1;
					this.moveMade();
					return true;
				} else {
					return false;
				}
			}
			// REMOVE TOWER
			if (this.getTile(x, y).isTower(player)) {
				if (this.getTile(x, y).demolishTower()) {
					this.towerCounts[player] -= 1;
					this.moveMade();
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	private void moveMade() {
		this.moveCounter -= 1;
		if (this.moveCounter <= 0) {
			this.turn = (this.turn == RiseGame.BLUE) ? RiseGame.RED
					: RiseGame.BLUE;
			this.moveCounter = 2;
		}
	}

	private boolean hasNeighbourWorker(int x, int y, int player) {
		if (this.getTile(x - 1, y) != null
				&& this.getTile(x - 1, y).isWorker(player)) {
			return true;
		}
		if (this.getTile(x + 1, y) != null
				&& this.getTile(x + 1, y).isWorker(player)) {
			return true;
		}
		if (this.getTile(x, y - 1) != null
				&& this.getTile(x, y - 1).isWorker(player)) {
			return true;
		}
		if (this.getTile(x, y + 1) != null
				&& this.getTile(x, y + 1).isWorker(player)) {
			return true;
		}
		if (this.getTile(x - 1, y - 1) != null
				&& this.getTile(x - 1, y - 1).isWorker(player)) {
			return true;
		}
		if (this.getTile(x - 1, y + 1) != null
				&& this.getTile(x - 1, y + 1).isWorker(player)) {
			return true;
		}
		return false;
	}

	private boolean hasNeighbour(int x, int y) {
		if (this.getTile(x - 1, y) != null
				&& this.getTile(x - 1, y).isNotBlank()) {
			return true;
		}
		if (this.getTile(x + 1, y) != null
				&& this.getTile(x + 1, y).isNotBlank()) {
			return true;
		}
		if (this.getTile(x, y - 1) != null
				&& this.getTile(x, y - 1).isNotBlank()) {
			return true;
		}
		if (this.getTile(x, y + 1) != null
				&& this.getTile(x, y + 1).isNotBlank()) {
			return true;
		}
		if (this.getTile(x - 1, y - 1) != null
				&& this.getTile(x - 1, y - 1).isNotBlank()) {
			return true;
		}
		if (this.getTile(x - 1, y + 1) != null
				&& this.getTile(x - 1, y + 1).isNotBlank()) {
			return true;
		}
		return false;
	}

	public int getCurrentPlayer() {
		return this.turn;
	}

}
