package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.weapon.Weapon;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;
import view.ViewManager;


import java.util.ArrayList;
import java.util.List;


public class BombermanGame extends Application {



    public static void main(String[] args)  {
        //System.setIn(new FileInputStream("D:\Input.txt"));

        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            ViewManager manager = new ViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.setTitle("SpaceRunner the Game");
            primaryStage.setResizable(false);
            //primaryStage.setFullScreen(true);

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
     *         gc = canvas.getGraphicsContext2D();
     *
     *         // Tao root container
     *         Group root = new Group();
     *         root.getChildren().add(canvas);
     *
     *         // Tao scene
     *         scene = new Scene(root);
     *
     *         // Them scene vao stage
     *         stage.setScene(scene);
     *         stage.show();
     *         GameMap.createMap(level);
     */


}
