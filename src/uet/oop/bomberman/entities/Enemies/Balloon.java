package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        this.s1 = Sprite.balloom_right1;
        this.s2 = Sprite.balloom_right2;
        this.s3 = Sprite.balloom_right3;
        state = State.RIGHT;
        turn = 2;
    }
    @Override
    public void update() {
        super.update();
        if(state == State.STOP){
            dieAnimation();
        }else {
            animate();
        }
    }

    public void animate(){
        if(turn == 0){
            s1 = Sprite.balloom_right1;
            s2 = Sprite.balloom_right2;
            s3 = Sprite.balloom_right3;
        }
        if(turn == 2){
            s1 = Sprite.balloom_left1;
            s2 = Sprite.balloom_left2;
            s3 = Sprite.balloom_left3;
        }
        timer = timer > Sprite.SCALED_SIZE ? 0 :timer+1;
        this.img = Sprite.movingSprite(s1, s2, s3, this.timer, animateTime).getFxImage();
    }

    public void die(){
        super.die();
        this.img = Sprite.balloom_dead.getFxImage();
    }

    public void Turn(){

    }


}