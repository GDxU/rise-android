package com.matthewtole.androidrise.game.pieces;

import java.util.ArrayList;

import android.graphics.Canvas;

import com.matthewtole.androidrise.game.SpriteManager;
import com.matthewtole.androidrise.lib.GridLocation;

public class CompositePiece extends Piece {

	@SuppressWarnings("unused")
	private static final String TAG = CompositePiece.class.getSimpleName();
	private ArrayList<Piece> pieces;

	public CompositePiece(SpriteManager sprites) {
		super(sprites);
		this.pieces = new ArrayList<Piece>();
	}

	protected void addPiece(Piece p) {
		this.pieces.add(p);
	}
	
	protected void hidePiece(int i) {
		this.pieces.get(i).hide();
		
	}
	
	protected Piece getPiece(int i) {
		return this.pieces.get(i);		
	}
	
	protected void showPiece(int i) {
		this.pieces.get(i).show();		
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		for (Piece p : this.pieces) {
			p.draw(canvas);
		}
	}
	
	@Override
	public void update() {
		super.update();
		for (Piece p : this.pieces) {
			p.update();
		}
	}

	@Override
	public void setLocation(GridLocation loc, boolean instant) {
		super.setLocation(loc, instant);
		for (Piece p : this.pieces) {
			p.setLocation(this.location.getScreenX(), this.location.getScreenY());
		}
	}

	@Override
	protected void setLocation(float x, float y) {
		super.setLocation(x, y);
		for (Piece p : this.pieces) {
			p.setLocation(x, y);
		}
	}

}
