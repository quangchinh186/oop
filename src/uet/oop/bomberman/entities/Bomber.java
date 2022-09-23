package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Physics.Vector2D;

import java.awt.*;
import java.util.HashSet;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends Entity {

    private Paint pt;
    static HashSet<String> currentlyActiveKeys;
    static HashSet<String> releasedKey;

    private Vector2D velocity;

    public int health;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        prepareActionHandlers();
        velocity = new Vector2D();
        rect.setWidth(20);
        rect.setHeight(20);


    }

    @Override
    public void update() {
        //get input
        actionHandler();
        //chage position


        rect.setX(position.x);
        rect.setY(position.y);

        Grass tmp = new Grass();

        for (Entity object : stillObjects) {
            if(object.rect.intersects(this.rect.getX(), this.rect.getY(), this.rect.getWidth(), this.rect.getHeight())
            && object.getClass() != tmp.getClass()) {
                System.out.println("COLLISON");
                velocity.x = -velocity.x;
                velocity.y = -velocity.y;
            }
        }
        //update pos sau khi nhan va cham
        position.x += velocity.x;
        position.y += velocity.y;

    }

    public void actionHandler () {

        if(currentlyActiveKeys.contains("LEFT")) {
            velocity.x = -1;
        }
        if (currentlyActiveKeys.contains("RIGHT")){
            velocity.x = 1;
        }
        if (currentlyActiveKeys.contains("UP")){
            velocity.y = -1;
        }
        if (currentlyActiveKeys.contains("DOWN")){
            velocity.y = 1;
        }

        //on released
        if(releasedKey.contains("LEFT") || releasedKey.contains("RIGHT")) {
            velocity.x = 0;
        }

        if (releasedKey.contains("UP") || releasedKey.contains("DOWN")){
            velocity.y = 0;
        }
    }
    public void setVel(int velX, int velY) {
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
    }

    private static void prepareActionHandlers()
    {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        releasedKey = new HashSet<String>();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
                releasedKey.clear();
            }

        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());
                releasedKey.add(event.getCode().toString());
            }
        });
    }
}
