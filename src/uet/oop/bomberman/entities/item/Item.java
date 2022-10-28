package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import view.GameViewManager;


public class Item extends Entity {

    protected boolean isActive = true;
    public Item(int x, int y, Image img) {
        super(x, y, img);
        rect.setX(x* Sprite.SCALED_SIZE);
        rect.setY(y* Sprite.SCALED_SIZE);
        rect.setWidth(30);
        rect.setHeight(30);
    }

    @Override
    public void update() {
        if(!isActive) {
            //clear khoi list item.
            GameViewManager.items.remove(this);
        }
    }

    public void doEffect() {

    }


}