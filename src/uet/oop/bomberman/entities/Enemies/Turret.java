package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Projectile;
import uet.oop.bomberman.graphics.Sprite;

import java.util.TimerTask;
import java.util.TooManyListenersException;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.BombermanGame.visualEffects;

public class Turret extends Entity {


    Vector2D direction = new Vector2D();
    public static final int TURRET_RANGE = 200;

    public Turret(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

        if(Vector2D.getDistance(bomberman.position, position) < TURRET_RANGE) {

            direction.x = (int) (bomberman.position.x - position.x)/Vector2D.getDistance(bomberman.position, position);
            direction.y = (int) (bomberman.position.y - position.y)/Vector2D.getDistance(bomberman.position, position);

            //createTurretShot(direction);
        }
    }

    public void createTurretShot(Vector2D direction) {
        Projectile pj = new Projectile((int)((position.x ) / Sprite.SCALED_SIZE ) ,
                (int)(position.y) /Sprite.SCALED_SIZE,
                Sprite.minvo_right2.getFxImage(), direction);
        visualEffects.add(pj);
    }
}
