package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class Creeper extends Oneal{
    private int count;
    private Bomb bomb;
    public Creeper(int x, int y, Image img){
        super(x, y, img);
        count = 50;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void dieAnimation() {
        s1 = Sprite.bomb_exploded;
        s2 = Sprite.bomb_exploded1;
        s3 = Sprite.bomb_exploded2;
        this.img = Sprite.movingSprite(s1, s2, s3, this.timer, animateTime).getFxImage();
        timer = timer < animateTime ? timer+1 : animateTime;
        if(timer == animateTime){
            this.img = null;
            if(bomb != null){
                BombermanGame.visualEffects.removeAll(bomb.getVisual());
            }
            state = State.DIE;
        }
    }

    @Override
    public void animate(){
        s1 = Sprite.creeper_right1;
        s2 = Sprite.creeper_right2;
        s3 = Sprite.creeper_right3;

        timer = timer > Sprite.SCALED_SIZE ? 0 :timer+1;
        this.img = Sprite.movingSprite(s1, s2, s3, this.timer, animateTime).getFxImage();
    }

    @Override
    public void die(){
        this.timer = 1;
        state = State.STOP;
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
                bomb = new Bomb(x, y, "Creeper");
                bomb.explode();
                die();
            }
        }else{
            count = 50;
        }
    }
}
