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
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class BombermanGame extends Application {


    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public static int level = 1;

    public static Scene scene;

    public static Bomber bomberman;

    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static List<Item> items = new ArrayList<>();

    public static List<Entity> bombs = new ArrayList<>();

    public static List<Entity> visualEffects = new ArrayList<>();

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

        bombs.forEach(Entity::update);

        if(bomberman.getCd() == 10) {
            bombs.clear();
            visualEffects.clear();

        }

        entities.forEach(Entity::update);
        items.forEach(Entity::update);

        visualEffects.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        items.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        visualEffects.forEach(g -> g.render(gc));
    }

}
