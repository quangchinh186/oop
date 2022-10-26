package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

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
                    BombermanGame.items.add(p);
                    break;
                case "flame":
                    Item f = new FlameItem(x, y, Sprite.powerup_flames.getFxImage());
                    BombermanGame.items.add(f);
                    break;
                case "bomb":
                    Item b = new BombItem(x, y, Sprite.powerup_bombs.getFxImage());
                    BombermanGame.items.add(b);
                    break;
                case "speed":
                    Item s = new SpeedItem(x, y, Sprite.powerup_speed.getFxImage());
                    BombermanGame.items.add(s);
                    break;
                default: break;
            }
            added = true;
        }
    }
}
