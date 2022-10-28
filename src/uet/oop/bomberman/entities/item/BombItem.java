package uet.oop.bomberman.entities.item.powerup;

import javafx.scene.image.Image;
<<<<<<<< HEAD:src/uet/oop/bomberman/entities/item/powerup/BombPowerUp.java
import static view.GameViewManager.bomberman;


public class BombPowerUp extends PowerUp {

    public BombPowerUp(int x, int y, Image img) {
========

import static view.GameViewManager.bomberman;


public class BombItem extends Item {

    public BombItem(int x, int y, Image img) {
>>>>>>>> merge-dev:src/uet/oop/bomberman/entities/item/BombItem.java
        super(x, y, img);
    }
    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doEffect() {
<<<<<<<< HEAD:src/uet/oop/bomberman/entities/item/powerup/BombPowerUp.java
        super.doEffect();
        bomberman.increaseBombRange();
        System.out.println("BOMB TO");
========
        bomberman.setBombNumbers(bomberman.getBombNumbers()+1);
>>>>>>>> merge-dev:src/uet/oop/bomberman/entities/item/BombItem.java
    }
}

