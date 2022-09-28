package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private int x;
    private int y;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        if(this.timer > 300) {
            this.timer = 0;
        } else if(this.timer > 90){
            this.timer++;
            explode();
        }
        else{
            this.timer++;
            this.img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2,timer, 40).getFxImage();
        }
    }

    public void explode(){
        if(timer == 95){
            for(int i = y-1; i <= y+1; i++){
                if(i != y){
                    Entity f = new Flames(x, i, Sprite.explosion_vertical.getFxImage());
                    BombermanGame.visualEffect.add(f);
                }
            }
            for(int i = x-1; i <= x+1; i++){
                if(i != x){
                    Entity f = new Flames(i, y, Sprite.explosion_horizontal.getFxImage());
                    BombermanGame.visualEffect.add(f);
                }
            }
        }

        this.img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, timer-90, 10).getFxImage();
    }
}