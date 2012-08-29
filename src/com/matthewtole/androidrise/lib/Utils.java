package com.matthewtole.androidrise.lib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

import com.matthewtole.androidrise.lib.enums.GamePlayer;

public class Utils {

	public static String coordString(int x, int y) {
		return String.valueOf(x) + "," + String.valueOf(y);
	}

	public static String coordString(float x, float y) {
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

	public static String readTextAsset(Context context, String name) {

		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			InputStream stream = context.getAssets().open(
					"layouts/" + name + ".txt");
			int i = stream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = stream.read();
			}
			stream.close();
			return byteArrayOutputStream.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
