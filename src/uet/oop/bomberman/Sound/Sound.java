package uet.oop.bomberman.sound;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.*;

public class Sound {
    private Clip sfx;
    private FloatControl floatControl;
    float curVolume = 0;
    public Sound(String path) {
        try {
                File file = new File(path);
                AudioInputStream input = AudioSystem.getAudioInputStream(file);
                sfx = AudioSystem.getClip();
                sfx.open(input);
                floatControl = (FloatControl)sfx.getControl(FloatControl.Type.MASTER_GAIN);
                setVol(-10);
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
    public void play(int time){
        sfx.setFramePosition(time);
        sfx.start();
    }

    public void loop(){
        sfx.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        sfx.stop();
    }
    public int getTime(){
        return sfx.getFramePosition();
    }
    public boolean isEnd(){
        return sfx.getFramePosition() == sfx.getFrameLength();
    }
    public void volumeUp(){
        curVolume += 2.0f;
        if(curVolume > 6.0f){
            curVolume = 6.0f;
        }
        floatControl.setValue(curVolume);
    }
    public void volumeDown(){
        curVolume -= 2.0f;
        if(curVolume < -80.0f){
            curVolume = -80.0f;
        }
        floatControl.setValue(curVolume);
    }

    public void setVol(float val){
        floatControl.setValue(val);
    }
}