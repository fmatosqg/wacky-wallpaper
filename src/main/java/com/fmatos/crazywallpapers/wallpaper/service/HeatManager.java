package com.fmatos.crazywallpapers.wallpaper.service;


import com.fmatos.crazywallpapers.sound.SoundFacade;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by fdematos on 29/12/15.
 */
public class HeatManager {

	private static final int MAX_SIZE = 20;
	private final Queue<Point> points; // TODO do we need SQLite to persist values or is it fun on itself?

	private final SoundFacade soundFacade;

	public HeatManager(SoundFacade soundFacade) {
		this.soundFacade = soundFacade;
		points = new LinkedList(); // TODO need an efficient custom circular buffer implementation
	}

	public void addPoint(float x, float y) {

		if ( points.size() > MAX_SIZE ) {
			points.remove();
			soundFacade.playCling();
		}

		Point point = new Point(x, y);
		points.add(point);

		// TODO need some worker thread to produce the heat map here

	}

	public Collection<Point> getHeatMap() {
		return points;
	}
}
