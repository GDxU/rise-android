package com.matthewtole.androidrise.lib;

import com.matthewtole.androidrise.game.enums.GamePlayer;

public class Utils {

	public static String coordString(int x, int y) {
		return String.valueOf(x) + "," + String.valueOf(y);
	}

	public static String playerString(GamePlayer player) {
		switch (player) {
		case BLUE:
			return "blue";
		case RED:
			return "red";
		}
		return null;
	}

}
