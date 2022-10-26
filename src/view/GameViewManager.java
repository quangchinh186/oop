package view;


import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.SKIN;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.Weapon;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;
import uet.oop.bomberman.states.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


    public static List<Enemy> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Entity> bombs = new ArrayList<>();
    public static List<Entity> visualEffects = new ArrayList<>();


    public static BgmManagement musicPlayer = new BgmManagement("res/music");

    public static boolean isPause = false;

    public static int score = 0;
    public static int highScore;
    Text music;
    Text hp;
    Button p = new Button("play");
    Button s = new Button("stop");
    Button n = new Button(">>");
    Button b = new Button("<<");
    Button up = new Button("v+");
    Button down = new Button("v-");

    public GameViewManager() {
        initializeStage();
    }

    public void setButton(Group root){
        p.setFocusTraversable(false);
        s.setFocusTraversable(false);
        n.setFocusTraversable(false);
        b.setFocusTraversable(false);
        up.setFocusTraversable(false);
        down.setFocusTraversable(false);
        p.setLayoutX(0*30); p.setLayoutY(14*33);
        s.setLayoutX(1*30); s.setLayoutY(14*33);
        b.setLayoutX(2*30); b.setLayoutY(14*33);
        n.setLayoutX(3*30); n.setLayoutY(14*33);
        down.setLayoutX(4*30); down.setLayoutY(14*33);
        up.setLayoutX(5*30); up.setLayoutY(14*33);

        p.setOnAction(event->{
            musicPlayer.play();
        });
        s.setOnAction(event->{
            musicPlayer.pause();
        });
        n.setOnAction(event->{
            musicPlayer.next();
        });
        b.setOnAction(event->{
            musicPlayer.prev();
        });
        up.setOnAction(event->{
            musicPlayer.changeVolume("up");
        });
        down.setOnAction(event->{
            musicPlayer.changeVolume("down");
        });

        root.getChildren().add(p);
        root.getChildren().add(s);
        root.getChildren().add(n);
        root.getChildren().add(b);
        root.getChildren().add(up);
        root.getChildren().add(down);
    }
    /**
     * create things for MAINGAME
     */
    private void initializeStage() {
        gameStage = new Stage();
        try {
            File file = new File("highScore.txt");
            Scanner scanner = new Scanner(file);
            highScore = scanner.nextInt();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        musicPlayer.play();
        music = new Text();
        music.setText("Now playing: " + musicPlayer.getNow() + "\tLevel " + level);
        music.setX(0);
        music.setY(14*31);
        hp = new Text();
        hp.setText("Heart left: " + 4 + "\tBomb to use: " + 1 + "\tScore: " + score);
        hp.setY(14*32);

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT+2));
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(music);
        root.getChildren().add(hp);
        setButton(root);

        // Tao scene
        gameScene = new Scene(root);
        gameScene.setFill(Color.web("#81c483"));
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
    }


    //from oldMain
    public void update() {
        music.setText("Now playing: " + musicPlayer.getNow() + "\tLevel " + level);
        hp.setText("Heart left: " + bomberman.lives + "\tBomb to use: " + bomberman.getBombNumbers() + "\tScore: " + score);
        bomberman.update();
        bombs.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        if(!(bombs.isEmpty())) {
            Bomb b = (Bomb) bombs.get(0);
            if(b.getTime() == 0){
                visualEffects.removeAll(b.getVisual());
                GameMap.updateMap(b.x, b.y);
                if(b.whom.equals("Bomber")){
                    bomberman.setBombNumbers(bomberman.getBombNumbers()+1);
                }
                bombs.remove(b);
            }
        }
        for (Enemy e : entities)
        {
            e.update();
            if(e.getState() == uet.oop.bomberman.states.State.DIE){
                e.update();
                entities.remove(e);
                break;
            }
        }
        items.forEach(Entity::update);
        visualEffects.forEach(Entity::update);

        if(bomberman.isAtPortal() && entities.isEmpty()){
            newLevel();
            System.out.println("Level: " + level);
        }

        clearInactiveEntity(visualEffects);

        //remove projectile
        //nen de rieng or lam chung voi visual effect.



    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));



        items.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        visualEffects.forEach(g -> g.render(gc));
        bomberman.render(gc);
    }


    public void newLevel(){
        entities.clear();
        bombs.clear();
        visualEffects.clear();
        items.clear();
        bomberman.revive();
        level++;
        GameMap.createMap(level);
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
