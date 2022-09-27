package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
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
        if(this.timer > 100) {
            this.timer = 0;
        } else if(this.timer > 75){
            explode();
        }
        else{
            this.timer++;
            this.img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2,timer, 40).getFxImage();
        }
    }

    public void explode(){
        for(int i = y-1; i < y+1; i++){

        }
        for(int i = x-1; i < x+1; i++){

        }

        this.img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, timer, 40).getFxImage();
    }
}