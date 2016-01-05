package com.fmatos.crazywallpapers.wallpaper.domain;

import com.fmatos.crazywallpapers.sound.SoundFacade;

import java.util.Collection;

/**
 * Created by fdematos on 29/12/15.
 */
public class HeatManager {

	private static final String TAG = HeatManager.class.getSimpleName();


	private static final int BURN_THRESHOLD = 10;
	private static final int CLING_THRESHOLD = 5;
	private static final int CRASH_THRESHOLD = 15;


	private final SoundFacade soundFacade;

	private final Grid theGrid;

	public HeatManager(SoundFacade soundFacade, Grid theGrid) {
		this.soundFacade = soundFacade;
		this.theGrid = theGrid;

	}

	public int addPoint(float x, float y, boolean canPlaySound) {

		int count = theGrid.addPoint(x, y);

		if (canPlaySound) {
			playSound(count);
		}

		return count;
	}

	private void playSound(int count) {
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

	public Collection<Point> getHeatMap() {
		return theGrid.getPoints();
	}

	public void onSurfaceChanged(int width, int height) {
		theGrid.onSurfaceChanged(width, height);
	}
}
