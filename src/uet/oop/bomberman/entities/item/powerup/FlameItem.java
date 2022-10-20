package uet.oop.bomberman.entities.item.powerup;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.bomberman;


public class FlameItem extends PowerUp {

    public FlameItem(int x, int y, Image img) {
            super(x, y, img);
    }
    @Override
    public void update() {
        super.update();
    }

    public void doEffect() {
        super.doEffect();
        System.out.println("CHADIFY");
        bomberman.becomeChad();
    }
}

