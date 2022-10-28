package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

import uet.oop.bomberman.entities.item.weapon.Weapon;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.entities.tiles.Portal;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.physics.Vector2D;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;
import view.GameViewManager;
import uet.oop.bomberman.timer.GTimer;

import java.util.*;

import static view.GameViewManager.*;

public class Bomber extends Entity {
    private int bombNumbers = 1;
    private Sound deadNoise;
    public static final double PLAYER_SPEED_NORMAL = 1;
    public static final double PLAYER_SPEED_BOOSTED = 1.5;
    private String checkStuck = "";
    private String twoFrameBackStuck = "";
    private String prevCheckStuck = "";
    private Rectangle nextFrameRect;
    int spawnX, spawnY;

    private Timer jTimer = new Timer();
    public static HashSet<String> currentlyActiveKeys;
    public static HashSet<String> releasedKey;
    private Vector2D velocity;
    public static double playerSpeed = 1.5;
    public int lives;
    private boolean atPortal;

    public HashMap<String, Animation> animations = new HashMap<>();


    private static Image playerSheet = new Image("/textures/bombersheet.png", 32 * 5, 32 * 5,
            true, true);

    private static Image ricardoSheet = new Image("/textures/ricardo_sheet.png", 13600/6.25, 32,
            true, true);

    private static Image emptySheet = new Image("/textures/empty.png");

    private static Image chosenSheet = new Image("/textures/empty.png");


    public static List<Weapon> weapons = new ArrayList<>();

    public int frames = 1;
    public int speed = 100;

