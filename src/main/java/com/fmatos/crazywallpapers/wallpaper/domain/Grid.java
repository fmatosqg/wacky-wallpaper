package com.fmatos.crazywallpapers.wallpaper.domain;

import android.graphics.Color;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by fdematos on 01/01/16.
 */
public class Grid {

	private static final int GRID_DIMENSION = 5;
	private static final int MAX_SIZE = 50; // amount of stored points


	private final Queue<Point> points; // TODO do we need SQLite to persist values or is it fun on itself?

	private final int grid[][];

	private int height;
	private int width;


	public Grid() {

		points = new LinkedList(); // TODO need an efficient custom circular buffer implementation
		grid = new int[GRID_DIMENSION][];

		for (int i = 0; i < GRID_DIMENSION; i++) {
			grid[i] = new int[GRID_DIMENSION];
			for (int j = 0; j < GRID_DIMENSION; j++) {
				grid[i][j] = 0;
			}
		}
	}

	public int addPoint(float x, float y) {

		Point point = new Point(x, y, Color.YELLOW );

		// TODO need some worker thread to produce the heat map here?
		checkOverflowed();
		int count = processGrid(point, 1);

		int color;

		if ( count > 10 ) {
			color = Color.RED;
		} else if ( count > 7 ) {
			color = Color.rgb(255,0,255);
		} else if ( count > 5 ) {
			color = Color.BLUE;
		} else if ( count > 2 ) {
			color = Color.CYAN;
		} else {
			color = Color.YELLOW;
		}

		Point newPoint = new Point(x,y,color);
		points.add(newPoint);

		return count;
	}

	private void checkOverflowed() {
		if (points.size() > MAX_SIZE) {
			processGrid(points.remove(), -1);
		}
	}


	private int processGrid(Point p, int dv) {
		int i = (int)(p.getX() / (width / GRID_DIMENSION));
		int j = (int)(p.getY() / (height / GRID_DIMENSION));

		int count = 0;

		if (i < GRID_DIMENSION && j < GRID_DIMENSION) {

			grid[i][j] += dv;
			//Log.d(TAG,"(" + i + "," + j + ") =  " + grid[i][j] + " / dv = " + dv );

			count = grid[i][j];
		} else {
			throw new GridEdgeOverflowException("Beyond edge at " + i + "," + j);
		}

		return count;
	}

	public void onSurfaceChanged(int height,int width) {
		this.width = width;
		this.height = height;
	}

	public Queue<Point> getPoints() {
		return points;
	}

	public static class GridEdgeOverflowException extends RuntimeException {

		public GridEdgeOverflowException(String s) {
			super(s);
		}
	}
}
