package view;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.SKIN;

import java.util.Random;

public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private ImageView ship;

    private static final int GAME_WIDTH = 31 * 32;
    private static final int GAME_HEIGHT = 13 * 32;



    public GameViewManager() {
        initializeStage();
    }


    /**
     * create things for MAINGAME
     */
    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setTitle("Bomber Man");
        gameStage.setScene(gameScene);
        gameStage.setResizable(false);
        gameStage.setOnCloseRequest(x -> {
            x.consume();
            // if(ConfirmExit.askConfirmation()) {
            // Platform.exit();
            gameStage.close();
            menuStage.show();
            // }
        });

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
        //createShip(chosenShip); create new bomberman
        //createGameElements(chosenShip);

        //the game loop.
        //createGameLoop();
        gameStage.show();

    }
}
