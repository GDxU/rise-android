package com.matthewtole.androidrise.lib;

import com.matthewtole.androidrise.lib.enums.GamePlayer;

public class GameUpdate {
	
	public static final int WORKER_SELECTED = 100;
	public static final int WORKER_UNSELECTED = 101;
	public static final int WORKER_MOVED = 102;
	public static final int WORKER_ADDED = 103;
	public static final int WORKER_JUMP = 104;
	public static final int TILE_ADDED = 200;
	public static final int TOWER_REDUCED = 300;
	public static final int TOWER_DEMOLISHED = 301;
	public static final int TOWER_CREATED = 302;
	public static final int TOWER_BUILT = 303;
	public static final int SACRIFICE_REMOVE = 400;
	public static final int SACRIFICE_ADD = 401;
	public static final int MOVE_MADE = 500;
	public static final int TURN_FINISHED = 501;
	
	public boolean success;
	public String failureReason = "";
	public int type;
	public GridLocation location;
	public GridLocation locationSecondary;
	public GridLocation locationTertiary;
	public int extraInt;
	public GamePlayer player;
	
	
	
	@Override
	public String toString() {
		switch (type) {
		case WORKER_SELECTED: return "WORKER_SELECTED";
		case WORKER_UNSELECTED: return "WORKER_UNSELECTED";
		case WORKER_MOVED: return "WORKER_MOVED";
		case WORKER_ADDED: return "WORKER_ADDED";
		case WORKER_JUMP: return "WORKER_JUMP";
		case TILE_ADDED: return "TILE_ADDED";
		case TOWER_REDUCED: return "TOWER_REDUCED";
		case TOWER_DEMOLISHED: return "TOWER_DEMOLISHED";
		case TOWER_CREATED: return "TOWER_CREATED";
		case TOWER_BUILT: return "TOWER_BUILT";
		case SACRIFICE_REMOVE: return "SACRIFICE_REMOVE";
		case SACRIFICE_ADD: return "SACRIFICE_ADD";
		case MOVE_MADE: return "MOVE_MADE";
		case TURN_FINISHED: return "TURN_FINISHED";
		}
		return failureReason;
	}

	public GameUpdate(boolean success, String reason) {
		this.success = success;
		this.failureReason = reason;
	}

	public GameUpdate(int type, GridLocation location) {
		this.success = true;
		this.type = type;
		this.location = location;
	}
	
	public GameUpdate(int type, GridLocation location, GridLocation locationSecondary) {
		this.success = true;
		this.type = type;
		this.location = location;
		this.locationSecondary = locationSecondary;
	}

	public GameUpdate(int type, GridLocation location,
			int extraInt) {
		this.success = true;
		this.type = type;
		this.location = location;
		this.extraInt = extraInt;
	}

	public GameUpdate(int type, GridLocation location, GridLocation locationSecondary, GridLocation locationTertiary) {
		this.success = true;
		this.type = type;
		this.location = location;
		this.locationSecondary = locationSecondary;
		this.locationTertiary = locationTertiary;
	}

	public GameUpdate(int type, GamePlayer player) {
		this.success = true;
		this.type = type;
		this.player = player;
	}

	public GameUpdate(int workerAdded, GridLocation gridLocation,
			GamePlayer player2) {
		// TODO Auto-generated constructor stub
	}
}
