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
    int spawnX, spawnY;
    static HashSet<String> currentlyActiveKeys;
    static HashSet<String> releasedKey;

    private Vector2D velocity;
    public static double playerSpeed = 1.5;

    public int health;
    public Bomber(int x, int y, Image img) {
        super( x + 1 , y, img);
        spawnX = x+1;
        spawnY = y;
        prepareActionHandlers();
        velocity = new Vector2D();
        rect.setWidth(30);
        rect.setHeight(30);
        nextFrameRect = new Rectangle(30,30);
        state = State.STOP;
    }

    @Override
    public void update() {

        //get input
        actionHandler();
        if(cd > 0) cd--;
        handleMapCollision();
        handleItemCollision();

        if(state != State.STOP){
            animated();
        }

        rect.setX(position.x);
        rect.setY(position.y);
    }

    public void animated(){
        this.timer++;
        if(timer > 100) timer = 0;
        switch (state){
            case DOWN:
                this.s1 = Sprite.player_down;
                this.s2 = Sprite.player_down_1;
                this.s3 = Sprite.player_down_2;
                break;
            case LEFT:
                this.s1 = Sprite.player_left;
                this.s2 = Sprite.player_left_1;
                this.s3 = Sprite.player_left_2;
                break;
            case UP:
                this.s1 = Sprite.player_up;
                this.s2 = Sprite.player_up_1;
                this.s3 = Sprite.player_up_2;
                break;
            case DIE:
                this.s1 = Sprite.player_dead1;
                this.s2 = Sprite.player_dead2;
                this.s3 = Sprite.player_dead3;
                break;
            default:
                this.s1 = Sprite.player_right;
                this.s2 = Sprite.player_right_1;
                this.s3 = Sprite.player_right_2;
                break;
        }

        this.img = Sprite.movingSprite(s1, s2, s3, this.timer, Sprite.DEFAULT_SIZE).getFxImage();
    }

    private void handleItemCollision() {
        List<Item> toRemove = new ArrayList<>();
        for(Item entity : items) {
            if(entity.rect.intersects(position.x, position.y, rect.getWidth(), rect.getHeight())) {
                //entity.doEffect();
                toRemove.add(entity);
            }
        }
        items.removeAll(toRemove);
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

        if(state != State.DIE){
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
                if(state == State.LEFT){
                    velocity.y = 0;
                }
                velocity.x = -playerSpeed;
                state = State.LEFT;
            }
            if (currentlyActiveKeys.contains("RIGHT")){
                if(state == State.RIGHT){
                    velocity.y = 0;
                }
                velocity.x = playerSpeed;
                state = State.RIGHT;
            }
            if (currentlyActiveKeys.contains("UP")){
                if(state == State.UP){
                    velocity.x = 0;
                }
                velocity.y = -playerSpeed;
                state = State.UP;
            }
            if (currentlyActiveKeys.contains("DOWN")){
                if(state == State.DOWN){
                    velocity.x = 0;
                }
                velocity.y = playerSpeed;
                state = State.DOWN;
            }
            if (currentlyActiveKeys.contains("SPACE") && cd == 0){
                currentlyActiveKeys.remove("SPACE");
                int x = (int) ((position.x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE);
                int y = (int) ((position.y + Sprite.DEFAULT_SIZE)/ Sprite.SCALED_SIZE);
                Entity bom = new Bomb(x, y, Sprite.bomb.getFxImage());
                boolean check = true;

                for(int i = 0; i < bombs.size(); i++){
                    if((bombs.get(i).equals(bom))){
                        check = false;
                        break;
                    }
                }
                if(check){
                    bombs.add(bom);
                    cd = 0;
                    System.out.println(x + " " + y);
                }
            }
            //if(releasedKey.contains("LEFT") || )
        }
    }
    public double getX() {
        return this.position.x;
    }
    public double getY() {
        return this.position.y;
    }

    public void becomeChad() {
        this.img = Sprite.player_chad.getFxImage();
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

    public void die(){
        state = State.DIE;
        velocity.x = 0;
        velocity.y = 0;
        //position.x = spawnX * Sprite.SCALED_SIZE;
        //position.y = spawnY * Sprite.SCALED_SIZE;
    }
}
