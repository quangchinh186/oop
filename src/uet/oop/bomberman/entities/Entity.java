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

public abstract class Entity {

    protected Rectangle rect;
    //Tọa độ X tính từ góc trái trên trong Canvas
    //Tọa độ Y tính từ góc trái trên trong Canvas

    protected Vector2D position;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        position = new Vector2D(xUnit * Sprite.SCALED_SIZE, yUnit * Sprite.SCALED_SIZE);
        this.img = img;
        rect = new Rectangle(xUnit,yUnit, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, position.x, position.y);
        //write a draw function for rect.

        rect.setStroke(Color.RED);

        gc.setStroke(Color.YELLOW);
        gc.strokeRect(position.x,position.y,Sprite.SCALED_SIZE,Sprite.SCALED_SIZE);


    }
    public abstract void update();
}
