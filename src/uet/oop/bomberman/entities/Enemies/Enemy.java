package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Animation;

public class Enemy extends Entity {

    public Vector2D velocity = new Vector2D();

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        Animation walkLeft = new Animation(0, 3, 100);
        Animation walkRight = new Animation(1, 3, 100);
        Animation die = new Animation(2, 6, 100);
        animations.put("WalkLeft", walkLeft);
        animations.put("WalkRight", walkRight);
        animations.put("Die", die);
    }

    @Override
    public void update() {
        //ai and shiet.

        handleAnimation();
    }

    public void handleAnimation() {

        if(!isActive()) {
            play("Die");
        }
        else if(velocity.x > 0) {
            play("WalkRight");
        }
        else {
            play("WalkLeft");
        }

    }
}
