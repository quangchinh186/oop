package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.sound.BgmManagement;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BombermanGame extends Application {
    public static boolean isPause = false;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 15;
    public static int level = 1;
    public static int score = 0;
    public static int highScore;
    public static Scene scene;
    public static Bomber bomberman;
    private GraphicsContext gc;
    private Canvas canvas;
    public static BgmManagement musicPlayer = new BgmManagement("res/music");

    public static List<Enemy> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Entity> bombs = new ArrayList<>();
    public static List<Entity> visualEffects = new ArrayList<>();

    public static void main(String[] args)  {
        
        Application.launch(BombermanGame.class);
    }


    @Override
    public void start(Stage stage) {
        try {
            File file = new File("highScore.txt");
            Scanner scanner = new Scanner(file);
            highScore = scanner.nextInt();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        //highScore = scanner.nextInt();
        stage.setTitle("Bomberman");
        Text curScore = new Text();
        Text music = new Text();
        curScore.setText("Score: " + score + " Best: " + highScore);
        curScore.setX(0);
        curScore.setY(14*32);
        music.setText("Now playing: " + musicPlayer.getNow());
        music.setX(0);
        music.setY(15*32);

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(curScore);
        root.getChildren().add(music);

        // Tao scene
        scene = new Scene(root);
        scene.setFill(Color.web("#81c483"));
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        GameMap.createMap(level);
        musicPlayer.play();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                if(!isPause){
                    curScore.setText("Score: " + score + "\tBest: " + highScore);
                    music.setText("Now playing: " + musicPlayer.getNow());
                    update();
                    musicPlayer.autoMove();
                }
                if(bomberman.lives == 0){
                    System.out.println("Game Over!");
                    highScore = highScore < score ? score : highScore;
                    try {
                        Writer wr = new FileWriter("highScore.txt");
                        wr.write(Integer.toString(highScore));
                        wr.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stop();
                }
                else{
                    bomberman.actionHandler();
                }
            }
        };
        timer.start();
    }


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
           if(e.getState() == State.DIE){
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
        items.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        visualEffects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
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
