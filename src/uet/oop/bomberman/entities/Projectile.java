package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Physics.Vector2D;

public class Projectile extends Entity{

    private Vector2D velocity;

    private int range;

    public Projectile(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}
