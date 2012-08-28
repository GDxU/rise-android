package com.matthewtole.androidrise.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.matthewtole.androidrise.lib.RiseGame;

public class TurnIndicator {
	
	private Paint paint;
	private Rect rects[];
	private int turnCounter;
	private Paint paintInactive;

	public TurnIndicator(Rect rect, int colour, int count) {
		this.paint = new Paint();
		this.paint.setColor(colour == RiseGame.RED ? Color.parseColor("#E72D18") : Color.parseColor("#4B56CE"));
		this.paint.setAntiAlias(true);
		
		this.paintInactive = new Paint(this.paint);
		this.paintInactive.setColor(Color.parseColor("#444444"));
		
		this.turnCounter = count;
		this.rects = new Rect[2];
		this.rects[0] = new Rect(rect.left, rect.top, rect.left + rect.width() / 2 - 5, rect.bottom);
		this.rects[1] = new Rect(rect.left + rect.width() / 2 + 5, rect.top, rect.right, rect.bottom);
	}

	public void draw(Canvas canvas) {
		canvas.drawRect(this.rects[0], this.turnCounter > 0 ? this.paint : this.paintInactive);
		canvas.drawRect(this.rects[1], this.turnCounter > 1 ? this.paint : this.paintInactive);
	}
	
	public void moveMade() {
		this.turnCounter -= 1;
	}
	
	public void myTurn() {
		this.turnCounter = 2;
	}

}
