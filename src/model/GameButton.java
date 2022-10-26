package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GameButton extends Button {

    private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private static final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent;-fx-background-image: " +
            "url('file:///C:/Users/nguye/IdeaProjects/demoAndLearn/src/main/java/model/resources/yellow_button_pressed.png');";
    private static final String BUTTON_FREE_STYLE = "-fx-background-color: transparent;-fx-background-image: " +
            "url('file:///C:/Users/nguye/IdeaProjects/demoAndLearn/src/main/java/model/resources/yellow_button.png');";
    //private static final String BUTTON_FREE_STYLE = "src/model/resources/rocketButton.png";

    public GameButton(String text) {

        //setBackground(getBackground());
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);

        setStyle(BUTTON_FREE_STYLE);
        initialiseButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 25));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 25));
            System.out.println("Font not found or could not be loaded. Using default \"Verdana\"");
        }

    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);

    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initialiseButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }

        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow(50, Color.YELLOW));
                //System.out.println("nnter");
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}

