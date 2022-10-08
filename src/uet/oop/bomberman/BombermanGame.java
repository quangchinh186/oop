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
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;


import java.util.ArrayList;
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
        //entities.add(bomberman);
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


    public void update() {
        bomberman.update();
        bombs.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        if(bomberman.getCd() == 0 && !(bombs.isEmpty())) {
            Bomb b = (Bomb) bombs.get(0);
            if(b.getTime() == 0){
                visualEffects.removeAll(b.getVisual());
                bombs.remove(b);
            }
        }

        entities.forEach(Entity::update);
        items.forEach(Entity::update);


        visualEffects.forEach(Entity::update);


        clearInactiveEntity(visualEffects);
        //remove projectile
        //nen de rieng or lam chung voi visual effect.




    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bomberman.render(gc);
        items.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        visualEffects.forEach(g -> g.render(gc));
    }

    public void clearInactiveEntity(List<Entity> lst) {
        List<Entity> toRemove = new ArrayList<>();
        for(Entity entity : lst) {
            if(entity.isActive() == false) {
                toRemove.add(entity);
            }
        }
        lst.removeAll(toRemove);
    }

}
