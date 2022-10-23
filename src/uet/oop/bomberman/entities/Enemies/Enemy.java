package uet.oop.bomberman.entities.enemies;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.physics.Vector2D;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import static uet.oop.bomberman.BombermanGame.isPause;

public class Enemy extends Entity {
    public static double targetX, targetY;
    protected int animateTime;
    protected int turn;
    protected static double[] v_x = {1, 0, -1, 0, 0};
    protected static double[] v_y = {0, 1, 0, -1, 0};
    public Enemy(int x, int y, Image img){
        super(x, y, img);
        animateTime = Sprite.DEFAULT_SIZE;
    }

    public void die(){
        timer = -10;
        state = State.STOP;
        animateTime = Sprite.SCALED_SIZE;
    }

    public void dieAnimation(){
        s1 = Sprite.mob_dead1;
        s2 = Sprite.mob_dead2;
        s3 = Sprite.mob_dead3;
        this.img = Sprite.movingSprite(s1, s2, s3, this.timer, animateTime).getFxImage();
        timer = timer < animateTime ? timer+1 : animateTime;
        if(timer == animateTime){
            this.img = null;
            state = State.DIE;
        }
    }
    public void update() {
        targetX = BombermanGame.bomberman.x;
        targetY = BombermanGame.bomberman.y;
        this.x = (int) (position.x / Sprite.SCALED_SIZE);
        this.y = (int) (position.y / Sprite.SCALED_SIZE);
        if(state != State.DIE && BombermanGame.bomberman.getState() != State.DIE && state != State.STOP){
            checkMeetBomber();
            move();
        }
        this.rect.setX(position.x);
        this.rect.setY(position.y);
    }

    public void checkMeetBomber(){
        if(this.rect.intersects(targetX * Sprite.SCALED_SIZE + 8, targetY * Sprite.SCALED_SIZE + 8, Sprite.DEFAULT_SIZE, Sprite.DEFAULT_SIZE)){
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
                turn = (int) Math.floor(Math.random()*4);
            } else {
                position.x += v_x[turn];
                position.y += v_y[turn];
            }
        } else {
            position.x += v_x[turn];
            position.y += v_y[turn];
        }
    }

    public void setPosition(Vector2D v){
        this.position.x = v.x;
        this.position.y = v.y;
    }

    public Vector2D getPosition(){
        return this.position;
    }
}
