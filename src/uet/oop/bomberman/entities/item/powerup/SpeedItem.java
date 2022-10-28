package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;

import static uet.oop.bomberman.entities.Bomber.PLAYER_SPEED_BOOSTED;
import static view.GameViewManager.bomberman;

public class SpeedItem extends Item {

    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void doEffect() {
        bomberman.setPlayerSpeed(PLAYER_SPEED_BOOSTED);
    }
}