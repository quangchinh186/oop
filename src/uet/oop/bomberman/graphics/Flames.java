package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.map.GameMap;

public class Flames extends Entity {
    public boolean horizontal = false;
    public boolean vertical = false;

    private Sprite s1, s2, s3;
    public Flames(int x, int y, Image img){
        super(x, y, img);
        this.x = x;
        this.y = y;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        s1 = Sprite.explosion_horizontal;
        s2 = Sprite.explosion_horizontal1;
        s3 = Sprite.explosion_horizontal2;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        s1 = Sprite.explosion_vertical;
        s2 = Sprite.explosion_vertical1;
        s3 = Sprite.explosion_vertical2;
    }

    @Override
    public void update() {
        this.img = Sprite.movingSprite(s1, s2, s3, Bomb.timer,50).getFxImage();

        if(GameMap.map.get(this.y).charAt(this.x) == '#') this.img = null;
        if(GameMap.map.get(this.y).charAt(this.x) == '*'){
            GameMap.updateMap(this.x, this.y);
        }

    }
}
