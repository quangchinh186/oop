package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.stillobjects.Brick;
import uet.oop.bomberman.entities.stillobjects.Grass;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;
import view.GameViewManager;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private List<Flames> flames = new ArrayList<>();
    private Sprite s1, s2, s3;
    public static int power = 1;

    public static void increasePower() {
        power++;
    }

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        this.time = 100;
        s1 = Sprite.bomb;
        s2 = Sprite.bomb_1;
        s3 = Sprite.bomb_2;
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
        if(this.time == 50){
            explode();
            s1 = Sprite.bomb_exploded;
            s2 = Sprite.bomb_exploded1;
            s3 = Sprite.bomb_exploded2;
        }
        else{
            this.img = Sprite.movingSprite(s1, s2, s3, this.timer - time, 50).getFxImage();
        }
        this.time--;
    }

    public void explode() {

            int width_lim = GameMap.WIDTH - 1;
            int height_lim = GameMap.HEIGHT - 1;
            //up
            for (int i = y - 1; i >= Math.max(y - power, 0); i--) {
                Flames f = new Flames(x, i, Sprite.explosion_vertical.getFxImage(), State.UP);
                if (i == Math.max(y - power, 0) || GameMap.map.get(i + 1).charAt(x) == '#') {
                    f.setLast(true);
                }
                f.setVertical(true);
                GameViewManager.visualEffects.add(f);
                flames.add(f);
                destroy(x, i);
                if (!(GameViewManager.stillObjects.get(i * 31 + x) instanceof Grass)) {
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
                GameViewManager.visualEffects.add(f);
                flames.add(f);
                destroy(x, i);
                if (!(GameViewManager.stillObjects.get(i * 31 + x) instanceof Grass)) {
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
                GameViewManager.visualEffects.add(f);
                flames.add(f);
                destroy(i, y);
                if (!(GameViewManager.stillObjects.get(y * 31 + i) instanceof Grass)) {
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
                GameViewManager.visualEffects.add(f);
                flames.add(f);
                destroy(i, y);
                if (!(GameViewManager.stillObjects.get(y * 31 + i) instanceof Grass)) {
                    break;
                }
            }
        GameViewManager.visualEffects.addAll(flames);
    }



    public void destroy(int _x, int _y) {
        switch (GameMap.map.get(_y).charAt(_x)){
            case '*' :
                Brick temp = (Brick) GameViewManager.stillObjects.get(_y*31 + _x);
                temp.setExploded(true);
                break;

            default:
                break;
        }
    }

}