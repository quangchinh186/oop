package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.physics.Vector2D;

public class Buggy extends Enemy{

    public Buggy(int x, int y, Image img){
        super(x, y, img);
    }
    private int teleportTime = 0;

    @Override
    public void update() {
        if(position.x % Sprite.SCALED_SIZE == 0 && position.y % Sprite.SCALED_SIZE == 0){
            if(teleportTime == 1000){
                teleport();
            }
        }
        super.update();
        teleportTime = teleportTime < 1000 ? teleportTime+1 : 1000;
    }

    public void animated(){
        //change color when teleportTime = 500(yellow) and 1000(red)
    }

    public void teleport(){
        System.out.println("got in here!");
        for (Enemy enemy : BombermanGame.entities) {
            if(enemy instanceof Buggy) continue;
            if(enemy.getPosition().x % 32 == 0 && enemy.getPosition().y % 32 == 0){
                double a = enemy.getPosition().x;
                double b = enemy.getPosition().y;
                enemy.setPosition(this.position);
                position.x = a;
                position.y = b;
                System.out.println("tp to: " + a + " " + b);
                teleportTime = 0;
                return;
            }
        }
    }



}
