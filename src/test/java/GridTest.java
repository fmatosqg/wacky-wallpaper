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

	@Test
	public void whenAddPointToUnsaturatedAreaThenReturnYellow() {
		Grid grid = new Grid();
		grid.onSurfaceChanged(100, 100);

		grid.addPoint(0, 0);
		grid.addPoint(0, 0);
		grid.addPoint(0, 0);
		grid.addPoint(0, 0);
		grid.addPoint(0, 0);

		grid.addPoint(99, 99);

		Queue<Point> points = grid.getPoints();

		assertEquals(6, points.size());
		assertEquals(Color.YELLOW, points.remove().getColor());
		assertEquals(Color.YELLOW, points.remove().getColor());
		assertEquals(Color.CYAN, points.remove().getColor());
		assertEquals(Color.CYAN, points.remove().getColor());
		assertEquals(Color.CYAN, points.remove().getColor());

		assertEquals(Color.YELLOW, points.remove().getColor());
	}


	@Test
	public void colorAtEdgeTest() {
		Grid grid = new Grid();
		int max = 99;
		grid.onSurfaceChanged(max + 1, max + 1);

		assertEquals(1, grid.addPoint(max, max));
		assertEquals(2, grid.addPoint(max, max));

		assertEquals(3, grid.addPoint(max, max));
		assertEquals(4, grid.addPoint(max, max));
		assertEquals(5, grid.addPoint(max, max));


		assertEquals(5, grid.getPoints().size());
	}

	@Test (expected = Grid.GridEdgeOverflowException.class)
	public void colorBeyondEdgeTest() {
		Grid grid = new Grid();
		int max = 99;
		grid.onSurfaceChanged(max - 1, max - 1);
		assertEquals(1, grid.addPoint(max, max));
	}

}
