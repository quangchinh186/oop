package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.physics.Vector2D;
import uet.oop.bomberman.entities.Projectile;
import uet.oop.bomberman.graphics.Sprite;

import static view.GameViewManager.bomberman;
import static view.GameViewManager.visualEffects;


public class Turret extends Enemy {


    //Vector2D direction = new Vector2D();
    public static final int TURRET_RANGE = 200;

    public Turret(int x, int y, Image img) {
        super(x, y, img);
        gTimer.start();
    }

    @Override
    public void update() {

        if(Vector2D.getDistance(bomberman.position, position) < TURRET_RANGE) {

            Vector2D direction = new Vector2D();

            direction.x = (int) (bomberman.position.x - position.x)/Vector2D.getDistance(bomberman.position, position);
            direction.y = (int) (bomberman.position.y - position.y)/Vector2D.getDistance(bomberman.position, position);


            if(gTimer.getTicks() > 60 * 3) {
                createTurretShot(direction);
                gTimer.start();
                System.out.println("SHOOT");
            }
        }
    }

    public void createTurretShot(Vector2D direction) {
        Projectile pj = new Projectile((int)((position.x ) / Sprite.SCALED_SIZE ) ,
                (int)(position.y) /Sprite.SCALED_SIZE,
                Sprite.minvo_right2.getFxImage(), direction);
        visualEffects.add(pj);
    }
}
