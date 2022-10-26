package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.physics.Vector2D;
import view.GameViewManager;

import static view.GameViewManager.entities;
import static view.GameViewManager.stillObjects;

public class Projectile extends Entity{

    private Vector2D velocity;

    private Vector2D ogPos;

    private int range = 400;

    public Projectile(int x, int y, Image img, Vector2D vel) {
        position = new Vector2D(x + Sprite.SCALED_SIZE / 2.0 , y + Sprite.SCALED_SIZE / 2.0);
        this.img = img;
        this.x = x;
        this.y = y;
        rect = new Rectangle(position.x, position.y, 12, 12);
        ogPos = new Vector2D(x,y);
        velocity = vel;
    }

    @Override
    public void update() {

        if(Vector2D.getDistance(position, ogPos) > range) {
            setInactive();
        }
        position.x += velocity.x;
        position.y += velocity.y;

        rect.setX(position.x);
        rect.setY(position.y);

        handleCollision();

    }

    public void handleCollision() {
        for(Enemy enemy : GameViewManager.entities) {
            if(rect.intersects(enemy.rect.getX(), enemy.rect.getY(),
                    enemy.rect.getWidth(), enemy.rect.getHeight())) {
                enemy.die();
                this.setInactive();
            }
        }
        for(Entity entity : stillObjects) {
                if(rect.intersects(entity.rect.getX(), entity.rect.getY(),
                        entity.rect.getWidth(), entity.rect.getHeight())
                && !(entity instanceof Grass)) {
                    this.setInactive();
                }
        }

    }
}
