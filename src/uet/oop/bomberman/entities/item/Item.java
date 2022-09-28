package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.entities.Entity;

public class Item extends Entity {

    public Item(int x, int y, Image img) {
        super( x + 1, y, img);
        rect.setWidth(30);
        rect.setHeight(30);
    }

    @Override
    public void update() {

    }

}
