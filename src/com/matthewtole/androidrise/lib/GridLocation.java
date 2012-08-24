package com.matthewtole.androidrise.lib;

import com.matthewtole.androidrise.game.Common;

public class GridLocation implements Location {

	@SuppressWarnings("unused")
	private static final String TAG = GridLocation.class.getSimpleName();

	private int x = 0;
	private int y = 0;

	public GridLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return super.equals(o);
		}
		GridLocation other = (GridLocation) o;
		return (this.x == other.x && this.y == other.y);
	}

	public int getScreenX() {
		return toScreen().getScreenX();
	}

	public ScreenLocation toScreen() {
		int x = this.getGridX();
		int y = this.getGridY();

		int wX = Common.TILE_WIDTH * x;
		int wY = Common.TILE_HEIGHT_THREEQUARTERS * y;
		if (Math.abs(y % 2) == 1) {
			wX += Common.TILE_WIDTH_HALF;
		}

		return new ScreenLocation(wX, wY);
	}

	public int getScreenY() {
		return toScreen().getScreenY();
	}

	public int getGridX() {
		return this.x;
	}

	public int getGridY() {
		return this.y;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Grid Location: (" + String.valueOf(x) + ", "
				+ String.valueOf(y) + ")";
	}

}
