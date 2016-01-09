package com.fmatos.crazywallpapers.wallpaper.domain;

import java.util.Queue;

/**
 * Created by fdematos on 09/01/16.
 */
public class Screen {

	private Orientation orientation;

	private final Grid portrait;
	private final Grid landscape;
	private boolean isScreenInit;

	public Screen() {
		orientation = null;

		isScreenInit = false;
		this.portrait = new Grid();
		this.landscape = new Grid();
	}

	private void initScreen(int height,int width) {
		changeOrientation(height,width);
		if ( orientation == Orientation.PORTRAIT ) {
			portrait.onSurfaceChanged(height,width);
			landscape.onSurfaceChanged(width,height);
		} else {
			portrait.onSurfaceChanged(width,height);
			landscape.onSurfaceChanged(height,width);
		}
	}

	public int addPoint(float x,float y) {
		return getGrid().addPoint(x,y);
	}

	public Queue<Point> getPoints() {
		return getGrid().getPoints();
	}

	private Grid getGrid() {
		switch (orientation) {
			case PORTRAIT:
				return portrait;
			case LANDSCAPE:
				return  landscape;
			default:
				throw new RuntimeException("Must choose orientation");
		}
	}

	private Orientation changeOrientation(int height, int width) {
		if ( width < height) {
			orientation = Orientation.LANDSCAPE;
		} else {
			orientation = Orientation.PORTRAIT;
		}

		return orientation;
	}

	public void onSurfaceChanged(int height, int width) {
		if (! isScreenInit ) {
			isScreenInit = true;
			initScreen(height,width);
		}
		changeOrientation(height,width);
	}

	// for testing purposes only
	protected Orientation getOrientation() {
		return orientation;
	}
}
