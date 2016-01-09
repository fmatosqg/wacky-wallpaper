package com.fmatos.crazywallpapers.wallpaper.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by fdematos on 09/01/16.
 */
public class ScreenTest {

	private Screen screen;

	@Before
	public void setup () {
		screen = new Screen();
		screen.onSurfaceChanged(100,200);
	}


	@Test
	public void testOrientation() {
		assertEquals(Orientation.PORTRAIT,screen.getOrientation());
		screen.onSurfaceChanged(200, 100);
		assertEquals(Orientation.LANDSCAPE, screen.getOrientation());
	}

	@Test
	public void whenOrientationChangePointsAreSaved() {

		screen.addPoint(0,0);
		assertEquals(1,screen.getPoints().size());

		screen.onSurfaceChanged(200, 100);
		assertEquals(0,screen.getPoints().size());

		screen.onSurfaceChanged(100, 200);
		assertEquals(1,screen.getPoints().size());

	}


	@Test
	public void whenOrientationChangePointsAreSaved2() {

		assertEquals(0, screen.getPoints().size());

		screen.onSurfaceChanged(200, 100);
		screen.addPoint(0, 0);
		assertEquals(1,screen.getPoints().size());

		screen.onSurfaceChanged(100, 200);
		assertEquals(0,screen.getPoints().size());

	}

	@Test(expected = RuntimeException.class)
	public void whenUninitializedThrow() {
		Screen another = new Screen();

		another.getPoints();
	}
}