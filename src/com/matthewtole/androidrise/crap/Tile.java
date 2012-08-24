package com.matthewtole.androidrise.crap;

public class Tile {

	private Tile[] neighbours;

	public Tile() {
		this.neighbours = new Tile[6];
	}

	public boolean addNeighbour(Tile neighbour, int direction) {
		if (this.neighbours[direction] == null) {
			return false;
		}
		this.neighbours[direction] = neighbour;
		return true;
	}
	
	public boolean hasNeighbour(int direction) {
		return this.neighbours[direction] != null;
	}
}
