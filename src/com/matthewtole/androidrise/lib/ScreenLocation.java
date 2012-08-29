package com.matthewtole.androidrise.lib;

import com.matthewtole.androidrise.game.Common;

public class ScreenLocation implements Location {

	private float x = 0;
	private float y = 0;

	public ScreenLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return super.equals(o);
		}
		ScreenLocation other = (ScreenLocation)o;
		return (this.x == other.x && this.y == other.y);
	}

	public ScreenLocation(Location loc) {
		this.x = loc.getScreenX();
		this.y = loc.getScreenY();
	}

	public float getScreenX() {
		return this.x;
	}

	public float getScreenY() {
		return this.y;
	}

	public int getGridX() {
		return toGridLocation().getGridX();
	}

	public int getGridY() {
		return toGridLocation().getGridY();
	}

	public GridLocation toGridLocation() {

		float x = this.getScreenX();
		float y = this.getScreenY();
		
		//x -= Common.TILE_WIDTH_HALF;
		//y -= Common.TILE_HEIGHT_HALF;
		
		int gY = (int)y / Common.TILE_HEIGHT_THREEQUARTERS;
		if (Math.abs(gY % 2) == 1) {
			x -= Common.TILE_WIDTH_HALF;
		}
		int gX = (int)x / Common.TILE_WIDTH;
		
		return new GridLocation(gX, gY);
	}
}
