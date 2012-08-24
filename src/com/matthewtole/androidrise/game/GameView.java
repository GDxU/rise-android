package com.matthewtole.androidrise.game;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.matthewtole.androidrise.game.enums.GamePlayer;
import com.matthewtole.androidrise.lib.GridLocation;
import com.matthewtole.androidrise.lib.RiseGame;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = SurfaceHolder.class.getSimpleName();

	private final float DRAG_START_AMOUNT = 30;

	private GameThread thread;
	private int surfaceWidth = 0;
	private int surfaceHeight = 0;

	private RiseGame game;

	private SpriteManager spriteManager;

	private float offsetX = 0;
	private float offsetY = 0;

	private ArrayList<Tile> tiles;
	private ArrayList<Tower> towers;
	private ArrayList<Worker> workers;

	private float dragStartX = 0;
	private float dragStartY = 0;
	private boolean isDragging = false;
	
	private int sleepCounter = 120;

	public GameView(Context context) {
		super(context);

		this.getHolder().addCallback(this);
		this.thread = new GameThread(this.getHolder(), this);

		this.spriteManager = new SpriteManager(context);

		this.tiles = new ArrayList<Tile>();
		this.towers = new ArrayList<Tower>();
		this.workers = new ArrayList<Worker>();
		
		Tile tmp = new Tile(this.spriteManager);
		tmp.setLocation(new GridLocation(0, 0), true);
		this.tiles.add(tmp);

		tmp = new Tile(this.spriteManager);
		tmp.setLocation(new GridLocation(1, 0), true);
		this.tiles.add(tmp);

		tmp = new Tile(this.spriteManager);
		tmp.setLocation(new GridLocation(0, 1), true);
		this.tiles.add(tmp);

		tmp = new Tile(this.spriteManager);
		tmp.setLocation(new GridLocation(0, 2), true);
		this.tiles.add(tmp);

		Worker w = new Worker(this.spriteManager, GamePlayer.RED);
		w.setLocation(new GridLocation(1, 0));
		w.setLocation(new GridLocation(0, 2), false);
		this.workers.add(w);

		Tower to = new Tower(this.spriteManager, GamePlayer.RED);
		to.setLocation(new GridLocation(0, 0), true);
		this.towers.add(to);

		this.setFocusable(true);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.surfaceWidth = width;
		this.surfaceHeight = height;
		this.offsetX = width / 2;
		this.offsetY = height / 3;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
		Log.d(TAG, "Surface Created");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				this.thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		canvas.translate(this.offsetX, this.offsetY);
		this.drawBackground(canvas);
		// canvas.save();

		this.drawTiles(canvas);
		this.drawPieces(canvas);
		this.drawInterface(canvas);
		// canvas.restore();
	}

	private void drawBackground(Canvas canvas) {
		for (int x = -2000; x < 2000; x += 256) {
			for (int y = -2000; y < 2000; y += 256) {
				canvas.drawBitmap(this.spriteManager.getBitmap("background"),
						x, y, null);
			}
		}
	}

	private void drawTiles(Canvas canvas) {
		for (Tile tile : this.tiles) {
			tile.draw(canvas);
		}
	}

	private void drawPieces(Canvas canvas) {
		for (Worker worker : this.workers) {
			worker.draw(canvas);
		}
		for (Tower tower : this.towers) {
			tower.draw(canvas);
		}
	}

	private void drawInterface(Canvas canvas) {
		// TODO Auto-generated method stub
	}

	public void update() {
		
		if (this.sleepCounter > 0) {
			this.sleepCounter -= 1;
			return;
		}
		
		for (Worker worker : this.workers) {
			worker.update();
		}

		for (Tower tower : this.towers) {
			tower.update();
		}
	}

	private void updateDrawOffset(float x, float y) {
		this.offsetX += (x - this.dragStartX);
		this.offsetY += (y - this.dragStartY);
		this.dragStartX = x;
		this.dragStartY = y;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dragStartX = event.getX();
			dragStartY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if (this.isDragging) {
				updateDrawOffset(event.getX(), event.getY());
				this.isDragging = false;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (!this.isDragging
					&& (Math.abs(event.getX() - dragStartX) > this.DRAG_START_AMOUNT || Math
							.abs(event.getY() - dragStartY) > this.DRAG_START_AMOUNT)) {
				this.isDragging = true;
			}
			if (this.isDragging) {
				updateDrawOffset(event.getX(), event.getY());
			}
			break;
		}

		return true;
	}

}
