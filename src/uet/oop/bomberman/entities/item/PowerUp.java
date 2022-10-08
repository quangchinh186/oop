package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class PowerUp extends Item {
    public PowerUp(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doEffect() {
        super.doEffect();
        setInactive();
    }
}
