package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Physics.Vector2D;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;

public abstract class Entity {

    public Entity() {}
    //Rectangle de lam collision.
    protected Rectangle rect;

    protected Timer timerF = new Timer();

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
        gc.drawImage(img, position.x, position.y);
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


}
