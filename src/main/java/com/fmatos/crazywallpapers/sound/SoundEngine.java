package com.fmatos.crazywallpapers.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by fdematos on 29/12/15.
 */
public class SoundEngine {

	private static final String TAG = SoundEngine.class.getSimpleName();
	private static final int MAX_STREAMS = 3;

	private SoundPool mSoundPool;
	private AudioManager mAudioManager;

	private boolean mCanPlayAudio;

	AudioManager.OnAudioFocusChangeListener afChangeListener;
	private final WeakReference<Context> context;

	public SoundEngine(Context context) {

		this.context = new WeakReference<Context>(context);

		afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
			@Override
			public void onAudioFocusChange(int focusChange) {

				if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
					mAudioManager.abandonAudioFocus(afChangeListener);
					mCanPlayAudio = false;
					Log.d(TAG,"Audio focus loss");
				} else if ( focusChange == AudioManager.AUDIOFOCUS_GAIN){
					mCanPlayAudio = true;
					Log.d(TAG,"Audio focus gain " + focusChange);
				}

			}
		};

		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		// Request audio focus
		int result = mAudioManager.requestAudioFocus(afChangeListener,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

		// Set to true if app has audio foucs
		mCanPlayAudio = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;

	}

	private void loadSoundPool() {
		// Create a SoundPool
		mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);

		// Set an OnLoadCompleteListener on the SoundPool
		mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
									   int status) {

				if (0 != status) {
					Log.i(TAG, "Unable to load sound");
				}
			}
		});

	}

	public void play(final int soundId) {

		Thread t = new Thread(){
			@Override
			public void run() {
				if (mCanPlayAudio) {
					if (mSoundPool != null ) {
						float volume = 0.15f;
						mSoundPool.play(soundId, volume,volume,
								1, 0, 1.0f);
					} else {
						Log.w(TAG,"Sound pool is null");
					}
				}
			}
		};

		t.start();


	}

	/**
	 * Acquire resources
	 */
	public void onResume() {

		mAudioManager.setSpeakerphoneOn(true);
		mAudioManager.loadSoundEffects();

		loadSoundPool();
	}

	/**
	 * Acquire resources
	 */
	public void onPause() {

		if (null != mSoundPool) {

			mSoundPool.release();
			mSoundPool = null;

			Log.d(TAG,"Sound pool release");
		}

		mAudioManager.setSpeakerphoneOn(false);
		mAudioManager.unloadSoundEffects();

		Log.d(TAG, "Sound pool pause unloaded");
	}

	public SoundPool getSoundPool() {
		return mSoundPool;
	}

}
