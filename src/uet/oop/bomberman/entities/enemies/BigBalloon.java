package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.graphics.Sprite;

public class BigBalloon extends Enemy{
    int life = 1;
    //Enemy child = new Balloon()
    public BigBalloon(int x, int y, Image image){
        super(x, y, image);
        this.s1 = Sprite.doll_right1;
        this.s2 = Sprite.doll_right2;
        this.s3 = Sprite.doll_right3;
        animateTime = Sprite.DEFAULT_SIZE;
        state = State.RIGHT;
        turn = 2;
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void update() {
        if(state == State.DIE){
            Enemy b = new Balloon(x, y, Sprite.balloom_left3.getFxImage());
            Enemy b1 = new Balloon(x, y, Sprite.balloom_left2.getFxImage());

            BombermanGame.entities.add(b1);
            BombermanGame.entities.add(b);
        }
        super.update();
        animate();
    }

    public void animate(){
        if(turn == 0){
            s1 = Sprite.doll_right1;
            s2 = Sprite.doll_right2;
            s3 = Sprite.doll_right3;
        }
        if(turn == 2){
            s1 = Sprite.doll_left1;
            s2 = Sprite.doll_left2;
            s3 = Sprite.doll_left3;

        }
        if(state == State.DIE){
            s1 = Sprite.doll_dead;
            s2 = Sprite.mob_dead2;
            s3 = Sprite.mob_dead3;
        }
        if(state == State.DIE && timer % Sprite.DEFAULT_SIZE == 0){
            this.img = null;
        }else{
            timer = timer > Sprite.SCALED_SIZE ? 0 :timer+1;
            this.img = Sprite.movingSprite(s1, s2, s3, this.timer, animateTime).getFxImage();
        }
    }
}
