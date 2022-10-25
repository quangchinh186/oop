package uet.oop.bomberman.entities;

import com.fasterxml.jackson.core.JsonToken;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.weapon.Weapon;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;
import uet.oop.bomberman.map.GameMap;
import view.GameViewManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TimerTask;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.SpriteSheet.*;
import static view.GameViewManager.*;

public class Bomber extends Entity {
    private int cd = 1;


    private int hp = 3;

    private static Image playerSheet = new Image("/textures/udlf.png", 32 * 3, 32 * 4,
            true, true);

    public static final double PLAYER_SPEED_NORMAL = 1;

    public static final double PLAYER_SPEED_BOOSTED = 1.5;

    private boolean isImmuned = false;
    private String checkStuck = "";
    private String twoFrameBackStuck = "";
    private String prevCheckStuck = "";

    private State state = State.RIGHT;

    private Rectangle nextFrameRect;

    static HashSet<String> currentlyActiveKeys;

    //dung de luu weapons

    public static HashSet<String> weaponsSet = new HashSet<>();
    public static List<Weapon> weapons = new ArrayList<>() {
    };
    //

    static HashSet<String> releasedKey;

    private Vector2D velocity;
    public static double playerSpeed = 1.5;

    public int health;
    public Bomber(int x, int y, Image img) {


        super( x + 1, y, img);
        isAnimated = true;
        prepareActionHandlers();
        velocity = new Vector2D();
        rect.setWidth(30);
        rect.setHeight(30);
        nextFrameRect = new Rectangle(30,30);

        spriteSheet = playerSheet;
        weapons.clear();
        initAnimation();
    }


    public Bomber(int x, int y, String sheetUrl) {


        super( x + 1, y, Sprite.player_right.getFxImage());
        isAnimated = true;
        prepareActionHandlers();
        velocity = new Vector2D();
        rect.setWidth(30);
        rect.setHeight(30);
        nextFrameRect = new Rectangle(30,30);
        weapons.clear();
        initAnimation();

        spriteSheet = new Image(sheetUrl,32 * 4, 32 * 3, true, true);
    }





    public void initAnimation() {
        Animation walkUp = new Animation(0, 3, 100);
        Animation walkDown = new Animation(1, 3, 100);
        Animation walkLeft = new Animation(2, 3, 100);
        Animation walkRight = new Animation(3, 3, 100);
        Animation die = new Animation(4, 3, 100);
        Animation idleUp = new Animation(0, 1, 100);
        Animation idleDown = new Animation(1, 1, 100);
        Animation idleLeft = new Animation(2, 1, 100);
        Animation idleRight = new Animation(3, 1, 100);

        animations.put("WalkUp", walkUp);
        animations.put("WalkDown", walkDown);
        animations.put("WalkLeft", walkLeft);
        animations.put("WalkRight", walkRight);
        animations.put("Die", die);
        //
        animations.put("IdleUp", idleUp);
        animations.put("IdleDown", idleDown);
        animations.put("IdleLeft", idleLeft);
        animations.put("IdleRight", idleRight);
    }

    @Override
    public void update() {

        //get input
        actionHandler();

        if(cd > 0) cd--;

        handleMapCollision();
        handleItemCollision();
        handleDamageCollision();
        //update weapons.
        weapons.forEach(Entity::update);

        if(velocity.x != 0 || velocity.y != 0){
            animated();
        }
        rect.setX(position.x);
        rect.setY(position.y);
        //System.out.println(currentlyActiveKeys);
    }

