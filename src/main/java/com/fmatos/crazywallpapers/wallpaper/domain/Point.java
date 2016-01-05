package com.fmatos.crazywallpapers.wallpaper.domain;

/**
 * Created by fdematos on 29/12/15.
 */
public class Point {

	private final float x;
	private final float y;
	private final int color;

	public Point(float x, float y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getColor() {
		return color;
	}
}
