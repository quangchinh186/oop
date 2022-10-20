package uet.oop.bomberman.Sound;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private Clip sfx;
    public Sound(String path) {
        try {
                File file = new File(path);
                AudioInputStream input = AudioSystem.getAudioInputStream(file);
                sfx = AudioSystem.getClip();
                sfx.open(input);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void play(){
        sfx.setFramePosition(0);
        sfx.start();
    }
    public void loop(){
        sfx.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        sfx.stop();
    }
}