package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Entity {
    State state;
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        this.s1 = Sprite.balloom_right1;
        this.s2 = Sprite.balloom_right2;
        this.s3 = Sprite.balloom_right3;
        state = State.RIGHT;
    }

    @Override
    public void update() {
        double x = BombermanGame.bomberman.getX();
        double y = BombermanGame.bomberman.getY();
        this.position.x--;
        this.rect.setX(position.x);
        this.rect.setY(position.y);
        if(this.rect.intersects(x , y, Sprite.SCALED_SIZE - 5, Sprite.SCALED_SIZE - 5)){
            BombermanGame.bomberman.die();
        }
        if(this.timer > 100) this.timer = 0;
        else this.timer++;
        this.img = Sprite.movingSprite(s1, s2, s3, this.timer, Sprite.DEFAULT_SIZE).getFxImage();
    }
}