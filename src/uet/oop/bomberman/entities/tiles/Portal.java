package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.item.Item;

public class Portal extends Item {

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        double _x = BombermanGame.bomberman.position.x;
        double _y = BombermanGame.bomberman.position.y;
        if(!this.rect.intersects(_x, _y, rect.getWidth(), rect.getHeight())) {
            BombermanGame.bomberman.setAtPortal(false);
        }
    }

    @Override
    public void doEffect() {
        BombermanGame.bomberman.setAtPortal(true);
    }
}