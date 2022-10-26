package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.bomberman;



public class BombItem extends Item {

    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doEffect() {
        bomberman.setBombNumbers(bomberman.getBombNumbers()+1);
    }
}

