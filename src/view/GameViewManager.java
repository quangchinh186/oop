package view;


import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.SKIN;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.weapon.Weapon;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.util.ArrayList;
import java.util.List;
import uet.oop.bomberman.sound.BgmManagement;

public class GameViewManager {
    private AnchorPane gamePane;
    public static Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private ImageView ship;

    private static final int GAME_WIDTH = 31 * 32;
    private static final int GAME_HEIGHT = 13 * 32;



    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private AnimationTimer gameTimer;

    public static int level = 1;

    //public static Scene scene;

    public static Bomber bomberman;

    public static long javaFxTicks = 0;

    private GraphicsContext gc;
    private Canvas canvas;


    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Entity> bombs = new ArrayList<>();
    public static List<Entity> visualEffects = new ArrayList<>();


    public static BgmManagement musicPlayer = new BgmManagement("res/music");

    public static boolean isPause = false;

    public static int score = 0;

    public GameViewManager() {
        initializeStage();
    }


    /**
     * create things for MAINGAME
     */
    private void initializeStage() {

        gameStage = new Stage();

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        gameScene = new Scene(root);

        // Them scene vao stage
        gameStage.setScene(gameScene);
        gameStage.show();



        //use this for main file
    }


    private void createKeysListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {

                } else if (event.getCode() == KeyCode.RIGHT) {

                }
                if (event.getCode() == KeyCode.SPACE) {

                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    //isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    //isRightKeyPressed = false;
                }
                if (event.getCode() == KeyCode.SPACE) {
                    //isSpacePressed = false;
                    //isBulletFired = false;
                }

            }
        });

    }

    /**
     * hide menuStage and create gameStage
     */

    public void createNewGame(Stage menuStage, SKIN chosenSkin) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        //createBG();

        /**
         * use this thing to make map, bomber, and other entities.
         */
        createPlayer(chosenSkin); //create new bomberman
        createGameElements();

        //the game loop.
        createGameLoop();
        gameStage.show();

    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        gameTimer.start();
    }

    private void createGameElements() {
        GameMap.createMap(level);

    }

    private void createPlayer(SKIN chosenSkin) {
        bomberman = new Bomber(1,1, chosenSkin.getSheetUrl());
        //entities.add(bomberman);
    }


    //from oldMain
    public void update() {

        bomberman.update();
        javaFxTicks++;

        bombs.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        if(bomberman.getCd() == 0 && !(bombs.isEmpty())) {
            Bomb b = (Bomb) bombs.get(0);
            if(b.getTime() == 0){
                visualEffects.removeAll(b.getVisual());
                bombs.remove(b);
            }
        }

        enemies.forEach(Entity::update);
        items.forEach(Entity::update);


        visualEffects.forEach(Entity::update);


        clearInactiveEntity(visualEffects);
        //clearInactiveEntity(stillObjects);
        //remove projectile
        //nen de rieng or lam chung voi visual effect.

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        bomberman.render(gc);
        items.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        visualEffects.forEach(g -> g.render(gc));

    }

    public static void clearInactiveEntity(List<Entity> lst) {
        List<Entity> toRemove = new ArrayList<>();
        for(Entity entity : lst) {
            if(entity.isActive() == false) {
                toRemove.add(entity);
            }
        }
        lst.removeAll(toRemove);
    }

    public static void clearInactiveItem(List<Item> lst) {
        List<Entity> toRemove = new ArrayList<>();
        for(Entity entity : lst) {
            if(entity.isActive() == false) {
                toRemove.add(entity);
            }
        }
        lst.removeAll(toRemove);
    }

    public static void clearInactiveWeapon(List<Weapon> lst) {
        List<Entity> toRemove = new ArrayList<>();
        for(Entity entity : lst) {
            if(entity.isActive() == false) {
                toRemove.add(entity);
            }
        }
        lst.removeAll(toRemove);
    }

    public static long getJavaFxTicks() {
        return javaFxTicks;
    }

}
