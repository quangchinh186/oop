package uet.oop.bomberman.entities.Enemies;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class Enemy extends Entity {
    public static double targetX, targetY;
    protected int animateTime;
    protected int turn;
    protected static double[] v_x = {1, 0, -1, 0, 0};
    protected static double[] v_y = {0, 1, 0, -1, 0};
    public Enemy(int x, int y, Image img){
        super(x, y, img);
    }

    public void die(){
        timer = 1;
        state = State.DIE;
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
        if(this.rect.intersects(targetX * Sprite.SCALED_SIZE, targetY * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)){
            BombermanGame.bomberman.die();
        }
    }

    public void move(){
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
        char c = GameMap.map.get(i).charAt(j);
        if(c == '#' || c == '*' || c == 'o'){
            if(position.x % 32 == 0 && position.y % 32 == 0){
                turn = timer % 4;
            } else {
                position.x += v_x[turn];
                position.y += v_y[turn];
            }
        } else {
            position.x += v_x[turn];
            position.y += v_y[turn];
        }
    }

}
