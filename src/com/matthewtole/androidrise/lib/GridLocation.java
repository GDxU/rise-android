package com.matthewtole.androidrise.lib;

import com.matthewtole.androidrise.game.Common;

public class GridLocation implements Location {

	private int x = 0;
	private int y = 0;

	public GridLocation(int x, int y) {
		this.x = x;
		this.y = y;		
	}

	public int getScreenX() {
		return toScreen().getScreenX();
	}

	private ScreenLocation toScreen() {
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
		return this.x ;
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
}
