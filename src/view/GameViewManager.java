package view;


import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.GameButton;
import model.GameSubScene;
import model.SKIN;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.Weapon;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.util.ArrayList;
import java.util.List;
import uet.oop.bomberman.sound.BgmManagement;

import static uet.oop.bomberman.entities.Bomber.currentlyActiveKeys;
import static view.ViewManager.*;

public class GameViewManager {
    private AnchorPane gamePane;
    public static Scene gameScene;
    private Stage gameStage;

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


    private GameButton returnToTileButton;

    private GameButton exitButton;
    Group root;
    public static BgmManagement musicPlayer = new BgmManagement("res/music");

    public static boolean isPause = false;

    public static int score = 0;
    private int MENU_BUTTON_START_X = 390;

    private int MENU_BUTTON_START_Y = 150;

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
        root = new Group();
        root.getChildren().add(canvas);


        // Tao scene
        gameScene = new Scene(root);

        // Them scene vao stage
        gameStage.setScene(gameScene);

        gameStage.setOnCloseRequest(x -> {
            x.consume();
            // if(ConfirmExit.askConfirmation()) {
            // Platform.exit();
            gameStage.close();
            menuStage.show();
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

        returnToTileButton = new GameButton("TILE");
        addPauseMenuButtons(returnToTileButton);

        returnToTileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //hide GameSubScene and showMenu

                //clear all entities
                clearAllEntities();
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

    private void clearAllEntities() {

        entities.clear();
        stillObjects.clear();
        items.clear();
        bombs.clear();
        visualEffects.clear();
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
