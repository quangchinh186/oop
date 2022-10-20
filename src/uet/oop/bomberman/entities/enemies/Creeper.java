package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class Creeper extends Oneal{
    private int count;
    public Creeper(int x, int y, Image img){
        super(x, y, img);
        count = 50;
    }

    @Override
    public void update() {
        if(state == State.STOP) return;
        super.update();
    }

    public void animate(){

    }

    @Override
    public void die(){
        this.timer = 1;
        state = State.DIE;
        animateTime = Sprite.SCALED_SIZE;
    }
    @Override
    public void checkMeetBomber() {
        if(Math.abs(targetX-x)+Math.abs(targetY-y) <= 1){
            count--;
            if(count == 0){
                position.x = x*32;
                position.y = y*32;
                this.img = Sprite.bomb_exploded.getFxImage();
                Bomb b = new Bomb(x, y);
                BombermanGame.bombs.add(b);
                b.setTime(20);
                die();
            }
        }else{
            count = 50;
        }
    }
}