    private void handleDamageCollision() {

        for(Entity et : visualEffects) {
            if(et.rect.intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight())) {
                if(et instanceof Flames || et instanceof Projectile) {
                    if(!isImmuned) {

                        System.out.println("Va chung sat thuong");
                        hp--;
                        setImmuned(true);
                        jTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                setImmuned(false);
                            }
                        },1000);
                    }
                }

                et.setInactive();
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);

        weapons.forEach(g -> g.render(gc));
    }

    public void animated(){

        int sheetX = 0, sheetY = 0;

        timer = timer > Sprite.DEFAULT_SIZE ? 0 : timer+1;

        switch (state){
            case DOWN:
                play("WalkDown");
                break;
            case LEFT:
                play("WalkLeft");
                break;
            case UP:
                play("WalkUp");
                break;
            case DIE:
                break;
            case RIGHT:
                play("WalkRight");
                break;
            default:
                break;

        }



        /**
         * else {
         *             this.img = Sprite.movingSprite(s1, s2, s3, this.timer, Sprite.DEFAULT_SIZE).getFxImage();
         *         }
         */


        /**
         *         this.img = Sprite.movingSpriteSheet(playerSheet, sheetX, sheetY, 3,
         *             this.timer, Sprite.DEFAULT_SIZE).getFxImage();
         */

    }

    private void handleItemCollision() {
        List<Item> toRemove = new ArrayList<>();
        for(Item entity : items) {
            if(entity.rect.intersects(position.x, position.y, rect.getWidth(), rect.getHeight())) {
                entity.doEffect();
            }
        }

        int countArmed = 0;

        for(Weapon wp : weapons) {

            if(wp.rect.intersects(position.x, position.y, rect.getWidth(), rect.getHeight())) {
                wp.doEffect();
            }
            if(wp.isArmed()) countArmed++;

        }

        if(countArmed > 1) {
            for(int i = 0; i < countArmed - 1; i++) {
                weapons.get(i).setInactive();
            }
        }

        GameViewManager.clearInactiveItem(items);

        GameViewManager.clearInactiveWeapon(weapons);



    }

    public void addWeapons(String key, Item wp) {
            //replace weapon voi key.
        if(!weapons.isEmpty()) {
            for(Item item : items) {
                if(item instanceof Weapon && ((Weapon) item).isArmed()) {
                    item.setInactive();
                    break;
                }
            }
            weapons.clear();
        }
        weapons.add((Weapon) wp);
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
            //state = State.STOP;
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
            cd = 0;
        }
        if (currentlyActiveKeys.contains("K")){
            currentlyActiveKeys.remove("K");
            //
            for(Weapon wp : weapons) {
                if(wp.isArmed()) {
                    wp.useWeapon();
                    break;
                }
            }
        }

        if(releasedKey.contains("LEFT") ) {
            velocity.x = 0;
            state = state.LEFT;
        }
        if(releasedKey.contains("RIGHT") ) {
            velocity.x = 0;
            state = state.RIGHT;
        }

        if(releasedKey.contains("UP") ) {
            velocity.y = 0;
            state = state.UP;
        }
        if(releasedKey.contains("DOWN") ) {
            velocity.y = 0;
            state = state.DOWN;
        }

        if(currentlyActiveKeys.isEmpty()) {
            switch (state){
                case DOWN:
                    play("IdleDown");
                    break;
                case LEFT:
                    play("IdleLeft");
                    break;
                case UP:
                    play("IdleUp");
                    break;
                case DIE:
                    break;
                case RIGHT:
                    play("IdleRight");
                    break;
                default:
                    break;
            }
        }

    }



    public void setVel(int velX, int velY) {
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
    }

    public void becomeChad() {
        state = State.CHAD;
        spriteSheet = middleEastTiles;
        setImmuned(true);
        jTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setImmuned(false);
                spriteSheet = playerSheet;
            }
        },10000);
    }


    private static void prepareActionHandlers()
    {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        releasedKey = new HashSet<String>();
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
                releasedKey.clear();
            }

        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());
                releasedKey.add(event.getCode().toString());
            }
        });
    }


    public Vector2D getPosition() { return this.position; }
    public void setCd(int cd) {
        this.cd = cd;
    }

    public int getCd() {
        return cd;
    }

    public void setImmuned(boolean immuned) {
        isImmuned = immuned;
    }



    public void setPlayerSpeed(double speed) {
        playerSpeed = speed;
    }

    public void increaseBombRange() {
        Bomb.increasePower();
    }

    public State getState() {
        return state;
    }
}