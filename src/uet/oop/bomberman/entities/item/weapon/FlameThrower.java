package uet.oop.bomberman.entities.item.weapon;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;
import view.GameViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class FlameThrower extends Weapon{

    int flameTimer;

    private List<Flames> flames = new ArrayList<>();

    public FlameThrower(int x, int y, Image img) {
        super(x, y, img);
        ammo = 3;
    }

    public void doEffect() {
        super.doEffect();
        this.img = Sprite.explosion_horizontal2.getFxImage();
    }


    @Override
    public void useWeapon() {
        super.useWeapon();



        if(armed && ammo > 0) {
            System.out.println("EXPOLSE");

            timer.schedule(new TimerTask(){
                //override run method
                @Override
                public void run() {
//print a message notifying about timer
                    System.out.println("Timer begins. . . .");
                    explode();
                }



            }, 400);



        }

        for(Flames fl : flames) {
            //if()
        }


    }


    public void explode() {

        int width_lim = GameMap.WIDTH - 1;
        int height_lim = GameMap.HEIGHT - 1;
        //up

        //right
        for (int i = (int)position.x/Sprite.SCALED_SIZE + 1; i < Math.min((int)position.x/Sprite.SCALED_SIZE + 3, width_lim); i++) {
            Flames f = new Flames(i, (int)position.y/Sprite.SCALED_SIZE, Sprite.explosion_horizontal.getFxImage(), State.RIGHT);
            if (i == Math.min((int)position.x/Sprite.SCALED_SIZE  , width_lim) || GameMap.map.get((int)position.y/Sprite.SCALED_SIZE).charAt(i + 1) == '#') {
                f.setLast(true);
            }
            f.setHorizontal(true);
            GameViewManager.visualEffects.add(f);
            flames.add(f);
            destroy(i, (int)position.y/Sprite.SCALED_SIZE);
            if (!(GameViewManager.stillObjects.get((int)position.y/Sprite.SCALED_SIZE * 31 + i) instanceof Grass)) {
                break;
            }
        }
        GameViewManager.visualEffects.addAll(flames);
    }


    public void destroy(int _x, int _y) {
        switch (GameMap.map.get(_y).charAt(_x)){
            case '*' :
                Brick temp = (Brick) GameViewManager.stillObjects.get(_y*31 + _x);
                temp.setExploded(true);
                break;

            default:
                break;
        }
    }
}
