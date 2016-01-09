package com.fmatos.crazywallpapers.wallpaper.domain;

import android.util.Log;

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
	private final Screen screen;

	public HeatManager(SoundFacade soundFacade, Screen screen) {
		this.soundFacade = soundFacade;
		this.screen = screen;
	}

	public int addPoint(float x, float y, boolean canPlaySound) {

		int count = 0;

		try {
			count = screen.addPoint(x, y);

			if (canPlaySound) {
				playSound(count);
			}
		} catch (Grid.GridEdgeOverflowException e) {
			// nothing to see here, report error
			Log.i(TAG,"Error adding point");
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
		return screen.getPoints();
	}

	public void onSurfaceChanged(int width, int height) {
		screen.onSurfaceChanged(height, width);
	}
}
