package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.entities.tiles.Portal;
import uet.oop.bomberman.physics.Vector2D;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import static view.GameViewManager.stillObjects;

public class Projectile extends Entity{

    private Vector2D velocity;

    private Vector2D ogPos;

    private int range = 200;

    public Projectile(int x, int y, Image img, Vector2D vel) {
        super(x, y, img);
        ogPos = new Vector2D(x * Sprite.SCALED_SIZE,y * Sprite.SCALED_SIZE);

        velocity = vel;

        position.x += 1;
        position.y += 1;
        rect.setX(position.x);
        rect.setY(position.y);
        rect.setWidth(20);
        rect.setHeight(20);

    }

    @Override
    public void update() {

        if(Vector2D.getDistance(position, ogPos) > range) {
            System.out.println("OUT OF RANGE PROJECTILE");
            setInactive();
        }

        for(Entity et : stillObjects) {
            if(et.rect.intersects(this.rect.getX(), this.rect.getY(), this.rect.getWidth(), this.rect.getHeight())) {
                if(et instanceof Brick) {
                    ((Brick) et).setExploded(true);
                }
                if(! (et instanceof Grass || et instanceof Portal)) {
                    this.setInactive();
                }
            }
        }
        position.x += velocity.x;
        position.y += velocity.y;
        rect.setX(position.x);
        rect.setY(position.y);

    }

    public void handleCollision() {
        GameMap.destroy((int) position.x / Sprite.SCALED_SIZE, (int) position.y / Sprite.SCALED_SIZE);
    }
}
