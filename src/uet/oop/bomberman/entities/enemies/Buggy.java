package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.physics.Vector2D;
import uet.oop.bomberman.states.State;
import view.GameViewManager;

public class Buggy extends Enemy{

    public Buggy(int x, int y, Image img){
        super(x, y, img);
    }
    private int teleportTime = 0;

    @Override
    public void update() {
        if(position.x % Sprite.SCALED_SIZE == 0 && position.y % Sprite.SCALED_SIZE == 0){
            if(teleportTime == 500){
                teleport();
            }
        }
        super.update();
        if(state == State.STOP){
            dieAnimation();
        }else {
            animate();
        }
        teleportTime = teleportTime < 500 ? teleportTime+1 : 500;
    }

    public void die(){
        super.die();
        if(teleportTime == 500){
            this.img = Sprite.blue_dead.getFxImage();
        } else {
            this.img = Sprite.white_dead.getFxImage();
        }
    }
    public void animate(){
        //change color when teleportTime = 500(yellow) and 1000(red)
        if(teleportTime == 500) {
            if(turn == 0 || turn == 1){
                s1 = Sprite.blue_right1;
                s2 = Sprite.blue_right2;
                s3 = Sprite.blue_right3;
            }
            else{
                s1 = Sprite.blue_left1;
                s2 = Sprite.blue_left2;
                s3 = Sprite.blue_left3;
            }
        } else {
            if(turn == 0 || turn == 1){
                s1 = Sprite.white_right1;
                s2 = Sprite.white_right2;
                s3 = Sprite.white_right3;
            }
            else{
                s1 = Sprite.white_left1;
                s2 = Sprite.white_left2;
                s3 = Sprite.white_left3;
            }
        }

        timer = timer > Sprite.SCALED_SIZE ? 0 :timer+1;
        this.img = Sprite.movingSprite(s1, s2, s3, this.timer, animateTime).getFxImage();
    }

    public void teleport(){
        for (Enemy enemy : GameViewManager.entities) {
            if(enemy instanceof Buggy) continue;
            if(enemy.getPosition().x % 32 == 0 && enemy.getPosition().y % 32 == 0){
                double a = enemy.getPosition().x;
                double b = enemy.getPosition().y;
                enemy.setPosition(this.position);
                position.x = a;
                position.y = b;
                teleportTime = 0;
                return;
            }
        }
    }



}