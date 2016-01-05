import android.graphics.Color;

import com.fmatos.crazywallpapers.wallpaper.domain.Grid;
import com.fmatos.crazywallpapers.wallpaper.domain.Point;

import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Created by fdematos on 01/01/16.
 */

public class GridTest {

	@Test
	public void firstPointColor() {
		Grid grid = new Grid();
		grid.addPoint(0, 0);

		Queue<Point> points = grid.getPoints();

		assertEquals(1, points.size());
		assertEquals(Color.YELLOW, points.remove().getColor());
	}

	@Test
	public void fifthPointColorThenReturnBlue() {
		Grid grid = new Grid();
		grid.addPoint(0, 0);
		grid.addPoint(0, 0);
		grid.addPoint(0, 0);
		grid.addPoint(0, 0);
		grid.addPoint(0, 0);

		Queue<Point> points = grid.getPoints();

		assertEquals(5, points.size());
		assertEquals(Color.YELLOW, points.remove().getColor());
		assertEquals(Color.YELLOW, points.remove().getColor());
		assertEquals(Color.CYAN, points.remove().getColor());
		assertEquals(Color.CYAN, points.remove().getColor());
		assertEquals(Color.CYAN, points.remove().getColor());
	}
}
