package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;

import java.util.HashMap;
import java.util.Timer;

import uet.oop.bomberman.Timer.GTimer;

public abstract class Entity {

    public Entity() {}
    //Rectangle de lam collision.

    public boolean isAnimated = false;

    //khu xu ly animation
    public Image spriteSheet;
    public HashMap<String, Animation> animations = new HashMap<>();

    public Rectangle srcRect;

    public int frames = 0;
    public int speed = 100;

    public int animIndex = 0;

    //
    protected Rectangle rect;

    protected Timer jTimer = new Timer();

    protected int timer = 100;

    protected int x, y;

    protected boolean isActive = true;
    //Tọa độ X tính từ góc trái trên trong Canvas
    //Tọa độ Y tính từ góc trái trên trong Canvas
    public Vector2D position;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        position = new Vector2D(xUnit * Sprite.SCALED_SIZE, yUnit * Sprite.SCALED_SIZE);
        this.img = img;
        this.x = xUnit;
        this.y = yUnit;
        rect = new Rectangle(xUnit * Sprite.SCALED_SIZE, yUnit * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public void render(GraphicsContext gc) {

        //speed thay vao
        int srcX = 0;
        if(isAnimated) {
            //                                                                               speed ,  % frames.
            srcX = (Sprite.SCALED_SIZE * (int) ( (BombermanGame.getJavaFxTicks() / (GTimer.ticksInASeconds / 6)) % 3));
            System.out.println(srcX);
            gc.drawImage(spriteSheet,srcX,animIndex * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE,
                    position.x, position.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        }
        else {
            gc.drawImage(img, position.x, position.y);
        }


        //write a draw function for rect.
        drawRect(gc);


    }
    public abstract void update();

    public void drawRect(GraphicsContext gc) {
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public void setInactive() {
        isActive = false;
    }
    public boolean isActive() {
        return isActive;
    }

    public void play(String id) {
        frames = animations.get(id).frames;
        speed = animations.get(id).speed;
        animIndex = animations.get(id).index;
    }


}
