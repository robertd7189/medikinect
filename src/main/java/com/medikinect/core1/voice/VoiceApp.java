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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JavaSoundRecorder()
{
	
}

public class VoiceApp {
  public static void main(String... args) throws Exception {
    // Instantiates a client
    SpeechClient speech = SpeechClient.create();
//
//    figure out how to get line in and save file
    
    
    
    // The path to the audio file to transcribe
    String fileName = "/Users/robertdooley/Downloads/helppush.wav";

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
    	  System.out.print("T: "+alternative.getTranscript());
    	  System.out.print("C: "+alternative.getConfidence());
    	  
    	  if (alternative.getTranscript().contains("help")){
    		  System.out.print("help worked!");
    	  }
    	  
    	  if (alternative.getConfidence() > 0.5){
    		 System.out.print("above 50% confidence");
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