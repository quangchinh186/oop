package view;


import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameButton;
import model.GameSubScene;
import model.SKIN;
import uet.oop.bomberman.sound.BgmManagement;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.weapon.Weapon;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


import static uet.oop.bomberman.entities.Bomber.currentlyActiveKeys;

public class GameViewManager {

    private int maxLv = 2;

    private AnchorPane gamePane;
    public static Scene gameScene;
    private Stage gameStage;


    Timer jTimer = new Timer();
    private Stage menuStage;

    List<GameButton> pauseMenuButtons = new ArrayList<>();


    private GameSubScene sceneToHide;
    private static final int GAME_WIDTH = 31 * 32;
    private static final int GAME_HEIGHT = 13 * 32;

    private GameSubScene pauseSubScene;

    private GameSubScene gameOverSubScene;

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private AnimationTimer gameTimer;

    public static int level = 2;

    //public static Scene scene;

    public static Bomber bomberman;

    public static long javaFxTicks = 0;

    private int endCd = 0;

    private GraphicsContext gc;
    private Canvas canvas;


    public static List<Enemy> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Entity> bombs = new ArrayList<>();
    public static List<Entity> visualEffects = new ArrayList<>();


    private GameButton returnToTileButton;

    private GameButton exitButton;
    Group root;
    public static BgmManagement musicPlayer = new BgmManagement("res/music");



    public static int score = 0;
    public static int highScore;
    Text music;
    Text hp;
    Text ui;
    Button p = new Button("play");
    Button s = new Button("stop");
    Button n = new Button(">>");
    Button b = new Button("<<");
    Button up = new Button("v+");
    Button down = new Button("v-");
    Image heart = new Image("textures/heart.png");


    public static boolean isPause = false;

    private int MENU_BUTTON_START_X = 390;

    private int MENU_BUTTON_START_Y = 150;

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
        hp.setText("Heart left: ");
        hp.setY(14*32);
        ui = new Text();
        ui.setText("\tBomb to use: " + 1 + "\tScore: " + score);
        ui.setX(32*10);
        ui.setY(14*32);


        gameStage = new Stage();

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 2) );
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(music);
        root.getChildren().add(hp);
        root.getChildren().add(ui);
        //root.getChildren().add(pane);
        setButton(root);

        // Tao scene
        gameScene = new Scene(root);
        gameScene.setFill(Color.web("#81c483"));
        // Them scene vao stage
        gameStage.setScene(gameScene);

        gameStage.setOnCloseRequest(x -> {
            x.consume();
            // if(ConfirmExit.askConfirmation()) {
            Platform.exit();
            musicPlayer.pause();


            // }
        });

        gameStage.show();



        //use this for main file
    }




    private void handleInput() {
        //
        if (currentlyActiveKeys.contains("P")) {
            currentlyActiveKeys.remove("P");
            if (isPause) {
                isPause = false;
                hideSubScene(pauseSubScene);
            } else {
                isPause = true;
                showSubScene(pauseSubScene);
            }
        }
    }

    private void hideSubScene(GameSubScene subScene) {
        subScene.setVisible(false);
        hideSubSceneButtons(subScene);
    }

    /**
     * hide menuStage and create gameStage
     */

    public void createNewGame(Stage menuStage, SKIN chosenSkin) {
        isPause = false;
        level = 2;
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
                handleInput();
                if(!isPause) {
                    update();

                }

            }
        };
        gameTimer.start();
    }


    private void createSubScenes() {



        pauseSubScene = new GameSubScene();
        //gameOverSubScene = new GameSubScene();


        root.getChildren().addAll(pauseSubScene);

        createPauseSubScene();
        //createGameOverSubScene();


    }


    private void createPauseSubScene() {
        createPauseSubSceneButton();
    }


    private void addPauseMenuButtons(GameButton button) {
        if(!pauseMenuButtons.contains(button)) {
            button.setLayoutX(MENU_BUTTON_START_X);
            button.setLayoutY(MENU_BUTTON_START_Y + pauseMenuButtons.size() * 100);
            pauseMenuButtons.add(button);
            root.getChildren().add(button);
        }


    }
    private List<GameButton> createPauseSubSceneButton() {

        List buttons = new ArrayList<>();

        returnToTileButton = new GameButton("TITLE");
        addPauseMenuButtons(returnToTileButton);

        returnToTileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //hide GameSubScene and showMenu

                //clear all entities
                clearAllEntities();
                musicPlayer.pause();
                gameTimer.stop();
                gameStage.close();
                menuStage.show();

            }


        });

        returnToTileButton.setVisible(false);

        //create back button

        exitButton = new GameButton("Exit");
        addPauseMenuButtons(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //hide GameSubScene and showMenu
                Platform.exit();
                gameStage.close();
                menuStage.show();
            }


        });

        exitButton.setVisible(false);

        buttons.add(exitButton);
        buttons.add(returnToTileButton);

        return buttons;
    }

    private void createGameElements() {
        GameMap.createMap(level);
        //createPauseSubSceneButton();
        createSubScenes();

    }

    private void showSubScene(GameSubScene subScene) {

        subScene.setVisible(true);
        showSubSceneButtons(subScene);
        if (sceneToHide != null) {
            sceneToHide.moveSubScene(100,100);
        }
        subScene.moveSubScene(830,130);
        sceneToHide = subScene;
    }

    private void showSubSceneButtons(GameSubScene subScene) {
        for(GameButton gb : pauseMenuButtons) {
            gb.setVisible(true);
        }
    }

    private void hideSubSceneButtons(GameSubScene subScene) {
        for(GameButton gb : pauseMenuButtons) {
            gb.setVisible(false);
        }
    }

    private void createPlayer(SKIN chosenSkin) {
        bomberman = new Bomber(1,1, chosenSkin.getSheetUrl());
        //entities.add(bomberman);
    }


    //from oldMainf
    public void update() {

        javaFxTicks++;


        music.setText("Now playing: " + musicPlayer.getNow() + "\tLevel " + level);
        ui.setText("\tBomb to use: " + bomberman.getBombNumbers() + "\tScore: " + score);


        bomberman.update();
        updateLives();
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

    private void updateLives() {
        if(bomberman.lives == 0) {
            //draw image
            endCd++;

            System.out.println(endCd);
            musicPlayer.pause();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


            gc.drawImage(new Image("textures/gameOver.jpg"), 0, 0, canvas.getWidth(), canvas.getHeight());

            if(endCd > 120) {
                isPause = true;
                gameStage.close();
                menuStage.show();
                return;
            }


        }
    }

    private void clearAllEntities() {



        musicPlayer.pause();
        gameTimer.stop();
        entities.clear();
        stillObjects.clear();
        items.clear();
        bombs.clear();
        visualEffects.clear();
    }

    public void render() {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0; i < bomberman.lives; i++){
            gc.drawImage(heart, 64 + (10*i), 440, 10, 10);
        }

        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));



        items.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        visualEffects.forEach(g -> g.render(gc));
        bomberman.render(gc);
    }


    public void newLevel(){

        clearAllEntities();
        bomberman.revive();

        level++;
        if(level >= 3) {
            //draw image
            endCd++;

            System.out.println(endCd);
            musicPlayer.pause();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


            gc.drawImage(new Image("textures/win_scene.jpg"), 0, 0, canvas.getWidth(), canvas.getHeight());

            if(endCd > 120) {
                isPause = true;
                gameStage.close();
                menuStage.show();
                return;
            }


        }





        if(level < 3) {

            GameMap.createMap(level);
        }

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
