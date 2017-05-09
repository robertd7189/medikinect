package com.medikinect.core1.voice;

// Imports the Google Cloud client library
import com.google.cloud.speech.spi.v1beta1.SpeechClient;
import com.google.cloud.speech.v1beta1.RecognitionAudio;
import com.google.cloud.speech.v1beta1.RecognitionConfig;
import com.google.cloud.speech.v1beta1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1beta1.SpeechRecognitionResult;
import com.google.cloud.speech.v1beta1.SyncRecognizeResponse;
import com.google.protobuf.ByteString;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("restriction")
public class VoiceApp {
	
	
	static final long RECORD_TIME = 4000;  // 4 seconds
  public static void main(String... args) throws Exception {
    // Instantiates a client
    SpeechClient speech = SpeechClient.create();
//
    //open the sound file as a Java input stream
    String gongFile = "/Users/robertdooley/Downloads/Are you ok.wav";
    InputStream in = new FileInputStream(gongFile);

    // create an audiostream from the inputstream
    AudioStream audioStream = new AudioStream(in);

    // play the audio clip with the audioplayer class
    AudioPlayer.player.start(audioStream);
    
    TimeUnit.MILLISECONDS.sleep(4000);
    
    
//    figure out how to get line in and save file
    final JavaSoundRecorder recorder = new JavaSoundRecorder();
	 
    // creates a new thread that waits for a specified
    // of time before stopping
    Thread stopper = new Thread(new Runnable() {
        public void run() {
            try {
                Thread.sleep(RECORD_TIME);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            recorder.finish();
        }
    });

    stopper.start();

    // start recording
    recorder.start();
    
    
    // The path to the audio file to transcribe
    // /Users/robertdooley/Downloads
    String fileName = ("././././helpfilecreated.wav");
    //update

    // Reads the audio file into memory
    Path path = Paths.get(fileName);
    byte[] data = Files.readAllBytes(path);
    ByteString audioBytes = ByteString.copyFrom(data);
    
//    
//    File format= .wav ; 16,000 Hz ; PCM compression
//    
//		/Users/Downloads/helphelphelp.wav
    
    // Builds the sync recognize request
    RecognitionConfig config = RecognitionConfig.newBuilder()
        .setEncoding(AudioEncoding.LINEAR16)
        .setSampleRate(16000)
        .build();
    RecognitionAudio audio = RecognitionAudio.newBuilder()
        .setContent(audioBytes)
        .build();

    // Performs speech recognition on the audio file
    SyncRecognizeResponse response = speech.syncRecognize(config, audio);
    List<SpeechRecognitionResult> results = response.getResultsList();

    int helpCount = 0;
    
//    String[] helpKeywords = {
//    	"I need help",
//    	"help help help",
//    	"help me",
//    	"help needed",
//    	"help help"
//    };
    
    for (SpeechRecognitionResult result: results) {
    	
      List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
      
      for (SpeechRecognitionAlternative alternative: alternatives) {
//        System.out.printf("Transcription: %s%n", alternative.getTranscript());
//       
    	  
    	  //"If you are in need of assistance, reply with help, help help (three times).
    	  //"I need help"
    	  System.out.println("T: "+alternative.getTranscript());
    	  System.out.println("C: "+alternative.getConfidence());
    	  
    	  
    	  if ((alternative.getTranscript().contains("help")) && (alternative.getConfidence() > 0.7) && !alternative.getTranscript().contains("ok")){
    		  
    		  System.out.print("help needed!");
    	  }
    	  if ((alternative.getTranscript().contains("ok")) && (alternative.getConfidence() > 0.7))
    	  {
    		  System.out.println("Everything is OK");
    	  }
    	  
    		  
    		  
    	  
    	  
//    	  System.out.print(alternative);
    	  
//        if (alternative.getTranscript().equalsIgnoreCase("help"))
//        {
//        	helpCount++;
//        	System.out.println(helpCount);
//        }
      }
      
    }
    
    if(helpCount == 3){
    	System.out.println("Get help");
    }
    
    speech.close();
  }
}