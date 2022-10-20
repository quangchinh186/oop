package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.map.GameMap;

public class Flames extends Entity {
    public boolean horizontal = false;
    public State state;
    public boolean vertical = false;
    public boolean last = false;
    private int time;
    public Flames(int x, int y, Image img, State s){
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.state = s;
        this.time = 15;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        if(!last){
            this.s1 = Sprite.explosion_horizontal;
            this.s2 = Sprite.explosion_horizontal1;
            this.s3 = Sprite.explosion_horizontal2;
        }else {
            if(state == State.LEFT){
                this.s1 = Sprite.explosion_horizontal_left_last;
                this.s2 = Sprite.explosion_horizontal_left_last1;
                this.s3 = Sprite.explosion_horizontal_left_last2;
            }else{
                this.s1 = Sprite.explosion_horizontal_right_last;
                this.s2 = Sprite.explosion_horizontal_right_last1;
                this.s3 = Sprite.explosion_horizontal_right_last2;
            }
        }
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        if(!last){
            this.s1 = Sprite.explosion_vertical;
            this.s2 = Sprite.explosion_vertical1;
            this.s3 = Sprite.explosion_vertical2;
        }else {
            if(state == State.UP){
                this.s1 = Sprite.explosion_vertical_top_last;
                this.s2 = Sprite.explosion_vertical_top_last1;
                this.s3 = Sprite.explosion_vertical_top_last2;
            }else{
                this.s1 = Sprite.explosion_vertical_down_last;
                this.s2 = Sprite.explosion_vertical_down_last1;
                this.s3 = Sprite.explosion_vertical_down_last2;
            }
        }
    }

    @Override
    public void update() {
        this.time--;
        if(BombermanGame.stillObjects.get(y*31 + x) instanceof Grass){
            this.img = Sprite.movingSprite(this.s1, this.s2, this.s3, time%15,16).getFxImage();
        }else{
            this.img = null;
        }
        if(GameMap.map.get(this.y).charAt(this.x) == '*' && time == 0){
            GameMap.updateMap(this.x, this.y);
        }

    }
}
