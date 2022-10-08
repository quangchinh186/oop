package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.entities.Projectile;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.BombermanGame.visualEffects;

public class Weapon extends Item{

    private boolean armed = false;
    private int ammo = 20;
    public Weapon(int x, int y, Image img) {
        super(x, y, img);

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
            createProjectile();
        }
        ammo--;

    }

    @Override
    public void doEffect() {
        armed = true;
    }

    private void createProjectile() {

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

        Projectile pj = new Projectile((int)position.x/ Sprite.SCALED_SIZE, (int)position.y/Sprite.SCALED_SIZE,
                Sprite.minvo_right2.getFxImage(), direction);
        visualEffects.add(pj);
        //System.out.println("IM CUMMIN");
    }

}
