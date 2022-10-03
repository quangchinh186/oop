package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.map.GameMap;

public class Flames extends Entity {
    public boolean horizontal = false;
    public boolean vertical = false;

    public Flames(int x, int y, Image img){
        super(x, y, img);
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        if(GameMap.map.get(this.y).charAt(this.x) == '#') this.img = null;
        if(GameMap.map.get(this.y).charAt(this.x) == '*'){
            GameMap.updateMap(this.x, this.y);
        }

    }
}
