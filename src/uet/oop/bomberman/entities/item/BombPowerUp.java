package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;

import static view.GameViewManager.bomberman;


public class BombPowerUp extends Item {

    public BombPowerUp(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doEffect() {
        bomberman.increaseBombRange();
        System.out.println("BOMB TO");
    }
}

