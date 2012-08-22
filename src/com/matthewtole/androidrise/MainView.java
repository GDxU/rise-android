package com.matthewtole.androidrise;

import com.matthewtole.androidrise.lib.GridRef;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {
	private Bitmap tile;
	private Bitmap background;
	private Paint paint;

	private final static int X_MIN = -1;
	private final static int X_MAX = 2;
	private final static int Y_MIN = -1;
	private final static int Y_MAX = 2;
	private final static int DRAG_START_AMOUNT = 10;

	private boolean dragging = false;
	private float dragStartX = 0;
	private float dragStartY = 0;

	private int drawOffsetX = 0;
	private int drawOffsetY = 0;

	private int tileHighlightedX = 0;
	private int tileHighlightedY = 0;
	
	private int canvasWidth = 0;
	private int canvasHeight = 0;
	private int tileHeight = 0;
	private int tileWidth = 0;
	
	private GridRef lastTouch;

	public MainView(Context context) {
		super(context);
		tile = BitmapFactory.decodeResource(getResources(), R.drawable.tile);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.retina_wood);

		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		paint.setStyle(Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.BLACK);

		this.drawBackground(canvas);
		this.drawTiles(canvas);
		this.drawPieces(canvas);
		this.drawInterface(canvas);
		
		if (this.lastTouch != null) {
			canvas.drawCircle(lastTouch.x, lastTouch.y, 10, paint);
		}
	}

	private void drawInterface(Canvas canvas) {
		
	}

	private void drawPieces(Canvas canvas) {
		// TODO Auto-generated method stub
	}
	
	private void drawTile(Canvas canvas, int x, int y, boolean highlight) {
		
		canvasWidth = canvas.getWidth();
		canvasHeight = canvas.getHeight();
		tileHeight = tile.getHeight();
		tileWidth  = tile.getWidth();

		int centerX = drawOffsetX + (canvas.getWidth() / 2);
		int centerY = drawOffsetY + (canvas.getHeight() / 2);

		int tileW = tile.getWidth();
		int tileH = tileHeight;
		int tileW2 = tileW / 2;
		int tileW4 = (tileW / 4) * 3;
		int tileH2 = tileH / 2;

		int wX = (centerX + (tileW4 * x));
		int wY = (centerY + (tileH * y));
		if (Math.abs(x % 2) == 1) {
			wY += tileH2;
		}

		canvas.drawBitmap(tile, wX - tileW2, wY - tileH2, null);
		if (highlight) {
			canvas.drawCircle(wX, wY, 50, paint);
		}
	}	

	private void drawTiles(Canvas canvas) {
		for (int tX = X_MIN; tX < X_MAX; tX += 1) {
			for (int tY = Y_MIN; tY < Y_MAX; tY += 1) {
				if (tileHighlightedX == tX && tileHighlightedY == tY) {
					drawTile(canvas, tX, tY, true);
				} else {
					drawTile(canvas, tX, tY, false);
				}
			}
		}

	}

	private void drawBackground(Canvas canvas) {
		for (int x = 0; x < canvas.getWidth(); x += 256) {
			for (int y = 0; y < canvas.getHeight(); y += 256) {
				canvas.drawBitmap(background, x, y, null);
			}
		}
	}

	@SuppressWarnings("unused")
	private static String coordString(int x, int y) {
		return String.valueOf(x) + "," + String.valueOf(y);
	}

	@SuppressWarnings("unused")
	private static String coordString(float x, float y) {
		return String.valueOf(x) + "," + String.valueOf(y);
	}

	private void updateDrawOffset(float x, float y) {
		this.drawOffsetX += (x - dragStartX);
		this.drawOffsetY += (y - dragStartY);
		dragStartX = x;
		dragStartY = y;
		this.invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dragStartX = event.getX();
			dragStartY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if (dragging) {
				updateDrawOffset(event.getX(), event.getY());
				dragging = false;
			}
			else {
				lastTouch = new GridRef(event.getX(), event.getY());
				GridRef gr = getGridRef(event.getX(), event.getY());
				tileHighlightedX = gr.x;
				tileHighlightedY = gr.y;
				this.invalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (!dragging
					&& (Math.abs(event.getX() - dragStartX) > DRAG_START_AMOUNT || Math
							.abs(event.getY() - dragStartY) > DRAG_START_AMOUNT)) {
				dragging = true;
			}
			if (dragging) {
				updateDrawOffset(event.getX(), event.getY());
			}
			break;
		}

		return true;
	}

	private GridRef getGridRef(float x, float y) {
		
		x -= drawOffsetX;
		y -= drawOffsetY;
		
		x = x - (canvasWidth/ 2);
		y = y - (canvasHeight / 2);
		y -= tileHeight / 2;
		
		int gX = (int) (x / ((tileWidth / 4) * 3));
		if (Math.abs(gX % 2) == 1) {
			y -= tileHeight / 2;
		}
		
		int gY = (int) (y / tileHeight);
		return new GridRef(gX, gY);
	}

}