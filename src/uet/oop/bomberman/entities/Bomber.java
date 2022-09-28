package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.map.GameMap;

import java.awt.*;
import java.util.HashSet;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends Entity {


    private String checkStuck = "";

    private String twoFrameBackStuck = "";
    private String prevCheckStuck = "";
    private Rectangle nextFrameRect;

    private Paint pt;
    static HashSet<String> currentlyActiveKeys;
    static HashSet<String> releasedKey;

    private Vector2D velocity;

    public int health;
    public Bomber(int x, int y, Image img) {
        super( x + 1, y, img);
        prepareActionHandlers();
        velocity = new Vector2D();
        rect.setWidth(31);
        rect.setHeight(31);
        nextFrameRect = new Rectangle(30,30);

    }

    @Override
    public void update() {

        //get input
        actionHandler();
        Grass tmp = new Grass();
        //nextFrame position

        handleCollision();

        //

        //update pos sau khi nhan va cham

        rect.setX(position.x);
        rect.setY(position.y);

        //System.out.println(rect.toString());

    }


    public void handleCollision() {

        //check if x or y cause the collision.

        nextFrameRect.setX(this.rect.getX() + velocity.x);
        nextFrameRect.setY(this.rect.getY());
        if(GameMap.checkCollision(nextFrameRect)) {
            //System.out.println("COLLIDED X");
            checkStuck += "X";
        }
        else {

            position.x += velocity.x;
        }

        nextFrameRect.setX(this.rect.getX());
        nextFrameRect.setY(this.rect.getY() + velocity.y);

        if(GameMap.checkCollision(nextFrameRect)) {
            //System.out.println("COLLIDED Y");
            checkStuck += "Y";
        }
        else {
            position.y += velocity.y;
        }

        if(checkStuck.equals("XY")) {
            //only work for x-> travel.
            //if previous la collideX

            if(twoFrameBackStuck.equals("X")) {
                position.y -= velocity.y;
            }
            else if(twoFrameBackStuck.equals("Y")) {
                position.x -= velocity.x;
            }
        }

        //System.out.println(twoFrameBackStuck + "," + prevCheckStuck + "," + checkStuck);
        twoFrameBackStuck = prevCheckStuck;
        prevCheckStuck = checkStuck;
        checkStuck = "";
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
