package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends Entity {

    private int cd = 0;

    public static final double PLAYER_SPEED_NORMAL = 1;

    public static final double PLAYER_SPEED_BOOSTED = 1.5;

    private String checkStuck = "";
    private String twoFrameBackStuck = "";
    private String prevCheckStuck = "";

    private State state;

    private Rectangle nextFrameRect;

    private Paint pt;
    static HashSet<String> currentlyActiveKeys;
    static HashSet<String> releasedKey;

    private Vector2D velocity;
    public static double playerSpeed = 1.5;

    public int health;
    public Bomber(int x, int y, Image img) {
        super( x + 1, y, img);
        prepareActionHandlers();
        velocity = new Vector2D();
        rect.setWidth(30);
        rect.setHeight(30);
        nextFrameRect = new Rectangle(30,30);

    }

    @Override
    public void update() {

        //get input
        actionHandler();
        Grass tmp = new Grass();
        //nextFrame position

        if(cd > 0) cd--;

        handleMapCollision();
        handleItemCollision();

        if(velocity.x != 0 || velocity.y != 0){
            animated();
        }
        rect.setX(position.x);
        rect.setY(position.y);
        //System.out.println(currentlyActiveKeys);
    }

    private void handleItemCollision() {
        List<Item> toRemove = new ArrayList<>();


        for (Item entity : items) {
            if (entity.rect.intersects(position.x, position.y, rect.getWidth(), rect.getHeight())) {
                toRemove.add(entity);
                entity.doEffect();
            }

        }
        items.remove(toRemove);
    }

    public void animated(){
        this.timer++;
        if(timer > 100) timer = 0;
        switch (state){
            case DOWN:
                this.img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, this.timer, 20).getFxImage();
                break;
            case LEFT:
                this.img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, this.timer, 20).getFxImage();
                break;
            case UP:
                this.img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, this.timer, 20).getFxImage();
                break;
            default:
                this.img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, this.timer, 20).getFxImage();
                break;
        }
    }


    public void handleMapCollision() {
        //check if x or y cause the collision.

        nextFrameRect.setX(this.rect.getX() + velocity.x);
        nextFrameRect.setY(this.rect.getY());
        if(GameMap.checkCollision(nextFrameRect)) {
            checkStuck += "X";
        }
        else {
            position.x += velocity.x;
        }

        nextFrameRect.setX(this.rect.getX());
        nextFrameRect.setY(this.rect.getY() + velocity.y);

        if(GameMap.checkCollision(nextFrameRect)) {
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

        if(currentlyActiveKeys.isEmpty()){
            velocity.x = 0;
            velocity.y = 0;
            state = State.STOP;
        }
        if(currentlyActiveKeys.contains("A")) {
            currentlyActiveKeys.remove("A");
            Bomb.power++;
        }
        if(currentlyActiveKeys.contains("LEFT")) {
            velocity.x = -playerSpeed;
            state = State.LEFT;
        }
        if (currentlyActiveKeys.contains("RIGHT")){
            velocity.x = playerSpeed;
            state = State.RIGHT;
        }
        if (currentlyActiveKeys.contains("UP")){
            velocity.y = -playerSpeed;
            state = State.UP;
        }
        if (currentlyActiveKeys.contains("DOWN")){
            velocity.y = playerSpeed;
            state = State.DOWN;
        }
        if (currentlyActiveKeys.contains("SPACE") && cd == 0){
            currentlyActiveKeys.remove("SPACE");
            int x = (int) ((position.x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE);
            int y = (int) ((position.y + Sprite.DEFAULT_SIZE)/ Sprite.SCALED_SIZE);
            Entity bom = new Bomb(x, y, Sprite.bomb.getFxImage());
            bombs.add(bom);
            System.out.println(x + " " + y);
            cd = 0;
        }

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

    public void becomeChad() {
        this.img = Sprite.player_chad.getFxImage();
    }

    public void increaseBombRange() {

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

    public void setCd(int cd) {
        this.cd = cd;
    }

    public int getCd() {
        return cd;
    }

    public void setPlayerSpeed(double speed) {
        playerSpeed = speed;
    }
}
