package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillobjects.Grass;
import uet.oop.bomberman.map.GameMap;
import view.GameViewManager;

import java.util.Timer;
import java.util.TimerTask;

public class Flames extends Entity {
    public boolean horizontal = false;
    public State state;
    public boolean vertical = false;
    public boolean last = false;
    private Sprite s1, s2, s3;
    private int time;

    private Timer timerF = new Timer();


    public Flames(int x, int y, Image img, State s){
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.state = s;
        this.time = 100;

        timerF.schedule(new TimerTask(){
            @Override
            public void run() {
                setInactive();
            }
        }, 500);

    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        if(!last){
            s1 = Sprite.explosion_horizontal;
            s2 = Sprite.explosion_horizontal1;
            s3 = Sprite.explosion_horizontal2;
        }else {
            if(state == State.LEFT){
                s1 = Sprite.explosion_horizontal_left_last;
                s2 = Sprite.explosion_horizontal_left_last1;
                s3 = Sprite.explosion_horizontal_left_last2;
            }else{
                s1 = Sprite.explosion_horizontal_right_last;
                s2 = Sprite.explosion_horizontal_right_last1;
                s3 = Sprite.explosion_horizontal_right_last2;
            }
        }
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        if(!last){
            s1 = Sprite.explosion_vertical;
            s2 = Sprite.explosion_vertical1;
            s3 = Sprite.explosion_vertical2;
        }else {
            if(state == State.UP){
                s1 = Sprite.explosion_vertical_top_last;
                s2 = Sprite.explosion_vertical_top_last1;
                s3 = Sprite.explosion_vertical_top_last2;
            }else{
                s1 = Sprite.explosion_vertical_down_last;
                s2 = Sprite.explosion_vertical_down_last1;
                s3 = Sprite.explosion_vertical_down_last2;
            }
        }
    }

    @Override
    public void update() {
        this.time--;
        if(GameViewManager.stillObjects.get(y*31 + x) instanceof Grass){
            this.img = Sprite.movingSprite(s1, s2, s3, this.timer - time,80).getFxImage();
        }else{
            this.img = null;
        }
        if(GameMap.map.get(this.y).charAt(this.x) == '*' && time == 10){
            GameMap.updateMap(this.x, this.y);
        }

    }
}
