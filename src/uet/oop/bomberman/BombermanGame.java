package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Balloon;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Flames;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class BombermanGame extends Application {


    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public static int level = 1;

    public static Scene scene;

    static Entity bomberman;

    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> bomb = new ArrayList<>();
    public static List<Entity> item = new ArrayList<>();
    public static List<Entity> visualEffect = new ArrayList<>();





    public static void main(String[] args)  {
        //System.setIn(new FileInputStream("D:\Input.txt"));

        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();


        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        GameMap.createMap(level);
        GameMap.checkCollision(new Rectangle(1,2,4,5));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();


    }



    public void getInput(){

    }





//>>>>>>> test2

    public void update() {
        entities.forEach(Entity::update);
        if(Bomber.cd == 10){
            bomb.clear();
            visualEffect.clear();
        }
        bomb.forEach(Entity::update);
        visualEffect.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bomb.forEach(g -> g.render(gc));
        visualEffect.forEach(g -> g.render(gc));
    }




}
