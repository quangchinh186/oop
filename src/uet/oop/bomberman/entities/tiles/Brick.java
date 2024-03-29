package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.ChadItem;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;
import view.GameViewManager;

public class Brick extends Entity {
    private boolean exploded;
    private boolean added;
    private String underneath;

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
        added = false;
    }

    public Brick (int x, int y, Image img, String under) {
        super(x, y, img);
        this.underneath = under;
        exploded = false;
    }


    @Override
    public void update() {
        if(exploded && !added) {
            this.timer++;
            this.img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, timer, 100).getFxImage();
            switch (underneath){
                case "portal":
                    Item p = new Portal(x, y, Sprite.portal.getFxImage());
                    GameViewManager.items.add(p);
                    break;
                case "flame":
                    Item f = new ChadItem(x, y, Sprite.powerup_flames.getFxImage());
                    GameViewManager.items.add(f);
                    break;
                case "bomb":
                    Item b = new BombItem(x, y, Sprite.powerup_bombs.getFxImage());
                    GameViewManager.items.add(b);
                    break;
                case "speed":
                    Item s = new SpeedItem(x, y, Sprite.powerup_speed.getFxImage());
                    GameViewManager.items.add(s);
                    break;
                case "chad":
                    Item c = new ChadItem(x, y, Sprite.powerup_flamepass.getFxImage());
                    GameViewManager.items.add(c);
                    break;
                default: break;
            }
            added = true;
        }
    }
}