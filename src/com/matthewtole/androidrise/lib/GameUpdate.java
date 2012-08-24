package com.matthewtole.androidrise.lib;

public class GameUpdate {
	
	public static final int WORKER_SELECTED = 100;
	public static final int WORKER_UNSELECTED = 101;
	public static final int WORKER_MOVED = 102;
	public static final int WORKER_ADDED = 103;
	public static final int WORKER_JUMP = 104;
	public static final int TILE_ADDED = 200;
	public static final int TOWER_REDUCED = 300;
	public static final int TOWER_DEMOLISHED = 301;
	public static final int SACRIFICE_REMOVE = 400;
	public static final int SACRIFICE_ADD = 401;
	
	public boolean success;
	public String failureReason = "";
	public int type;
	public GridLocation location;
	public GridLocation locationSecondary;
	public GridLocation locationTertiary;
	public int extraInt;
	
	
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
}
