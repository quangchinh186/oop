package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.entities.Projectile;
import uet.oop.bomberman.graphics.Sprite;

import java.util.TimerTask;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.BombermanGame.visualEffects;

public class Gun extends Weapon{

    public Gun(int x, int y, Image img) {
        super(x, y, img);
        ammo = 7;
    }

    @Override
    public void doEffect() {
        super.doEffect();
        this.img = Sprite.explosion_horizontal.getFxImage();
    }

    @Override
    public void useWeapon() {
        super.useWeapon();
        if(ammo > 0 && armed) {

            System.out.println("FIRE");
            TimerTask task = new Helper();
            //timer.schedule(task, 1000);
            createProjectile();
            ammo--;
        }

    }


    public static void createProjectile() {

        Vector2D direction = new Vector2D(0,0);
        switch(bomberman.getState()) {
            case UP:
                direction.y = -3;
                break;
            case DOWN:y:
            // code block
            direction.y = 3;
                break;
            case LEFT:
                // code block
                direction.x = -3;
                break;
            case RIGHT:
                // code block
                direction.x = 3;
                break;
            default:
                // code block
        }

        Projectile pj = new Projectile((int)((bomberman.position.x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE ) ,
                (int)(bomberman.position.y + Sprite.DEFAULT_SIZE) /Sprite.SCALED_SIZE,
                Sprite.minvo_right2.getFxImage(), direction);
        visualEffects.add(pj);
        //System.out.println("IM CUMMIN");
    }
}
