package uet.oop.bomberman.entities.item.weapon;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.item.powerup.SpeedItem;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;
import view.GameViewManager;


import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class SuicideVest extends Weapon {


    private List<Flames> flames = new ArrayList<>();
    public static Image vestImg = new Image("/textures/suicide_vest.png");
    public SuicideVest(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void doEffect() {
        super.doEffect();
        this.img = vestImg;
    }

    @Override
    public void useWeapon() {
        super.useWeapon();

        if(armed && ammo > 0) {
            System.out.println("Suicide Vest");

            timer.schedule(new TimerTask(){
                //override run method
                @Override
                public void run() {
                    System.out.println("Timer begins. . . .");
                   suicide();
                }



            }, 400);

        }


    }

    private void suicide() {

        int width_lim = GameMap.WIDTH - 1;
        int height_lim = GameMap.HEIGHT - 1;

        for(int i = (int) (position.x / Sprite.SCALED_SIZE) - 1 ;
            i <= (position.x / Sprite.SCALED_SIZE) + 1 ;i++) {
            for(int j = (int) (position.y / Sprite.SCALED_SIZE) - 1 ;
                j <= (position.y / Sprite.SCALED_SIZE) + 1 ;j++) {

                Flames f = new Flames(i, j, Sprite.explosion_horizontal.getFxImage(), uet.oop.bomberman.states.State.RIGHT);
                if (i == Math.min((int)position.x/Sprite.SCALED_SIZE  , width_lim) || GameMap.map.get((int)position.y/Sprite.SCALED_SIZE).charAt(i + 1) == '#') {
                    f.setLast(true);
                }
                f.setHorizontal(true);
                GameViewManager.visualEffects.add(f);
                flames.add(f);

                if (!(GameViewManager.stillObjects.get((int)position.y/Sprite.SCALED_SIZE * 31 + i) instanceof Grass)) {
                    break;
                }

            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if(armed) {
            gc.drawImage(this.img, 0, 0, this.img.getWidth(), this.img.getHeight(),
                    position.x ,position.y,this.img.getWidth()/2,this.img.getHeight()/2);
        }
        else {
            gc.drawImage(this.img, position.x, position.y);
        }
    }
}
