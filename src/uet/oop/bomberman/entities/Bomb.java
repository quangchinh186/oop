package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private List<Flames> flames = new ArrayList<>();
    private Sprite s1, s2, s3;
    public static int power = 1;
    Sound boom;
    public String whom;

    public static void increasePower() {
        power++;
    }

    public Bomb(int x, int y, Image img, String whom) {
        super(x, y, img);
        this.time = 150;
        this.s1 = Sprite.bomb;
        this.s2 = Sprite.bomb_1;
        this.s3 = Sprite.bomb_2;
        this.whom = whom;
        boom = new Sound("res/sfx/explosion.wav");
    }

    public Bomb(int x, int y, String whom){
        super(x, y, null);
        this.whom = whom;
        boom = new Sound("res/sfx/explosion.wav");
    }
    public List getVisual(){
        return this.flames;
    }
    private int time;

    public int getTime() {
        return time;
    }

    @Override
    public void update() {
        if(this.time == 20){
            explode();
            this.s1 = Sprite.bomb_exploded;
            this.s2 = Sprite.bomb_exploded1;
            this.s3 = Sprite.bomb_exploded2;
        }
        else{
            this.img = Sprite.movingSprite(s1, s2, s3, time, Sprite.DEFAULT_SIZE).getFxImage();
        }
        this.time--;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void explode(){
        boom.play();
        this.img = Sprite.bomb_exploded1.getFxImage();
        int width_lim = GameMap.WIDTH-1;
        int height_lim = GameMap.HEIGHT-1;
        destroy(x, y);
        //up
        for(int i = y-1; i >= Math.max(y-power, 0); i--){
            Flames f = new Flames(x, i, Sprite.explosion_vertical.getFxImage(), State.UP);
            if(i == Math.max(y-power, 0) || GameMap.map.get(i+1).charAt(x) == '#'){
                f.setLast(true);
            }
            f.setVertical(true);
            BombermanGame.visualEffects.add(f);
            flames.add(f);
            destroy(x, i);
            if(!(BombermanGame.stillObjects.get(i*31 + x) instanceof Grass)){
                break;
            }
        }
        //down
        for (int i = y + 1; i < Math.min(y + power + 1, height_lim); i++) {
            Flames f = new Flames(x, i, Sprite.explosion_vertical.getFxImage(), State.DOWN);
            if (i == Math.min(y + power, height_lim) || GameMap.map.get(i + 1).charAt(x) == '#') {
                f.setLast(true);
            }
            f.setVertical(true);
            BombermanGame.visualEffects.add(f);
            flames.add(f);
            destroy(x, i);
            if (!(BombermanGame.stillObjects.get(i * 31 + x) instanceof Grass)) {
                break;
            }
        }
            //left
            for (int i = x - 1; i >= Math.max(x - power, 0); i--) {
                Flames f = new Flames(i, y, Sprite.explosion_horizontal.getFxImage(), State.LEFT);
                if (i == Math.max(x - power, 0) || GameMap.map.get(y).charAt(i - 1) == '#') {
                    f.setLast(true);
                }
                f.setHorizontal(true);
                BombermanGame.visualEffects.add(f);
                flames.add(f);
                destroy(i, y);
                if (!(BombermanGame.stillObjects.get(y * 31 + i) instanceof Grass)) {
                    break;
                }
            }
            //right
            for (int i = x + 1; i < Math.min(x + power + 1, width_lim); i++) {
                Flames f = new Flames(i, y, Sprite.explosion_horizontal.getFxImage(), State.RIGHT);
                if (i == Math.min(x + power, width_lim) || GameMap.map.get(y).charAt(i + 1) == '#') {
                    f.setLast(true);
                }
                f.setHorizontal(true);
                BombermanGame.visualEffects.add(f);
                flames.add(f);
                destroy(i, y);
                if (!(BombermanGame.stillObjects.get(y * 31 + i) instanceof Grass)) {
                    break;
                }
            }
            BombermanGame.visualEffects.addAll(flames);
    }




    public void destroy(int _x, int _y) {
        if(GameMap.map.get(_y).charAt(_x) == '*'){
            Brick temp = (Brick) BombermanGame.stillObjects.get(_y*31 + _x);
            temp.setExploded(true);
        }
        if(BombermanGame.bomberman.x == _x && BombermanGame.bomberman.y == _y){
            BombermanGame.bomberman.die();
        }
        for (Entity t : BombermanGame.entities)
        {
            if(t.x == _x && t.y == _y){
                t.die();
            }
        }
    }

    public boolean equals(Object obj){
        if(obj instanceof Bomb){
            Bomb b = (Bomb) obj;
            if(this.x == b.x && this.y == b.y){
                return true;
            }
        }
        return false;
    }

}