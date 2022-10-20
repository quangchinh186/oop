package uet.oop.bomberman.entities.item.powerup;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.bomberman;



public class BombPowerUp extends PowerUp {

    public BombPowerUp(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doEffect() {
        super.doEffect();
        bomberman.increaseBombRange();
        System.out.println("BOMB TO");
    }
}

