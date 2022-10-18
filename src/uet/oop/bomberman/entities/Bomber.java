package uet.oop.bomberman.entities;

import javafx.util.Pair;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
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
import java.util.function.Predicate;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends Entity {
    private int cd = 0;
    public static final double PLAYER_SPEED_NORMAL = 1;
    public static final double PLAYER_SPEED_BOOSTED = 1.5;
    private String checkStuck = "";
    private String twoFrameBackStuck = "";
    private String prevCheckStuck = "";
    private Rectangle nextFrameRect;
    int spawnX, spawnY;
    static HashSet<String> currentlyActiveKeys;
    static HashSet<String> releasedKey;
    private Vector2D velocity;
    public static double playerSpeed = 1.5;
    public int lives;
    public int bombX = 0, bombY = 0;
    private boolean atPortal;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        lives = 3;
        spawnX = x;
        spawnY = y;
        prepareActionHandlers();
        velocity = new Vector2D();
        nextFrameRect = new Rectangle(30,30);
        state = State.RIGHT;
        atPortal = false;
    }

    @Override
    public void update() {
        //get input
        x = (int) ((position.x + 8) / Sprite.SCALED_SIZE);
        y = (int) ((position.y + 8) / Sprite.SCALED_SIZE);
        actionHandler();
        if(cd > 0) cd--;
        handleMapCollision();
        handleItemCollision();
        if(velocity.x != 0 || velocity.y != 0){
            animated();
            rect.setX(position.x);
            rect.setY(position.y);
        }
        if(state == State.DIE && timer % 16 != 0){
            animated();
        }
        if(!this.rect.intersects(bombX * 32, bombY * 32, 30, 30)){
            GameMap.occupyBlock(bombX, bombY);
            bombX = 0;
            bombY = 0;
        }
    }

    public void animated(){
        timer = timer > Sprite.DEFAULT_SIZE ? 0 : timer+1;
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
                entity.doEffect();
                if(!(entity instanceof Portal)){
                    toRemove.add(entity);
                }
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
        twoFrameBackStuck = prevCheckStuck;
        prevCheckStuck = checkStuck;
        checkStuck = "";
    }

    public void actionHandler () {
        if(isPause){
            if(currentlyActiveKeys.isEmpty()) return;
            else isPause = false;
        }

        if(state != State.DIE){
            if(currentlyActiveKeys.isEmpty()){
                velocity.x = 0;
                velocity.y = 0;
            }
            if(currentlyActiveKeys.contains("ESCAPE")){
                currentlyActiveKeys.remove("ESCAPE");
                isPause = true;
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
            if (currentlyActiveKeys.contains("K")){
                currentlyActiveKeys.remove("K");
                createProjectile();
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
                    bombX = x;
                    bombY = y;
                    cd = 100;
               }
            }
        } else {
            if(currentlyActiveKeys.contains("SPACE")){
                currentlyActiveKeys.remove("SPACE");
                revive();
            }
        }
    }

    private void createProjectile() {

        Vector2D direction = new Vector2D(0,0);
        switch(state) {
            case UP:
                direction.y = -3;
                break;
            case DOWN:y:
                // code block
                direction.y = 3;
                break;
            case LEFT:
                // code block
                direction.x = -3;
                break;
            case RIGHT:
                // code block
                direction.x = 3;
                break;
            default:
                // code block
        }

        Projectile pj = new Projectile((int)position.x/Sprite.SCALED_SIZE, (int)position.y/Sprite.SCALED_SIZE,
                Sprite.minvo_right2.getFxImage(), direction);
        visualEffects.add(pj);
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void becomeChad() {
        this.img = Sprite.player_chad.getFxImage();
    }

    public void revive(){
        state = State.RIGHT;
        this.img = Sprite.player_right.getFxImage();
        this.s1 = Sprite.player_right;
        this.s2 = Sprite.player_right_1;
        this.s3 = Sprite.player_right_2;
        position.x = spawnX * Sprite.SCALED_SIZE;
        position.y = spawnY * Sprite.SCALED_SIZE;
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

    public void increaseBombRange() {
        Bomb.increasePower();
    }
    public void die(){
        timer = 1;
        state = State.DIE;
        velocity.x = 0;
        velocity.y = 0;
    }

    public boolean isAtPortal() {
        return atPortal;
    }

    public void setAtPortal(boolean atPortal) {
        this.atPortal = atPortal;
    }
}

