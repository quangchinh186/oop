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

public class Bomber extends Entity {
    private Rectangle rect;

    private Paint pt;
    static HashSet<String> currentlyActiveKeys;
    static HashSet<String> releasedKey;

    private Vector2D velocity;

    public int health;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        prepareActionHandlers();
        velocity = new Vector2D();


    }

    @Override
    public void update() {
        //System.out.println(this.x);
        //this.x++;
        actionHandler();
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
        BombermanGame.scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
                releasedKey.clear();
            }

        });
        BombermanGame.scene.setOnKeyReleased(new EventHandler<KeyEvent>()
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
