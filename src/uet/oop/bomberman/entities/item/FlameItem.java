package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.bomberman;


public class FlameItem extends Item {

    public FlameItem(int x, int y, Image img) {
            super(x, y, img);
    }
    @Override
    public void update() {
        super.update();
    }

    public void doEffect() {
        System.out.println("CHADIFY");
        bomberman.becomeChad();
    }
}

