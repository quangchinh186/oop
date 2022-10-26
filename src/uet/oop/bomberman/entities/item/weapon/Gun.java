package uet.oop.bomberman.entities.item.weapon;

import javafx.scene.image.Image;
import uet.oop.bomberman.physics.Vector2D;
import uet.oop.bomberman.entities.Projectile;
import uet.oop.bomberman.graphics.Sprite;

import static view.GameViewManager.bomberman;
import static view.GameViewManager.visualEffects;




public class Gun extends Weapon{


    public static Image bullet_sprite = new Image("textures/bullet.png", 12, 12, true, true);
    private int cd ;
    private static Image gunImg = new Image("/textures/rifle.png");

    private Image gunFx;

    private Image gunAmmoFx;

    public Gun(int x, int y, Image img) {
        super(x, y, img);

        ammo = 7;
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

            cd += 2;

            if(cd > 360) cd = 0;

            System.out.println(cd);

            //this.img = Sprite.movingSpriteSheet()
            System.out.println("FIRE");


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

        Projectile pj = new Projectile((int)((bomberman.position.x) / Sprite.SCALED_SIZE ) ,
                (int)(bomberman.position.y) /Sprite.SCALED_SIZE,
                bullet_sprite, direction);
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
