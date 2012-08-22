package com.matthewtole.androidrise;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;

import com.matthewtole.androidrise.lib.GridRef;
import com.matthewtole.androidrise.lib.RiseGame;

public class MainView extends View {

	private RiseGame game;

	private HashMap<String, Bitmap> bitmaps;
	private HashMap<String, Paint> paints;

	private final static int X_MIN = -3;
	private final static int X_MAX = 4;
	private final static int Y_MIN = -7;
	private final static int Y_MAX = 8;
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
	private int sidebarWidth = 0;
	private int tileHeight = 0;
	private int tileWidth = 0;

	public MainView(Context context) {
		super(context);

		this.game = new RiseGame();

		this.bitmaps = new HashMap<String, Bitmap>();
		this.addBitmap("tile", R.drawable.tile);
		this.addBitmap("tileH", R.drawable.blue3);
		this.addBitmap("background", R.drawable.retina_wood);

		this.paints = new HashMap<String, Paint>();
		this.makePaints();
	}

	private void makePaints() {
		this.paints.put("sidebarBackground", new Paint());
		this.paints.get("sidebarBackground").setColor(Color.DKGRAY);
		this.paints.get("sidebarBackground").setAntiAlias(true);
		this.paints.get("sidebarBackground").setAlpha(200);
		this.paints.get("sidebarBackground").setStyle(Style.FILL);
		this.paints.put("sidebarLine", new Paint());
		this.paints.get("sidebarLine").setColor(Color.LTGRAY);
		this.paints.get("sidebarLine").setAntiAlias(true);
		this.paints.get("sidebarLine").setAlpha(120);
		this.paints.get("sidebarLine").setStrokeWidth(5.0f);
		this.paints.get("sidebarLine").setStyle(Style.FILL);
	}

	private void addBitmap(String label, int resource) {
		this.bitmaps.put(label,
				BitmapFactory.decodeResource(getResources(), resource));
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.BLACK);

		this.drawBackground(canvas);
		this.drawTiles(canvas);
		this.drawPieces(canvas);
		this.drawInterface(canvas);
	}

	private void drawInterface(Canvas canvas) {
		canvas.drawRect(0, 0, sidebarWidth, canvasHeight,
				this.paints.get("sidebarBackground"));
		canvas.drawLine(sidebarWidth, 0, sidebarWidth, canvasHeight,
				this.paints.get("sidebarLine"));

	}

	private void drawPieces(Canvas canvas) {
		for (int tX = X_MIN; tX < X_MAX; tX += 1) {
			for (int tY = Y_MIN; tY < Y_MAX; tY += 1) {
				if (this.game.hasPiece(tX, tY)) {
					if (this.game.pieceColour(tX, tY) == RiseGame.BLUE) {
						this.drawBitmap(canvas, tX, tY,
								this.bitmaps.get("worker_blue"));
					} else {
						this.drawBitmap(canvas, tX, tY,
								this.bitmaps.get("worker_red"));
					}
				} else if (this.game.hasTower(tX, tY)) {
					if (this.game.towerColour(tX, tY) == RiseGame.BLUE) {
						switch (this.game.towerHeight(tX, tY)) {
						case 1:
							this.drawBitmap(canvas, tX, tY,
									this.bitmaps.get("tower_blue_1"));
							break;
						case 2:
							this.drawBitmap(canvas, tX, tY,
									this.bitmaps.get("tower_blue_2"));
							break;
						case 3:
							this.drawBitmap(canvas, tX, tY,
									this.bitmaps.get("tower_blue_3"));
							break;
						}
					} else {
						switch (this.game.towerHeight(tX, tY)) {
						case 1:
							this.drawBitmap(canvas, tX, tY,
									this.bitmaps.get("tower_red_1"));
							break;
						case 2:
							this.drawBitmap(canvas, tX, tY,
									this.bitmaps.get("tower_red_2"));
							break;
						case 3:
							this.drawBitmap(canvas, tX, tY,
									this.bitmaps.get("tower_red_3"));
							break;
						}
					}
				}
			}
		}
	}

	private void drawBitmap(Canvas canvas, int tX, int tY, Bitmap bitmap) {
	}

	private void drawTile(Canvas canvas, int x, int y, boolean highlight) {

		canvasWidth = canvas.getWidth();
		canvasHeight = canvas.getHeight();
		sidebarWidth = 300;
		tileHeight = this.bitmaps.get("tile").getHeight();
		tileWidth = this.bitmaps.get("tile").getWidth();

		int centerX = drawOffsetX + (canvas.getWidth() / 2);
		int centerY = drawOffsetY + (canvas.getHeight() / 2);

		int tileW = this.bitmaps.get("tile").getWidth();
		int tileH = tileHeight;
		int tileW2 = tileW / 2;
		int tileW4 = (tileW / 4) * 3;
		int tileH2 = tileH / 2;

		int wX = (centerX + (tileW4 * x));
		int wY = (centerY + (tileH * y));
		if (Math.abs(x % 2) == 1) {
			wY += tileH2;
		}

		if (tileHighlightedX == x && tileHighlightedY == y) {
			canvas.drawBitmap(this.bitmaps.get("tileH"), wX - tileW2, wY
					- tileH2, null);
		} else {
			canvas.drawBitmap(this.bitmaps.get("tile"), wX - tileW2, wY
					- tileH2, null);
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
				canvas.drawBitmap(this.bitmaps.get("background"), x, y, null);
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
			if (event.getX() <= sidebarWidth) {
				dragging = false;
				return sidebarClick(event.getX(), event.getY());
			}
			if (dragging) {
				updateDrawOffset(event.getX(), event.getY());
				dragging = false;
			} else {
				return this.gameClick(event.getX(), event.getY());
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getX() <= sidebarWidth) {
				dragging = false;
				return false;
			}
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

	private boolean gameClick(float x, float y) {
		GridRef gr = getGridRef(x, y);
		tileHighlightedX = gr.x;
		tileHighlightedY = gr.y;
		this.invalidate();
		return true;
	}

	private boolean sidebarClick(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	private GridRef getGridRef(float x, float y) {

		x -= drawOffsetX;
		y -= drawOffsetY;

		x = x - (canvasWidth / 2);
		y = y - (canvasHeight / 2);

		if (y < 0) {
			y -= tileHeight / 2;
		} else {
			y += tileHeight / 2;
		}

		if (x < 0) {
			x -= tileWidth / 2;
		} else if (x > 0) {
			x += tileWidth / 2;
		}

		int gX = (int) (x / ((tileWidth / 4) * 3));

		if (Math.abs(gX % 2) == 1) {
			y -= tileHeight / 2;
		}

		int gY = (int) (y / tileHeight);
		return new GridRef(gX, gY);
	}

}