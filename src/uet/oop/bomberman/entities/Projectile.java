package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class Projectile extends Entity{

    private Vector2D velocity;

    private Vector2D ogPos;

    private int range = 400;

    public Projectile(int x, int y, Image img, Vector2D vel) {
        super(x, y, img);
        ogPos = new Vector2D(x,y);

        velocity = vel;

        position.x += 1;
        position.y += 1;
        rect.setX(position.x);
        rect.setY(position.y);
        rect.setWidth(20);
        rect.setHeight(20);
    }

    @Override
    public void update() {

        if(Vector2D.getDistance(position, ogPos) > range) {
            setInactive();
        }

        if(GameMap.checkCollision(rect)) {
            System.out.println("VA CHAM");

            setInactive();
        }

        position.x += velocity.x;
        position.y += velocity.y;
        rect.setX(position.x);
        rect.setY(position.y);

    }

    public void handleCollision() {

    }
}
