package game.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	private static boolean mute = false;

	public static synchronized void play(final String fileName, double level) {
		// use .wav files
		// level (0.0 - 1.0)
		if (!mute) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Clip clip = AudioSystem.getClip();
						AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
						clip.open(inputStream);
						FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
						float dB = (float) (Math.log(level) / Math.log(10.0) * 20.0);
						volume.setValue(dB);
						clip.start();
					} catch (Exception e) {
						System.err.println("play sound error: " + e.getMessage() + " for " + fileName);
					}
				}
			}).start();
		}
	}
}
