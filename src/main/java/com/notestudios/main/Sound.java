package com.notestudios.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.*;

public class Sound {
	
	public static class Clips {
		public Clip[] clips;
		private int p;
		private int count;
		private AudioFormat audioFormat;
		
		public Clips(byte[] buffer, int count)
				throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if (buffer == null)
				return;
			
			audioFormat = getAudioFormat(buffer);
			clips = new Clip[count];
			this.count = count;

			for (int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}
		
		private AudioFormat getAudioFormat(byte[] buffer) throws UnsupportedAudioFileException, IOException {
			try(ByteArrayInputStream bais = new ByteArrayInputStream(buffer)) {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bais); 
					return audioInputStream.getFormat();
			}
		}
		
		public AudioFormat getAudioFormat() {
			if(audioFormat != null)
				return audioFormat;
			
			return null;
		}

		public void play() {
			if (clips == null)
				return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if (p >= count)
				p = 0;
			
		}

		public void stop() {
			if (clips == null)
				return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].stop();
			p--;
			if (p <= count)
				p = 0;
		}
		
		public void pause() {
			if(clips == null) {
				return;
			}
			clips[p].stop();
		}
		
		public void loop() {
			if (clips == null)
				return;
			clips[p].loop(300);
		}
		
		public void setVolume(float volume) {
		    if (clips == null) {
		        return;
		    } for (int i = 0; i < count; i++) {
		    	if(volume <= 2f) {
			        FloatControl gainControl = (FloatControl) clips[i].getControl(FloatControl.Type.MASTER_GAIN);
			        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
			        gainControl.setValue(dB);
		    	} else {
		    		volume = 1f;
		    	}
		    }
		}

	}
	

	public static Clips load(String path, int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(path));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = dis.read(buffer)) >= 0) {
				baos.write(buffer, 0, read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data, count);
		} catch (Exception e) {
			System.err.println("Unable to load '"+path.substring(0, 50)+"...' -> Clips.load()");
			System.out.println("The path '"+path+"' can be wrong, or the file doesn't exists.");
			try {
				return new Clips(null, 0);
			} catch (Exception ee) {
				return null;
			}
		}
	}

}