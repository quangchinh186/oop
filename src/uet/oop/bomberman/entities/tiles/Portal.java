package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.item.Item;
import view.GameViewManager;

public class Portal extends Item {

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        double _x = GameViewManager.bomberman.position.x;
        double _y = GameViewManager.bomberman.position.y;
        if(!this.rect.intersects(_x, _y, rect.getWidth(), rect.getHeight())) {
            GameViewManager.bomberman.setAtPortal(false);
        }
    }

    @Override
    public void doEffect() {
        GameViewManager.bomberman.setAtPortal(true);
    }
}