package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.bomberman;


public class BombPowerUp extends Item {

    public BombPowerUp(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        if(!isActive) {
            bomberman.increaseBombRange();
        }
        super.update();

    }
}

