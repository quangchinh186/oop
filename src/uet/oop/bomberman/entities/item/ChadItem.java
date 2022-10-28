package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import static view.GameViewManager.bomberman;


public class ChadItem extends Item {

    public ChadItem(int x, int y, Image img) {
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

