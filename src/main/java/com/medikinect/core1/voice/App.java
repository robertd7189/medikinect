package com.medikinect.core1.voice;




public class App 
{
	static final long RECORD_TIME = 3000;  // 3 seconds
    public static void main( String[] args )
    {
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
    }
}
