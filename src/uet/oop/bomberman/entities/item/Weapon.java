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
        //Weapon.createProjectile();
    }
}


public class Weapon extends Item{


    protected Timer timer = new Timer();
    protected boolean armed = false;
    protected int ammo;
    public Weapon(int x, int y, Image img) {
        super(x, y, img);
        ammo = 2;
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
        if(ammo == 0 && armed) setInactive();
    }

    public void reloadAmmo() {
        ammo = 10;
    }
    @Override
    public void doEffect() {
        armed = true;
    }




    public boolean isArmed() {
        return armed;
    }
}
