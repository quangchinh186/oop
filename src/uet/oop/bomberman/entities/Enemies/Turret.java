package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.physics.Vector2D;
import uet.oop.bomberman.entities.Projectile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.timer.GTimer;

import static view.GameViewManager.bomberman;
import static view.GameViewManager.visualEffects;


public class Turret extends Enemy {



    private final int BULLET_WIDTH = 50;
    private final int BULLET_HEIGHT = 30;
    public static Image lgBullet = new Image("/textures/gay_flag.jpeg", 50, 30, true, true);

    public static Image turretSkin = new Image("/textures/america_ball.png", 32, 32, true, true);

    GTimer gTimer = new GTimer();
    //Vector2D direction = new Vector2D();
    public static final int TURRET_RANGE = 100;

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
        Projectile pj = new Projectile((int)((position.x )) ,
                (int)(position.y) ,
                lgBullet, direction,  BULLET_WIDTH, BULLET_HEIGHT,true);
        visualEffects.add(pj);
    }
}
