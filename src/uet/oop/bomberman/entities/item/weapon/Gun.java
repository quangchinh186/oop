package uet.oop.bomberman.entities.item.weapon;

import javafx.scene.image.Image;
import uet.oop.bomberman.physics.Vector2D;
import uet.oop.bomberman.entities.Projectile;
import uet.oop.bomberman.sound.Sound;

import static view.GameViewManager.bomberman;
import static view.GameViewManager.visualEffects;


public class Gun extends Weapon{

    private int cd ;

    Sound gunShot = new Sound("res/sfx/gunShot.wav");

    public static Image gunImg = new Image("/textures/rifle.png",41,14,true,true);

    private static Image bullet = new Image("/textures/ammo.png",12,12, true, true);

    private Image gunFx;

    private Image gunAmmoFx;

    public Gun(int x, int y, Image img) {
        super(x, y, img);

        ammo = 10;
        cd = 0;

    }

    @Override
    public void doEffect() {
        super.doEffect();
        this.img = gunImg;
    }

    @Override
    public void useWeapon() {
        super.useWeapon();

        if(ammo > 0 && armed) {


            //this.img = Sprite.movingSpriteSheet()
            gunShot.play();

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

        Projectile pj = new Projectile((int) bomberman.position.x ,
                (int) bomberman.position.y,
                bullet, direction);
        visualEffects.add(pj);
        //System.out.println("IM CUMMIN");
    }

    @Override
    public void update() {
        super.update();
        if(armed) {
            cd += 3;
        }
    }


}
