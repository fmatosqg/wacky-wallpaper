package com.fmatos.crazywallpapers.wallpaper.service;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class CrazyWallpaperService extends WallpaperService {

	private static final String TAG = CrazyWallpaperService.class.getSimpleName();
	private CrazyWallpaperEngine engine;


	@Override
	public Engine onCreateEngine() {
		engine = new CrazyWallpaperEngine();
		return engine;
	}

	private class CrazyWallpaperEngine extends Engine {

		private final HeatManager heatManager;
		private final Paint paint;

		public CrazyWallpaperEngine() {
			Log.i(TAG, "bla");
			heatManager = new HeatManager();

			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.FILL);
//			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(20);

			paint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL));

		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			draw();
		}

		@Override
		public void onTouchEvent(MotionEvent event) {

			super.onTouchEvent(event);

			if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
				Log.i(TAG, "Down at " + event.getX() + "," + event.getY());
				heatManager.addPoint(event.getX(), event.getY());

				draw(); // TODO need efficient worker thread here
			}
		}

		private void draw() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = holder.lockCanvas();

			try {
				canvas.drawColor(Color.BLACK);

				for ( Point point : heatManager.getHeatMap() ) {
					canvas.drawCircle(point.getX(), point.getY(), 20.0f, paint);
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
		}

	}
}
