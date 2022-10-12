package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.entities.Projectile;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.BombermanGame.visualEffects;



class Helper extends TimerTask
{
    public static int i = 0;
    public void run()
    {
        Weapon.createProjectile();
    }
}


public class Weapon extends Item{


    protected Timer timer = new Timer();
    protected boolean armed = false;
    private int ammo;
    public Weapon(int x, int y, Image img) {
        super(x, y, img);
        ammo = 10;
        //void equip.

    }

    @Override
    public void update() {
        super.update();
        if(armed) {
            this.position = bomberman.getPosition();
        }

    }

    public void useWeapon() {
        //dung de overload.
        if(ammo > 0 && armed) {

            TimerTask task = new Helper();
            //timer.schedule(task, 1000);
            createProjectile();
            ammo--;
        }

        if(ammo == 0 && armed) setInactive();

    }

    public void reloadAmmo() {
        ammo = 10;
    }
    @Override
    public void doEffect() {
        armed = true;
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


    public boolean isArmed() {
        return armed;
    }
}
