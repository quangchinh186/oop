package uet.oop.bomberman.entities;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.states.State;
import uet.oop.bomberman.physics.Vector2D;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;

public abstract class Entity {
    public Entity() {}
    //Rectangle de lam collision.
    protected Rectangle rect;
    protected Sprite s1, s2, s3;
    protected int timer = 0;
    protected State state;
    public int x, y;

    protected Timer jTimer = new Timer();

    public State getState(){
        return state;
    }
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
        rect = new Rectangle(xUnit * Sprite.SCALED_SIZE + 1 , yUnit * Sprite.SCALED_SIZE + 1 , Sprite.SCALED_SIZE-2, Sprite.SCALED_SIZE-2);

    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, position.x, position.y);
        //write a draw function for rect.
        drawRect(gc);
    }
    public abstract void update();

    public void die(){};
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

    public Vector2D getPosition() {
        return position;
    }
}