    public int animIndex = 0;
    private static Image spriteSheet;
    private boolean isImmune = false;

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
        deadNoise = new Sound("res/sfx/oof.wav");
    }

    public Bomber(int x, int y, String Url) {
        super(x, y, Sprite.player_right.getFxImage());
        lives = 3;
        spawnX = x;
        spawnY = y;


        chosenSheet = new Image(Url,32 * 5, 32 * 5, true, true);
        spriteSheet = chosenSheet;
        initAnimation();
        //
        prepareActionHandlers();
        velocity = new Vector2D();
        nextFrameRect = new Rectangle(30,30);
        state = State.RIGHT;
        atPortal = false;
        deadNoise = new Sound("res/sfx/oof.wav");
    }



    public void initAnimation() {



        Animation walkUp = new Animation(0, 3, 10);
        Animation walkDown = new Animation(1, 3, 10);
        Animation walkLeft = new Animation(2, 3, 10);
        Animation walkRight = new Animation(3, 3, 10);
        Animation die = new Animation(4, 5, 10);
        Animation idleUp = new Animation(0, 1, 10);
        Animation idleDown = new Animation(1, 1, 10);
        Animation idleLeft = new Animation(2, 1, 10);
        Animation idleRight = new Animation(3, 1, 10);

        //ricardo animation
        Animation dancing = new Animation(0, 68, 5);

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

        //dancing baby
        animations.put("Dancing", dancing);
    }

    @Override
    public void update() {

        //get input
        x = (int) ((position.x + 8) / Sprite.SCALED_SIZE);
        y = (int) ((position.y + 8) / Sprite.SCALED_SIZE);
        actionHandler();
        handleMapCollision();
        handleItemCollision();

        weapons.forEach(Entity::update);

        if(velocity.x != 0 || velocity.y != 0 || spriteSheet.equals(ricardoSheet)){
            animated();
            rect.setX(position.x);
            rect.setY(position.y);
        }
        if(state == State.DIE){
            dieAnimation();
        }
        for (Entity b : bombs) {
            if(!this.rect.intersects(b.x * 32, b.y * 32, 30, 30)){
                GameMap.occupyBlock(b.x, b.y);
            }
        }

    }
    public void dieAnimation(){
        play("Die");
        timer = timer < 15 ? timer+1 : 15;
        if(timer == 15){
            this.img = null;
            //isPause = true;
            spriteSheet = emptySheet;


        }
    }

    public void animated(){
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
            case RIGHT:
                play("WalkRight");
                break;
            case DIE:
                play("Die");
            default:
                break;

        }


        if(spriteSheet.equals(ricardoSheet)) {
            play("Dancing");
        }

    }

    private void handleItemCollision() {
        List<Item> toRemove = new ArrayList<>();
        //System.out.println(items.size());
        for(Item entity : items) {
            if(entity.rect.intersects(position.x, position.y, rect.getWidth(), rect.getHeight())) {
                entity.doEffect();
                if(!(entity instanceof Portal)){
                    toRemove.add(entity);
                }
            }
        }
        items.removeAll(toRemove);


        int countArmed = 0;

        for(Weapon wp : weapons) {

            if(wp.rect.intersects(position.x, position.y, rect.getWidth(), rect.getHeight())) {
                wp.doEffect();
            }
            if(wp.isArmed()) countArmed++;

        }

        if(countArmed > 1) {
            for(int i = 0; i < countArmed - 1; i++) {
                if(countArmed > 1) {
                    weapons.get(i).setInactive();
                    countArmed--;
                }
            }
        }

        GameViewManager.clearInactiveItem(items);

        GameViewManager.clearInactiveWeapon(weapons);


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
            if(currentlyActiveKeys.isEmpty()) {
            }
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
                bombNumbers++;
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
                //
                for(Weapon wp : weapons) {
                    if(wp.isArmed()) {
                        wp.useWeapon();
                        break;
                    }
                }
            }

            //mp3 key input test
            if (currentlyActiveKeys.contains("Q")){
                currentlyActiveKeys.remove("Q");
                musicPlayer.play();
                System.out.println("now playing: " + musicPlayer.getNow());
            }
            if (currentlyActiveKeys.contains("W")){
                currentlyActiveKeys.remove("W");
                musicPlayer.pause();
            }
            if (currentlyActiveKeys.contains("E")){
                currentlyActiveKeys.remove("E");
                musicPlayer.next();
                System.out.println("now playing: " + musicPlayer.getNow());
            }
            if (currentlyActiveKeys.contains("R")){
                currentlyActiveKeys.remove("R");
                musicPlayer.prev();
                System.out.println("now playing: " + musicPlayer.getNow());
            }
            if (currentlyActiveKeys.contains("U")){
                currentlyActiveKeys.remove("U");
                musicPlayer.changeVolume("up");
            }
            if (currentlyActiveKeys.contains("I")){
                currentlyActiveKeys.remove("I");
                musicPlayer.changeVolume("down");
            }
            //end of test
            if (currentlyActiveKeys.contains("SPACE") && bombNumbers != 0){
                currentlyActiveKeys.remove("SPACE");
                int x = (int) ((position.x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE);
                int y = (int) ((position.y + Sprite.DEFAULT_SIZE)/ Sprite.SCALED_SIZE);
                Entity bom = new Bomb(x, y, Sprite.bomb.getFxImage(), "Bomber");
                boolean check = true;
                for(int i = 0; i < bombs.size(); i++){
                    if((bombs.get(i).equals(bom))){
                        check = false;
                        break;
                    }
                }
                if(check){
                    bombs.add(bom);
                    bombNumbers--;
                }
            }
        } else {
            if(currentlyActiveKeys.contains("SPACE")){
                currentlyActiveKeys.remove("SPACE");
                revive();
                spriteSheet = chosenSheet;
            }
        }

        if(releasedKey.contains("UP")) {
            play("IdleUp");

        }

        if(releasedKey.contains("DOWN")) {
            play("IdleDown");
        }

        if(releasedKey.contains("LEFT")) {
            play("IdleLeft");
        }

        if(releasedKey.contains("RIGHT")) {
            play("IdleRight");
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

    public void becomeChad() {
        state = State.CHAD;
        spriteSheet = ricardoSheet;
        setImmune(true);
        jTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setImmune(false);
                spriteSheet = chosenSheet;
                state = State.RIGHT;
            }
        },10000);
    }

    public boolean isImmune() {
        return isImmune;
    }

    private void setImmune(boolean b) {
        isImmune = b;
    }


    public void revive(){
        state = State.RIGHT;
        this.img = Sprite.player_right.getFxImage();
        this.s1 = Sprite.player_right;
        this.s2 = Sprite.player_right_1;
        this.s3 = Sprite.player_right_2;
        position.x = spawnX * Sprite.SCALED_SIZE;
        position.y = spawnY * Sprite.SCALED_SIZE;
        lives--;
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

    public void setBombNumbers(int bombNumbers) {
        this.bombNumbers = bombNumbers;
    }

    public int getBombNumbers() {
        return bombNumbers;
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
        play("Die");
        velocity.x = 0;
        velocity.y = 0;
        deadNoise.play();
        //lives--;
        System.out.println(lives);
    }

    public boolean isAtPortal() {
        return atPortal;
    }

    public void setAtPortal(boolean atPortal) {
        this.atPortal = atPortal;
    }

    public void play(String id) {
        frames = animations.get(id).frames;
        speed = animations.get(id).speed;
        animIndex = animations.get(id).index;
    }


    @Override
    public void render(GraphicsContext gc) {



        int srcX = 0;

        int frameSpeed = speed == 0 ? (GTimer.ticksInASeconds / 6) : speed;
        //                                                                               speed ,  % frames.
        srcX = (Sprite.SCALED_SIZE * (int) ( (GameViewManager.getJavaFxTicks() / frameSpeed) % frames));
        gc.drawImage(spriteSheet,srcX,animIndex * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE,
                position.x, position.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);


        weapons.forEach(g -> g.render(gc));
    }



}
