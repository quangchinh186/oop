package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        this.s1 = Sprite.oneal_right1;
        this.s2 = Sprite.oneal_right2;
        this.s3 = Sprite.oneal_right3;
        animateTime = Sprite.DEFAULT_SIZE;
        state = State.RIGHT;
        turn = 2;
    }

    @Override
    public void update() {
        super.update();
        animate();
    }
    public void animate(){
        if(turn == 0){
            s1 = Sprite.oneal_right1;
            s2 = Sprite.oneal_right2;
            s3 = Sprite.oneal_right3;
        }
        if(turn == 2){
            s1 = Sprite.oneal_left1;
            s2 = Sprite.oneal_left2;
            s3 = Sprite.oneal_left3;
        }
        if(state == State.DIE){
            s1 = Sprite.mob_dead1;
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

    public void die(){
        this.timer = 1;
        state = State.DIE;
        animateTime = Sprite.SCALED_SIZE;
        this.img = Sprite.oneal_dead.getFxImage();
    }
    private int findPlayer(int startX, int startY){
        int tX = (int) (targetX/Sprite.SCALED_SIZE);
        int tY = (int) (targetY/Sprite.SCALED_SIZE);
        int[] neighbor = {255, 255, 255, 255};
        int min = 10000, ans = 0;
        for(int i = 0; i < 4; i++){
            int t = (int) (x + v_x[i] + y + v_y[i]);
            if(tX + tY - t < min){
                min = tX + tY -t;
                ans = i;
            }
        }

        return ans;
    }
    public void move(){
        if(position.x % 32 == 0 && position.y % 32 == 0){
            turn = findPlayer(this.x, this.y);
        }
        double nX = position.x + v_x[turn], nY = position.y + v_y[turn];
        int j = (int) (nX / Sprite.SCALED_SIZE);
        int i = (int) (nY / Sprite.SCALED_SIZE);
        switch (turn){
            case 0:
                nX += Sprite.SCALED_SIZE;
                j = (int) (nX / Sprite.SCALED_SIZE);
                break;
            case 1:
                nY += Sprite.SCALED_SIZE;
                i = (int) (nY / Sprite.SCALED_SIZE);
                break;
            default:
                break;
        }


        if(GameMap.map.get(i).charAt(j) == '#' || GameMap.map.get(i).charAt(j) == '*'){
            if(position.x % 32 != 0 && position.y % 32 != 0){
                position.x += v_x[turn];
                position.y += v_y[turn];
            }
        } else {
            position.x += v_x[turn];
            position.y += v_y[turn];
        }

    }
}