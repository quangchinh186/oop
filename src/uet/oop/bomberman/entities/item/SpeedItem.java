package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.entities.Bomber.PLAYER_SPEED_BOOSTED;

public class SpeedItem extends Item {

    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(!isActive) {
            bomberman.setPlayerSpeed(PLAYER_SPEED_BOOSTED);
        }

        super.update();

    }
}
