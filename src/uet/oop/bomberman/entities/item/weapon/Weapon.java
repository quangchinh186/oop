package uet.oop.bomberman.entities.item.weapon;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.states.State;
import java.util.Timer;
import java.util.TimerTask;

import static view.GameViewManager.bomberman;


class Helper extends TimerTask
{
    public static int i = 0;
    public void run()
    {
        //Weapon.createProjectile();
    }
}


public class Weapon extends Item {

    private int angle = 0;
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
            this.position.x = bomberman.getPosition().x;
            this.position.y = bomberman.getPosition().y + Sprite.DEFAULT_SIZE;
            getAngle();

            if(ammo == 0) {
                setInactive();
            }
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



    @Override
    public void render(GraphicsContext gc) {
        //Sprite.drawRotatedImage(gc, this.img, angle, position.x ,position.y);
        //write a draw function for rect.
        if(bomberman.getState() == State.LEFT && armed) {
            gc.drawImage(this.img, 0, 0, this.img.getWidth(), this.img.getHeight(),
                    position.x ,position.y,-this.img.getWidth()/2,this.img.getHeight()/2);
        }
        else if(bomberman.getState() != State.LEFT && armed){
            Sprite.drawRotatedImage(gc, this.img, angle, position.x, position.y,
                    this.img.getWidth()/2, this.img.getHeight()/2);
        }

        else {
            gc.drawImage(this.img, position.x, position.y);
        }

    }

   public void getAngle() {
       switch (bomberman.getState()){
           case DOWN:
               angle = 90;
               break;
           case UP:
               angle = 270;
               break;
           default:
               angle = 0;
               break;
       }
    }
}
