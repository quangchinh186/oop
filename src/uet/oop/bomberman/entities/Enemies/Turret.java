package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.entities.Entity;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class Turret extends Entity {

    public static final int TURRET_RANGE = 200;

    public Turret(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(Vector2D.getDistance(bomberman.position, position) < TURRET_RANGE) {
            System.out.println("IN TURRET RANGE.");
        }
    }
}
