package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;
import view.GameViewManager;


import java.util.TimerTask;

public class Brick extends Entity {
    private boolean exploded;
    private boolean hasItem;

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
        jTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameMap.updateMap(x, y);
            }
        },1000);
    }

    public Brick (int x, int y, Image img, boolean hasItem) {
        super(x, y, img);
        this.hasItem = hasItem;
        exploded = false;
    }


    @Override
    public void update() {
        if(exploded) {
            this.timer++;
            this.img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, timer, 100).getFxImage();
            if(hasItem){
                Item p = new Portal(x, y, Sprite.portal.getFxImage());
                GameViewManager.items.add(p);
            }

            /**
             * jTimer.schedule(new TimerTask() {
             *                 @Override
             *                 public void run() {
             *                     setInactive();
             *                 }
             *             }, 300);
             */

        }
    }
}
