package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {

    private Sprite s1, s2, s3;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        this.timer = 0;
        s1 = Sprite.bomb;
        s2 = Sprite.bomb_1;
        s3 = Sprite.bomb_2;
    }

    public static int timer;

    @Override
    public void update() {
        if(this.timer == 150){
            explode();
            s1 = Sprite.bomb_exploded;
            s2 = Sprite.bomb_exploded1;
            s3 = Sprite.bomb_exploded2;
        }
        else{
            this.img = Sprite.movingSprite(s1, s2, s3,timer, 40).getFxImage();
        }
        this.timer++;
    }

    public void explode(){
        for(int i = y-1; i <= y+1; i++){
            if(i != y){
                Entity f = new Flames(x, i, Sprite.explosion_vertical.getFxImage());
                ((Flames) f).setVertical(true);
                BombermanGame.visualEffects.add(f);
            }
        }
        for(int i = x-1; i <= x+1; i++){
            if(i != x){
                Entity f = new Flames(i, y, Sprite.explosion_horizontal.getFxImage());
                ((Flames) f).setHorizontal(true);
                BombermanGame.visualEffects.add(f);
            }
        }
    }


}