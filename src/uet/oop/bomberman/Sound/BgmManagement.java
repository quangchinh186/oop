package uet.oop.bomberman.Sound;

import uet.oop.bomberman.Sound.*;
import java.io.File;
import java.util.ArrayList;

public class BgmManagement {
    private ArrayList<String> songs = new ArrayList<>();
    public int nowPlaying;
    private uet.oop.bomberman.sound.Sound music;
    private boolean isStop;
    private int stopTime = 0;

    public BgmManagement(String path){
        File folder = new File(path);
        File[] list = folder.listFiles();
        for (int i = 0; i < list.length; i++){
            songs.add(list[i].getPath());
        }
        nowPlaying = 0;
    }

    public void resume(){
        music.play(stopTime);
    }
    public String getNow(){
        return songs.get(nowPlaying).substring(10);
    }

    public void play(){
        if(music == null) {
            music = new uet.oop.bomberman.sound.Sound(songs.get(nowPlaying));
        }
        if(isStop){
            resume();
            return;
        }
        music.play();
    }

    public void pause(){
        isStop = true;
        stopTime = music.getTime();
        music.stop();
    }

    public void next(){
        music.stop();
        nowPlaying = nowPlaying == songs.size() ? 0 : nowPlaying+1;
        music = new uet.oop.bomberman.sound.Sound(songs.get(nowPlaying));
        music.play();
    }

    public void prev(){
        music.stop();
        nowPlaying = nowPlaying == 0 ? songs.size()-1 : nowPlaying-1;
        music = new uet.oop.bomberman.sound.Sound(songs.get(nowPlaying));
        music.play();
    }

    public void changeVolume(String func){
        if(func.equals("up")){
            music.volumeUp();
        } else {
            music.volumeDown();
        }
    }

    public void autoMove(){
        if(music == null) return;
        if(music.isEnd()){
            next();
        }
    }

}
