package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.Weapon;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;
import uet.oop.bomberman.map.GameMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.SpriteSheet.gTiles;
import static uet.oop.bomberman.graphics.SpriteSheet.tiles;

public class Bomber extends Entity {
    private int cd = 0;
    private Sprite s1, s2, s3;


    private SpriteSheet playerSheet;

    public static final double PLAYER_SPEED_NORMAL = 1;

    public static final double PLAYER_SPEED_BOOSTED = 1.5;

    private boolean poweredUp = false;
    private String checkStuck = "";
    private String twoFrameBackStuck = "";
    private String prevCheckStuck = "";

    private State state = State.RIGHT;

    private Rectangle nextFrameRect;

    private Paint pt;
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
        prepareActionHandlers();
        velocity = new Vector2D();
        rect.setWidth(30);
        rect.setHeight(30);
        nextFrameRect = new Rectangle(30,30);

        playerSheet = tiles;

        weapons.clear();

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
        //update weapons.
        weapons.forEach(Entity::update);

        if(velocity.x != 0 || velocity.y != 0){
            animated();
        }
        rect.setX(position.x);
        rect.setY(position.y);
        //System.out.println(currentlyActiveKeys);
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
                sheetY = 0;
                sheetX = 2;
                break;
            case LEFT:
                sheetY = 0;
                sheetX = 3;
                break;
            case UP:
                sheetY = 0;
                sheetX = 0;
                break;
            case DIE:
                break;
            default:
                sheetY = 0;
                sheetX = 1;
                break;
        }

        /**
         * else {
         *             this.img = Sprite.movingSprite(s1, s2, s3, this.timer, Sprite.DEFAULT_SIZE).getFxImage();
         *         }
         */


        this.img = Sprite.movingSpriteSheet(playerSheet, sheetX, sheetY, 3,
            this.timer, Sprite.DEFAULT_SIZE).getFxImage();

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

        BombermanGame.clearInactiveItem(items);

        BombermanGame.clearInactiveWeapon(weapons);



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
            System.out.println(x + " " + y);
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


    }



    public void setVel(int velX, int velY) {
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
    }

    public void becomeChad() {
        state = State.CHAD;
        playerSheet = gTiles;
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


    public Vector2D getPosition() { return this.position; }
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

    public State getState() {
        return state;
    }
}