package com.matthewtole.androidrise;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.matthewtole.androidrise.lib.GridRef;
import com.matthewtole.androidrise.lib.RiseGame;
import com.matthewtole.androidrise.lib.SpriteManager;

public class MainView extends View {

	private RiseGame game;

	private SpriteManager spriteManager;
	private HashMap<String, Paint> paints;

	private final int SIZE_MIN = 0;
	private final int SIZE_MAX = 60;
	private final int DRAG_START_AMOUNT = 20;
	private final int TILE_HEIGHT = 100;
	private final int TILE_WIDTH = 86;

	private final int TILE_HEIGHT_HALF = TILE_HEIGHT / 2;
	private final int TILE_WIDTH_HALF = TILE_WIDTH / 2;
	private final int TILE_HEIGHT_THREEQUARTERS = (TILE_HEIGHT / 4) * 3;

	private boolean dragging = false;
	private float dragStartX = 0;
	private float dragStartY = 0;

	private int drawOffsetX = 0;
	private int drawOffsetY = 0;

	private int canvasWidth = 0;
	private int canvasHeight = 0;
	private int canvasWidthHalf = 0;
	private int canvasHeightHalf = 0;

	private int sidebarWidth = 0;

	private char[][] layout;
	private int[] layoutHome;

	private boolean firstDraw = true;

	private int centerX = 0;
	private int centerY = 0;

	public MainView(Context context) {
		super(context);

		this.game = new RiseGame();
		this.loadLayout("cold_war");
		this.game.setup(this.layout);
		this.layoutHome = new int[2];

		this.spriteManager = new SpriteManager(context);
		this.paints = new HashMap<String, Paint>();
		this.makePaints();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dragStartX = event.getX();
			dragStartY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if (this.game.getState() != RiseGame.GAME_STATE_DONE) {
				if (event.getX() <= sidebarWidth) {
					dragging = false;
					return sidebarClick(event.getX(), event.getY());
				}
				if (event.getX() > this.canvasWidth - 75 && event.getY() < 75) {
					this.resetPosition();
					return true;
				}
				if (dragging) {
					updateDrawOffset(event.getX(), event.getY());
					dragging = false;
				} else {
					return this.gameClick(event.getX(), event.getY());
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getX() <= sidebarWidth) {
				dragging = false;
				return false;
			}
			if (this.game.getState() != RiseGame.GAME_STATE_DONE) {
				if (!dragging
						&& (Math.abs(event.getX() - dragStartX) > DRAG_START_AMOUNT || Math
								.abs(event.getY() - dragStartY) > DRAG_START_AMOUNT)) {
					dragging = true;
				}
				if (dragging) {
					updateDrawOffset(event.getX(), event.getY());
				}
			}
			break;
		}

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (firstDraw) {			
			canvasWidth = canvas.getWidth();
			canvasHeight = canvas.getHeight();
			canvasWidthHalf = canvasWidth / 2;
			canvasHeightHalf = canvasHeight / 2;
			centerX = drawOffsetX + canvasWidthHalf;
			centerY = drawOffsetY + canvasHeightHalf;

			sidebarWidth = 250;
			this.jumpToCenter(canvas);
			firstDraw = false;
		}

		canvas.drawColor(Color.BLACK);

		this.drawBackground(canvas);
		this.drawTiles(canvas);
		this.drawPieces(canvas);

		if (this.game.getState() == RiseGame.GAME_STATE_DONE) {
			this.drawFinishScreen(canvas);
		} else {
			this.drawInterface(canvas);
		}
	}

	private boolean loadLayout(String name) {
		try {
			InputStream stream = this.getContext().getAssets()
					.open("layouts/" + name + ".txt");
			String layoutString = readTxt(stream);
			layoutString = layoutString.replace(",", "");
			layoutString = layoutString.replace(".", "");

			String[] layoutRows = layoutString.split("\n");
			this.layout = new char[layoutRows[0].length()][layoutRows.length];
			for (int r = 0; r < layoutRows.length; r += 1) {
				for (int c = 0; c < layoutRows[r].length(); c += 1) {
					this.layout[c][r] = layoutRows[r].charAt(c);
				}
			}
			return true;

		} catch (IOException e) {
			Log.e("MainView", e.getMessage());
		} catch (Exception ex) {
			Log.e("MainView", ex.getMessage());
		}
		return false;
	}

