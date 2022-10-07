package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class Balloon extends Entity {
    private int turn;
    private int animateTime;
    private double[] v_x = {1, 0, -1, 0, 0};
    private double[] v_y = {0, 1, 0, -1, 0};
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        this.s1 = Sprite.balloom_right1;
        this.s2 = Sprite.balloom_right2;
        this.s3 = Sprite.balloom_right3;
        animateTime = Sprite.DEFAULT_SIZE;
        state = State.RIGHT;
        turn = 2;
    }
    @Override
    public void update() {
        this.x = (int) (position.x / Sprite.SCALED_SIZE);
        this.y = (int) (position.y / Sprite.SCALED_SIZE);
        if(state != State.DIE){
            checkMeetBomber();
            move();
        }
        animate();
        this.rect.setX(position.x);
        this.rect.setY(position.y);
        System.out.println(state);
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
        this.img = Sprite.balloom_dead.getFxImage();
    }
    public void checkMeetBomber(){
        double x = BombermanGame.bomberman.getX();
        double y = BombermanGame.bomberman.getY();
        if(this.rect.intersects(x + 5 , y + 5, Sprite.SCALED_SIZE - 5, Sprite.SCALED_SIZE - 5)){
            BombermanGame.bomberman.die();
        }
    }

    public void move(){
        double nX = position.x + v_x[turn], nY = position.y + v_y[turn];
        Rectangle nextMove = new Rectangle(nX, nY, nX+30, nY+30);
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
            if(position.x % 32 == 0 && position.y % 32 == 0){
                turn = turn > 2 ? 0 : turn+1;
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