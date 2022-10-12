package uet.oop.bomberman.entities.Enemies;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy extends Entity {
    public static double targetX, targetY;
    protected int animateTime;
    protected int turn;
    protected static double[] v_x = {1, 0, -1, 0, 0};
    protected static double[] v_y = {0, 1, 0, -1, 0};
    public Enemy(int x, int y, Image img){
        super(x, y, img);
    }

    public void update() {
        this.x = (int) (position.x / Sprite.SCALED_SIZE);
        this.y = (int) (position.y / Sprite.SCALED_SIZE);
        if(state != State.DIE){
            checkMeetBomber();
            move();
        }
        this.rect.setX(position.x);
        this.rect.setY(position.y);
    }

    public void checkMeetBomber(){
        targetX = BombermanGame.bomberman.getX();
        targetY = BombermanGame.bomberman.getY();
        System.out.println(targetX + " " + targetY);
        if(this.rect.intersects(targetX * Sprite.SCALED_SIZE, targetY * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)){
            BombermanGame.bomberman.die();
        }
    }

    public void move(){

    }

}
