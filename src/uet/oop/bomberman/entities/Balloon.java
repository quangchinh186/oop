package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Entity {

    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(this.timer > 100) this.timer = 0;
        else this.timer++;
        this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, this.timer, 100).getFxImage();
    }
}