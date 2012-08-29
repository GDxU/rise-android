package com.matthewtole.androidrise.lib;

import com.matthewtole.androidrise.lib.enums.GamePlayer;
import com.matthewtole.androidrise.lib.enums.UpdateType;

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

	public UpdateType type;
	public GridLocation location;
	public GridLocation locationSecondary;
	public GridLocation locationTertiary;
	public GamePlayer player;

	@Override
	public String toString() {
		return type.name();
	}

	public GameUpdate(UpdateType type, GridLocation location) {
		this.type = type;
		this.location = location;
	}

	public GameUpdate(UpdateType type, GridLocation location,
			GridLocation locationSecondary) {
		this.type = type;
		this.location = location;
		this.locationSecondary = locationSecondary;
	}

	public GameUpdate(UpdateType type, GridLocation location,
			GridLocation locationSecondary, GridLocation locationTertiary) {
		this.type = type;
		this.location = location;
		this.locationSecondary = locationSecondary;
		this.locationTertiary = locationTertiary;
	}

	public GameUpdate(UpdateType type, GamePlayer player) {
		this.type = type;
		this.player = player;
	}

	public GameUpdate(UpdateType type, GridLocation location, GamePlayer player) {
		this.type = type;
		this.location = location;
		this.player = player;

	}
}
