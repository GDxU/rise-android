package com.matthewtole.androidrise.lib;

import java.util.ArrayList;

public class Board {
	
	/*       3
	 *     #####
	 *  5 ####### 2
	 *   ######### 
	 *  4 ####### 1
	 *     #####
	 *       0
	 */
	
	private ArrayList<Tile> tiles;

	public boolean addTile() {
		if (this.tiles.size() > 0) {
			return false;
		}
		tiles.add(new Tile());
		return true;
	}

	public boolean addTile(Tile neighbour, int direction) {
		if (neighbour.hasNeighbour(direction)) {
			return false;
		}
		Tile tile = new Tile();
		tile.addNeighbour(neighbour, direction);
		neighbour.addNeighbour(tile, Board.invDirection(direction));
		tiles.add(tile);
		return true;
	}

	public static int invDirection(int direction) {
		return direction < 4 ? (direction + 3) : (direction - 3);
	}
}
