package com.fmatos.crazywallpapers.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.fmatos.crazywallpapers.R;

/**
 * Created by fdematos on 29/12/15.
 */
public class SoundEngine {

	private static final String TAG = SoundEngine.class.getSimpleName();

	private SoundPool mSoundPool;
	private AudioManager mAudioManager;
	private int mSoundId;
	private boolean mCanPlayAudio;

	private final Context context;

	public SoundEngine(Context context) {
		this.context = context;

		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		// Request audio focus
		int result = mAudioManager.requestAudioFocus(afChangeListener,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

		// Set to true if app has audio foucs
		mCanPlayAudio = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;

	}


	private void loadSoundPool() {
		// Create a SoundPool
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

		// Load bubble popping sound into the SoundPool
		mSoundId = mSoundPool.load(context, R.raw.cling_01_321285, 1);

		// Set an OnLoadCompleteListener on the SoundPool
		mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
									   int status) {

				// If sound loading was successful enable the play Button
				if (0 == status) {
					Log.d(TAG, "Sound pool is cool 1");
				} else {
					Log.i(TAG, "Unable to load sound");
					Log.d(TAG, "Sound pool is broken 1");

				}
			}
		});

	}

	public void play() {

		Thread t = new Thread(){
			@Override
			public void run() {
				if (mCanPlayAudio) {
					if (mSoundPool != null ) {
						float volume = 0.15f;
						mSoundPool.play(mSoundId, volume,volume,
								1, 0, 1.0f);
					} else {
						Log.w(TAG,"NULLLLLLL");
					}
				}
			}
		};

		t.start();


	}

	// Listen for Audio focus changes
	AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
		@Override
		public void onAudioFocusChange(int focusChange) {

			if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
				mAudioManager.abandonAudioFocus(afChangeListener);
				mCanPlayAudio = false;
				Log.d(TAG,"Audio focus loss");
			} else {
				Log.d(TAG,"Audio focus != loss " + focusChange);
			}

		}
	};

	public void onResume() {

		mAudioManager.setSpeakerphoneOn(true);
		mAudioManager.loadSoundEffects();

		loadSoundPool();
	}

	// Release resources & clean up
	public void onPause() {

		if (null != mSoundPool) {
			mSoundPool.unload(mSoundId);
			mSoundPool.release();
			mSoundPool = null;

			Log.d(TAG,"Sound pool release");
		}

		mAudioManager.setSpeakerphoneOn(false);
		mAudioManager.unloadSoundEffects();

		Log.d(TAG, "Sound pool pause unloaded");
	}

}