	private String readTxt(InputStream stream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		int i;
		try {
			i = stream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = stream.read();
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteArrayOutputStream.toString();
	}

	private void makePaints() {
		this.paints.put("sidebarBackground", new Paint());
		this.paints.get("sidebarBackground").setColor(
				Color.parseColor("#333333"));
		this.paints.get("sidebarBackground").setStyle(Style.FILL);
		this.paints.get("sidebarBackground").setAntiAlias(true);

		this.paints.put("finishBackground", new Paint());
		this.paints.get("finishBackground").setColor(
				Color.parseColor("#333333"));
		this.paints.get("finishBackground").setStyle(Style.FILL);
		this.paints.get("finishBackground").setAntiAlias(true);
		this.paints.get("finishBackground").setAlpha(200);

		this.paints.put("text", new Paint());
		this.paints.get("text").setColor(Color.WHITE);
		this.paints.get("text").setTextSize(48);

		Paint lineMaster = new Paint();
		lineMaster.setColor(Color.WHITE);
		lineMaster.setAntiAlias(true);
		lineMaster.setStyle(Style.FILL);

		this.paints.put("sidebarLineMiddle", new Paint(lineMaster));
		this.paints.put("sidebarLineRed", new Paint(lineMaster));
		this.paints.get("sidebarLineRed").setColor(Color.parseColor("#E72D18"));
		this.paints.put("sidebarLineBlue", new Paint(lineMaster));
		this.paints.get("sidebarLineBlue")
				.setColor(Color.parseColor("#4B56CE"));
		this.paints.put("sidebarLineDisabled", new Paint(lineMaster));
		this.paints.get("sidebarLineDisabled").setColor(
				Color.parseColor("#222222"));
	}

	private void drawFinishScreen(Canvas canvas) {

		RectF inRect = new RectF(10, 10, canvasWidth - 10, canvasHeight - 10);
		canvas.drawRoundRect(inRect, 20.0f, 20.0f,
				this.paints.get("finishBackground"));

	}

	private void jumpToCenter(Canvas canvas) {
		int[] piece1 = { 0, 0 };
		int[] piece2 = { 0, 0 };

		for (int tX = SIZE_MIN; tX < SIZE_MAX; tX += 1) {
			for (int tY = SIZE_MIN; tY < SIZE_MAX; tY += 1) {
				if (this.game.hasPiece(tX, tY)) {
					if (this.game.pieceColour(tX, tY) == RiseGame.BLUE) {
						piece1[0] = tX;
						piece1[1] = tY;
					} else {
						piece2[0] = tX;
						piece2[1] = tY;
					}
				}
			}
		}

		int[] tmp = tilePosToScreenCoordinates(canvas,
				(piece1[0] + piece2[0]) / 2, (piece1[1] + piece2[1]) / 2);
		this.drawOffsetX = (-1 * tmp[0])
				+ (this.sidebarWidth + (this.canvasWidth - sidebarWidth) / 2);
		this.drawOffsetY = (-1 * tmp[1]) + canvasHeightHalf;
		centerX = drawOffsetX + canvasWidthHalf;
		centerY = drawOffsetY + canvasHeightHalf;
		this.layoutHome[0] = this.drawOffsetX;
		this.layoutHome[1] = this.drawOffsetY;
	}

	private void drawInterface(Canvas canvas) {

		canvas.drawBitmap(this.spriteManager.getBitmap("target"),
				this.canvasWidth - 75, 10, null);

		RectF inRect = new RectF(10, 10, sidebarWidth - 10, canvasHeight - 10);
		canvas.drawRoundRect(inRect, 20.0f, 20.0f,
				this.paints.get("sidebarBackground"));

		int middle = (canvasHeight - 20) / 2;

		RectF redRect = new RectF(20, middle - 40, sidebarWidth - 20,
				middle - 5);
		canvas.drawRoundRect(redRect, 5.0f, 5.0f, this.paints.get(this.game
				.getCurrentPlayer() == RiseGame.RED ? "sidebarLineRed"
				: "sidebarLineDisabled"));

		RectF redRect2 = new RectF(20, middle - 85, sidebarWidth - 20,
				middle - 50);
		canvas.drawRoundRect(redRect2, 5.0f, 5.0f, this.paints.get(this.game
				.getCurrentPlayer() == RiseGame.RED
				&& this.game.getMovesLeft() > 1 ? "sidebarLineRed"
				: "sidebarLineDisabled"));

		RectF BlueRect = new RectF(20, middle + 5, sidebarWidth - 20,
				middle + 40);
		canvas.drawRoundRect(BlueRect, 5.0f, 5.0f, this.paints.get(this.game
				.getCurrentPlayer() == RiseGame.BLUE ? "sidebarLineBlue"
				: "sidebarLineDisabled"));

		RectF BlueRect2 = new RectF(20, middle + 50, sidebarWidth - 20,
				middle + 85);
		canvas.drawRoundRect(BlueRect2, 5.0f, 5.0f, this.paints.get(this.game
				.getCurrentPlayer() == RiseGame.BLUE
				&& this.game.getMovesLeft() > 1 ? "sidebarLineBlue"
				: "sidebarLineDisabled"));
	}

	private void drawPieces(Canvas canvas) {

		for (int tX = SIZE_MIN; tX < SIZE_MAX; tX += 1) {
			for (int tY = SIZE_MIN; tY < SIZE_MAX; tY += 1) {
				if (!this.game.hasPiece(tX, tY)) {
					continue;
				}

				String colour = RiseGame.colourName(this.game.pieceColour(tX,
						tY));
				String type = "worker";
				if (this.game.hasTower(tX, tY)) {
					type = String.format("tower%1d",
							this.game.towerHeight(tX, tY));
				}
				this.drawBitmap(canvas, tX, tY,
						this.spriteManager.getBitmap(colour + "_" + type));

				if (this.game.isSelectedWorker(tX, tY)) {
					this.drawBitmap(canvas, tX, tY,
							this.spriteManager.getBitmap("dots/0"));
				}
			}
		}
	}

	private int[] tilePosToScreenCoordinates(Canvas canvas, int x, int y) {
		int wX = (centerX + (TILE_WIDTH * x));
		int wY = (centerY + (TILE_HEIGHT_THREEQUARTERS * y));
		if (Math.abs(y % 2) == 1) {
			wX += TILE_WIDTH_HALF;
		}

		return new int[] { wX, wY };
	}

	private void drawBitmap(Canvas canvas, int x, int y, Bitmap bitmap) {

		int centerX = drawOffsetX + canvasWidthHalf;
		int centerY = drawOffsetY + canvasHeightHalf;

		int wX = (centerX + (TILE_WIDTH * x));
		int wY = (centerY + (TILE_HEIGHT_THREEQUARTERS * y));
		if (Math.abs(y % 2) == 1) {
			wX += TILE_WIDTH_HALF;
		}

		canvas.drawBitmap(bitmap, wX - TILE_WIDTH_HALF, wY - TILE_HEIGHT_HALF,
				null);
	}

	private void drawTile(Canvas canvas, int x, int y) {
		if (this.game.hasTile(x, y)) {
			this.drawBitmap(canvas, x, y, this.spriteManager.getBitmap("tile"));
		}
	}

	private void drawTiles(Canvas canvas) {
		for (int tX = SIZE_MIN; tX < SIZE_MAX; tX += 1) {
			for (int tY = SIZE_MIN; tY < SIZE_MAX; tY += 1) {
				drawTile(canvas, tX, tY);
			}
		}
	}

	private void drawBackground(Canvas canvas) {
		for (int x = -256; x < canvasWidth + 256; x += 256) {
			for (int y = -256; y < canvasHeight + 256; y += 256) {
				canvas.drawBitmap(this.spriteManager.getBitmap("background"), x
						+ (this.drawOffsetX % 256), y
						+ (this.drawOffsetY % 256), null);
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

	private void resetPosition() {
		this.drawOffsetX = this.layoutHome[0];
		this.drawOffsetY = this.layoutHome[1];
		centerX = drawOffsetX + canvasWidthHalf;
		centerY = drawOffsetY + canvasHeightHalf;
		this.invalidate();
	}

	private boolean gameClick(float x, float y) {
		GridRef gr = getGridRef(x, y);
		/*
		 * tileHighlightedX = gr.x; tileHighlightedY = gr.y;
		 */
		this.game.doAction(gr.x, gr.y, this.game.getCurrentPlayer());
		this.invalidate();
		return true;
	}

	private boolean sidebarClick(float x, float y) {
		this.game.setup(this.layout);
		this.resetPosition();
		this.invalidate();
		return true;
	}

	private GridRef getGridRef(float x, float y) {

		x -= drawOffsetX;
		y -= drawOffsetY;

		x = x - canvasWidthHalf;
		y = y - canvasHeightHalf;

		if (y < 0) {
			y -= TILE_HEIGHT_HALF;
		} else {
			y += TILE_HEIGHT_HALF;
		}

		if (x < 0) {
			x -= TILE_WIDTH_HALF;
		} else if (x > 0) {
			x += TILE_WIDTH_HALF;
		}

		int gY = (int) (y / TILE_HEIGHT_THREEQUARTERS);
		if (Math.abs(gY % 2) == 1) {
			x -= TILE_WIDTH_HALF;
		}
		int gX = (int) (x / TILE_WIDTH);

		return new GridRef(gX, gY);
	}

}