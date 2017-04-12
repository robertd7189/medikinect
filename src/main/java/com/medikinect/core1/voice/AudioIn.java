package com.medikinect.core1.voice;

import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class AudioIn {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		

		
			//
			 // open the sound file as a Java input stream
			    String gongFile = "/Users/robertdooley/Downloads/Are you ok.wav";
			    InputStream in = new FileInputStream(gongFile);

			    // create an audiostream from the inputstream
			    AudioStream audioStream = new AudioStream(in);

			    // play the audio clip with the audioplayer class
			    AudioPlayer.player.start(audioStream);
			    
			    
//			    figure out how to get line in and save file
			    final JavaSoundRecorder recorder = new JavaSoundRecorder();
	}

}
