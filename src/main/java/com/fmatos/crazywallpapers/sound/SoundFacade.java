package com.fmatos.crazywallpapers.sound;

import android.content.Context;
import android.support.annotation.RawRes;

import com.fmatos.crazywallpapers.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fdematos on 29/12/15.
 */
public class SoundFacade {

	private final SoundEngine soundEngine;
	private final WeakReference<Context> context;

	private final List<Integer> clingSounds;

	public SoundFacade(Context context) {
		soundEngine = new SoundEngine(context);
		this.context = new WeakReference<Context>(context);
		clingSounds = new ArrayList<Integer>(); // cheap access to random order
	}

	public void onResume() {
		soundEngine.onResume();
		loadSoundPool();
	}

	public void onPause() {

		unloadSoundPool();

		soundEngine.onPause();
	}

	private void unloadSoundPool() {
		if (soundEngine.getmSoundPool() != null) {
//			soundEngine.getmSoundPool().unload(mSoundId);
		}
	}

	private void loadSoundPool() {

		clingSounds.add(loadSoundResource(R.raw.cling_01_321285));
		clingSounds.add(loadSoundResource(R.raw.cling_02_321284));
		clingSounds.add(loadSoundResource(R.raw.cling_03_321283));
		clingSounds.add(loadSoundResource(R.raw.cling_04_321288));
		clingSounds.add(loadSoundResource(R.raw.cling_05_321287));
		clingSounds.add(loadSoundResource(R.raw.cling_06_321290));

	}

	private Integer loadSoundResource(@RawRes int audioResourceId) {
		return soundEngine.getmSoundPool().load(context.get(), audioResourceId, 1);
	}

	public void playCling() {

		int index = (int) (Math.random() * clingSounds.size());

		int mSoundId = clingSounds.get(index);
		soundEngine.play(mSoundId);
	}
}
