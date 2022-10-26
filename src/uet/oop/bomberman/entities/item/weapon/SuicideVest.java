package uet.oop.bomberman.entities.item.weapon;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;
import uet.oop.bomberman.states.State;
import view.GameViewManager;
import uet.oop.bomberman.sound.Sound;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


import static uet.oop.bomberman.entities.Bomb.power;

public class SuicideVest extends Weapon {

    private List<Flames> flames = new ArrayList<>();

    Sound boom = new Sound("res/sfx/suicideVestSong.wav");

    public static Image vestImg = new Image("/textures/suicide_vest.png");
    public SuicideVest(int x, int y, Image img) {
        super(x, y, img);
        ammo = 10;
        boom.setVol(-10);
    }

    @Override
    public void doEffect() {
        super.doEffect();
        this.img = vestImg;

    }

    @Override
    public void useWeapon() {
        super.useWeapon();

        if(ammo > 0 && armed) {
            explode();
        }
            ammo--;
    }

    @Override
    public void update() {
        super.update();
    }

    private void explode() {

        boom.play();
        int width_lim = 31;
        int height_lim = 12;

        int startX;
        int startY;

        startX = Math.max(0, (int) (position.x / Sprite.SCALED_SIZE) - 1);
        startY = Math.max(0, (int) (position.y / Sprite.SCALED_SIZE) - 1);

        for(int i = startY ; i <= startY + 2; i++) {
            for(int j = startX; j <= startX + 2;j++) {

                Flames f = new Flames(j, i, Sprite.bomb_exploded1.getFxImage(), State.UP);
                if(i == Math.max(y-power, 0) || GameMap.map.get(i+1).charAt(x) == '#'){
                    f.setLast(true);
                }
                f.setVertical(true);
                GameViewManager.visualEffects.add(f);
                flames.add(f);
                destroy(j, i);
                if(!(GameViewManager.stillObjects.get(i*31 + x) instanceof Grass)){
                    break;
                }
            }
        }

        GameViewManager.visualEffects.addAll(flames);

    }

    public void destroy(int _x, int _y) {
        if(GameMap.map.get(_y).charAt(_x) == '*'){
            Brick temp = (Brick) GameViewManager.stillObjects.get(_y*31 + _x);
            temp.setExploded(true);
        }
        if(GameViewManager.bomberman.x == _x && GameViewManager.bomberman.y == _y
                && !GameViewManager.bomberman.isImmune()){
            GameViewManager.bomberman.die();
        }
        for (Entity t : GameViewManager.entities)
        {
            if(t.x == _x && t.y == _y){
                t.die();
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
