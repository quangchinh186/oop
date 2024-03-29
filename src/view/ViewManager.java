package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * import model.InfoLabel;
 * import model.SHIP;
 * import model.ShipPicker;
 * import model.SoundEffects;
 * import model.GameButton;
 * import model.GameButton;
 */

public class ViewManager {

    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    public final double MENU_BUTTON_START_X = 100;
    public final double MENU_BUTTON_START_Y = 150;
    private static final String BUTTON_SFX = "file:src/model/resources/robotSFX.wav";

    AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private GameSubScene creditsSubScene;
    private GameSubScene helpSubScene;
    private GameSubScene scoreSubScene;

    private GameSubScene skinChooserSubScene;

    private GameSubScene sceneToHide;

    List<GameButton> menuButtons = new ArrayList<>();
    List<SkinChooser> skinList;

    private SKIN chosenSkin;

    public ViewManager() {
        //menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createBackground();
        createLogo();

        createButtons();

        mainStage.setOnCloseRequest(x -> {
            x.consume();
            // if(ConfirmExit.askConfirmation()) {
            Platform.exit();
            // }
        });

    }


    public Stage getMainStage() {
        return mainStage;
    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createBackground() {
        Image bgImage = new Image("view/resources/game_bg.jpg");
        BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(1024, 800, false, false, false, false));
        mainPane.setBackground(new Background(bg));
    }


    private void addMenuButtons(GameButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);

    }

    private void createStartButton() {
        GameButton startButton = new GameButton("Play");
        addMenuButtons(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showSubScene(skinChooserSubScene);
            }


        });

    }

    private GameButton createButtonToStart() {
        GameButton startButton = new GameButton("Start");
        startButton.setLayoutX(350);
        startButton.setLayoutY(320);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (chosenSkin != null) {
                    mainStage.hide();
                    GameViewManager gameViewManager = new GameViewManager();
                    gameViewManager.createNewGame(mainStage, chosenSkin);
                }
            }
        });
        return startButton;
    }




    private void createScoresButton() {

        GameButton scoresButton = new GameButton("Scores");
        addMenuButtons(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoreSubScene);
            }


        });

    }

    private void createCreditsSubScene() {
        creditsSubScene = new GameSubScene();
        mainPane.getChildren().add(creditsSubScene);

        InfoLabel credits = new InfoLabel("  <<< Credits >>>");
        credits.setLayoutX(120);
        credits.setLayoutY(20);
        Label credit0 = new Label("OOP Project By Nguyen Khoa Dang");
        Label credit1 = new Label("  ");
        Label credit2 = new Label("And Le Quang Chinh");


        VBox creditsBox = new VBox(10, credit0, credit1, credit2);


        creditsBox.setLayoutX(50);
        creditsBox.setLayoutY(80);
        creditsSubScene.getPane().getChildren().addAll(credits, creditsBox);

    }

    private void createHelpSubScene() {
        helpSubScene = new GameSubScene();
        mainPane.getChildren().add(helpSubScene);


        InfoLabel credits = new InfoLabel("  <<< Credits >>>");
        credits.setLayoutX(120);
        credits.setLayoutY(20);
        Label credit0 = new Label("Press SPACE to plant Bomb");
        Label credit1 = new Label("Press K to use your weapon  ");
        Label credit2 = new Label("Press P to pause the game");


        VBox creditsBox = new VBox(20, credit0, credit1, credit2);


        creditsBox.setLayoutX(50);
        creditsBox.setLayoutY(80);
        helpSubScene.getPane().getChildren().addAll(credits, creditsBox);

    }

        private void createHelpButton() {
        GameButton helpButton = new GameButton("Help");
        addMenuButtons(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }


        });
    }

    private void createCreditsButton() {
        GameButton creditsButton = new GameButton("Credits");
        addMenuButtons(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               showSubScene(creditsSubScene);
            }
        });

    }

    private void createExitButton() {
        GameButton exitButton = new GameButton("Exit");
        addMenuButtons(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

                Platform.exit();
                // mainStage.close();
            }
        });
    }


    private void createLogo() {
        Image logoImage = new Image("view/resources/Bomberman_logo.png", 500, 100, false, false);
        ImageView logo = new ImageView(logoImage);
        logo.setLayoutX(400);
        logo.setLayoutY(50);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                logo.setEffect(new DropShadow(100, Color.YELLOW));
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                logo.setEffect(null);
            }
        });
        mainPane.getChildren().add(logo);
    }


    private void createSubScenes() {
        creditsSubScene = new GameSubScene();
        helpSubScene = new GameSubScene();
        scoreSubScene = new GameSubScene();
        skinChooserSubScene = new GameSubScene();

        mainPane.getChildren().addAll( scoreSubScene, skinChooserSubScene);

        createSkinChooserSubScene();
        createCreditsSubScene();
        createHelpSubScene();


    }




        private HBox createSkinsToChoose() {
        HBox box = new HBox();
        box.setSpacing(20);

        skinList = new ArrayList<>();
        for (SKIN ship : SKIN.values()) {
            SkinChooser skinToPick = new SkinChooser(ship);
            skinList.add(skinToPick);
            box.getChildren().add(skinToPick);
            skinToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    for (SkinChooser ship : skinList) {
                        ship.setIsCircleChosen(false);
                    }
                    skinToPick.setIsCircleChosen(true);
                    chosenSkin = skinToPick.getSkin();
                }
            });
        }
        box.setLayoutX(300 - 118 * 2);
        box.setLayoutY(120);
        return box;
    }

    private void showSubScene(GameSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }


    private void createSkinChooserSubScene() {
        //skinChooserSubScene = new GameSubScene();
        //mainPane.getChildren().add(skinChooserSubScene);

        InfoLabel chooseSkinLabel = new InfoLabel("CHOOSE YOUR SKIN");
        chooseSkinLabel.setLayoutX(110);
        chooseSkinLabel.setLayoutY(20);
        skinChooserSubScene.getPane().getChildren().addAll(chooseSkinLabel, createSkinsToChoose(),
                createButtonToStart());

    }



    private void createScoreSubScene() {
        scoreSubScene = new GameSubScene();
        mainPane.getChildren().add(scoreSubScene);

    }






}
