package uet.oop.bomberman.sound;

import java.io.File;
import java.util.ArrayList;

public class BgmManagement {
    private ArrayList<String> songs = new ArrayList<>();
    public int nowPlaying;
    private Sound music;

    public BgmManagement(String path){
        File folder = new File(path);
        File[] list = folder.listFiles();
        for (int i = 0; i < list.length; i++){
            songs.add(list[i].getPath());
        }
        nowPlaying = 0;
    }

    public String getNow(){
        return songs.get(nowPlaying).substring(10);
    }

    public void play(){
        if(music == null) {
            music = new Sound(songs.get(nowPlaying));
        }
        music.play();
    }

    public void pause(){
        music.stop();
    }

    public void next(){
        music.stop();
        nowPlaying = nowPlaying == songs.size() ? 0 : nowPlaying+1;
        music = new Sound(songs.get(nowPlaying));
        music.play();
    }

    public void prev(){
        music.stop();
        nowPlaying = nowPlaying == 0 ? songs.size()-1 : nowPlaying-1;
        music = new Sound(songs.get(nowPlaying));
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
