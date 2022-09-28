package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.map.GameMap;

public class Flames extends Entity {
    public boolean horizontal = false;
    public boolean vertical = false;

    public Flames(int x, int y, Image img){
        super(x, y, img);
        if(GameMap.map.get(y).charAt(x) == '#') this.img = null;
        if(GameMap.map.get(y).charAt(x) == '*'){
            System.out.println("bum");
        }
    }

    @Override
    public void update() {


    }
}
