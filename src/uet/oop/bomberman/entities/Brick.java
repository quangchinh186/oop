package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity{
    private boolean exploded;

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public Brick (int x, int y, Image img) {
        super(x, y, img);
        exploded = false;
    }


    @Override
    public void update() {
        if(exploded) {
            this.timer++;
            this.img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, timer, 100).getFxImage();
        }

    }
}
