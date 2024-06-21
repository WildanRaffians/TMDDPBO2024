package viewmodel;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Wildan Hafizh Raffianshar
 */
public class Sound {
    public Clip playSound(Clip clip, String filename){     
        // konstruktor
        try {
            // mengambil audio input
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("assets/" + filename).getAbsoluteFile());
            // inisialisasi clip
            clip = AudioSystem.getClip();
            // membuka clip audio dari audio yang ingin diputar
            clip.open(audioIn);
            clip.start(); // mulai audio
            clip.loop(Clip.LOOP_CONTINUOUSLY); // loop audio
            
        } catch (UnsupportedAudioFileException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        } catch (LineUnavailableException e) {
           e.printStackTrace();
        }
        return clip;
    }
    
    public void stopSound(Clip clip){
        // untuk menghentikan audio
        clip.stop();
    }
}
