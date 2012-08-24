package com.matthewtole.androidrise.crap;

public class GridRef {
	public int x;
	public int y;

	public GridRef(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public GridRef(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
	}
}
