package com.fmatos.crazywallpapers.view;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fmatos.crazywallpapers.R;
import com.fmatos.crazywallpapers.wallpaper.service.CrazyWallpaperService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		ButterKnife.inject(this);
	}

	@OnClick(R.id.btnPreview)
	public void setWallpaper() {

		Intent intent = new Intent();
		intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
		String pkg = getPackageName();
		String cls = CrazyWallpaperService.class.getCanonicalName();
		intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(pkg, cls));
		startActivityForResult(intent, 0);

		Toast.makeText(this, "Amazing Wallpaper was successfully configured", Toast.LENGTH_LONG).show();
	}

}
