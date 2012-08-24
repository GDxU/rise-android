package com.matthewtole.androidrise.lib;

import com.matthewtole.androidrise.game.Common;

public class ScreenLocation implements Location {

	private int x = 0;
	private int y = 0;

	public ScreenLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public ScreenLocation(GridLocation loc) {
		this.x = loc.getScreenX();
		this.y = loc.getScreenY();
	}

	public int getScreenX() {
		return this.x;
	}

	public int getScreenY() {
		return this.y;
	}

	public int getGridX() {
		return toGridLocation().getGridX();
	}

	public int getGridY() {
		return toGridLocation().getGridY();
	}

	protected GridLocation toGridLocation() {

		int x = this.getScreenX();
		int y = this.getScreenY();

		if (y < 0) {
			y -= Common.TILE_HEIGHT_HALF;
		} else {
			y += Common.TILE_HEIGHT_HALF;
		}

		if (x < 0) {
			x -= Common.TILE_WIDTH_HALF;
		} else if (x > 0) {
			x += Common.TILE_WIDTH_HALF;
		}

		int gY = (int) (y / Common.TILE_HEIGHT_THREEQUARTERS);
		if (Math.abs(gY % 2) == 1) {
			x -= Common.TILE_WIDTH_HALF;
		}
		int gX = (int) (x / Common.TILE_WIDTH);
		
		return new GridLocation(gX, gY);
	}
}
