package com.fmatos.crazywallpapers.wallpaper.service;


import com.fmatos.crazywallpapers.sound.SoundFacade;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by fdematos on 29/12/15.
 */
public class HeatManager {

	private static final String TAG = HeatManager.class.getSimpleName();

	private static final int MAX_SIZE = 50;
	private static final int GRID_DIMENSION = 5;

	private static final int BURN_THRESHOLD = 10;
	private static final int CLING_THRESHOLD = 5;
	private static final int CRASH_THRESHOLD = 15;

	private final Queue<Point> points; // TODO do we need SQLite to persist values or is it fun on itself?

	private final SoundFacade soundFacade;

	private final int grid[][];
	private int height;
	private int width;

	public HeatManager(SoundFacade soundFacade) {
		this.soundFacade = soundFacade;
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

		if (points.size() > MAX_SIZE) {
			processGrid(points.remove(), -1);
			soundFacade.playCling();
		}

		Point point = new Point(x, y);
		points.add(point);

		return processGrid(point, 1);


		// TODO need some worker thread to produce the heat map here?

	}

	private int processGrid(Point p, int dv) {
		int i = (int)(p.getX() / (width / GRID_DIMENSION));
		int j = (int)(p.getY() / (height / GRID_DIMENSION));

		int count = 0;

		if (i < GRID_DIMENSION && j < GRID_DIMENSION) {

			grid[i][j] += dv;
			//Log.d(TAG,"(" + i + "," + j + ") =  " + grid[i][j] + " / dv = " + dv );

			count = grid[i][j];
			if (count > CRASH_THRESHOLD) {
				if (Math.random() > 0.7d) {
					soundFacade.playGlass();
				}
			} else if (count > BURN_THRESHOLD) {
				if (Math.random() > 0.3d) {
					soundFacade.playBurn();
				}
			} else if (count > CLING_THRESHOLD) {
				if (Math.random() > 0.3d) {
					soundFacade.playCling();
				}
			}

		}

		return count;
	}

	public Collection<Point> getHeatMap() {
		return points;
	}

	public void onSurfaceChanged(int width, int height) {
		this.width = width;
		this.height = height;

		for (Point p : points) {
			processGrid(p, 1);
		}
	}
}
