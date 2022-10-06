package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class Balloon extends Entity {
    State state;
    private int turn;
    private double[] v_x = {1, 0, -1, 0};
    private double[] v_y = {0, 1, 0, -1};
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        position.y++;
        rect.setHeight(30);
        rect.setWidth(30);
        this.s1 = Sprite.balloom_right1;
        this.s2 = Sprite.balloom_right2;
        this.s3 = Sprite.balloom_right3;
        state = State.RIGHT;
        turn = 0;
    }
    @Override
    public void update() {
        checkMeetBomber();
        move();
        if(this.timer > 100) this.timer = 0;
        else this.timer++;
        this.img = Sprite.movingSprite(s1, s2, s3, this.timer, Sprite.DEFAULT_SIZE).getFxImage();
    }

    public void checkMeetBomber(){
        double x = BombermanGame.bomberman.getX();
        double y = BombermanGame.bomberman.getY();
        this.rect.setX(position.x);
        this.rect.setY(position.y);
        if(this.rect.intersects(x + 5 , y + 5, Sprite.SCALED_SIZE - 5, Sprite.SCALED_SIZE - 5)){
            BombermanGame.bomberman.die();
        }
    }

    public void move(){
        double nX = position.x + v_x[turn], nY = position.y + v_y[turn];
        Rectangle nextMove = new Rectangle(nX, nY, nX+rect.getWidth(), nY+rect.getHeight());
        if(GameMap.checkCollision(nextMove)){
            //turn = turn > 2 ? 0 : turn+1;
        }else {
            position.x = nextMove.getX();
            position.y = nextMove.getY();
        }
    }

}