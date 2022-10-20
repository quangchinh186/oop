package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.physics.Vector2D;

public class Projectile extends Entity{

    private Vector2D velocity;

    private Vector2D ogPos;

    private int range = 400;

    public Projectile(int x, int y, Image img, Vector2D vel) {
        super(x, y, img);
        ogPos = new Vector2D(x,y);
        velocity = vel;
    }

    @Override
    public void update() {

        if(Vector2D.getDistance(position, ogPos) > range) {
            setInactive();
        }
        position.x += velocity.x;
        position.y += velocity.y;

    }

    public void handleCollision() {

    }
}
