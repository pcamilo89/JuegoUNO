package test;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Camilo
 */
public class TestSoundEffect {

    static String path = "C:/Users/Camilo/Documents/Programing/Java/JuegoUNO/resources/FFVictory.wav";
    static String path2 = "resources/FFVictory.wav";
    
    public static void playSound(String path) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            @Override
            public void run() {
                File audioFile = new File(path);
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                    AudioFormat format = audioStream.getFormat();

                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

                    SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

                    audioLine.open(format);

                    audioLine.start();

                    System.out.println("Playback started.");

                    byte[] bytesBuffer = new byte[1024];
                    int bytesRead = -1;

                    while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                        audioLine.write(bytesBuffer, 0, bytesRead);
                    }

                    audioLine.drain();
                    audioLine.close();
                    audioStream.close();

                    System.out.println("Playback completed.");

                } catch (UnsupportedAudioFileException ex) {
                    System.out.println("The specified audio file is not supported.");
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    System.out.println("Audio line for playing back is unavailable.");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    System.out.println("Error playing the audio file.");
                    ex.printStackTrace();
                }
            }

        }).start();
    }
    
    public static void main(String[] args) {
        playSound(path2);
        System.out.println("END OF MAIN");
    }
}
