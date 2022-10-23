package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

public class EvilBomber extends Enemy {
    public EvilBomber(int x, int y, Image img){
        super(x, y, img);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void move(){
        double nX = position.x + v_x[turn], nY = position.y + v_y[turn];
        int j = (int) (nX / Sprite.SCALED_SIZE);
        int i = (int) (nY / Sprite.SCALED_SIZE);
        switch (turn){
            case 0:
                nX += Sprite.SCALED_SIZE;
                j = (int) (nX / Sprite.SCALED_SIZE);
                break;
            case 1:
                nY += Sprite.SCALED_SIZE;
                i = (int) (nY / Sprite.SCALED_SIZE);
                break;
            default:
                break;
        }
        char c = GameMap.map.get(i).charAt(j);
        if(c == '#' || c == '*' || c == 'o'){
            if(position.x % 32 == 0 && position.y % 32 == 0){
                if(c == '*'){
                    Bomb bomb = new Bomb(x, y, Sprite.bomb_1.getFxImage());
                    BombermanGame.bombs.add(bomb);
                }
                turn = (int) Math.floor(Math.random()*4);
            } else {
                position.x += v_x[turn];
                position.y += v_y[turn];
            }
        } else {
            position.x += v_x[turn];
            position.y += v_y[turn];
        }
    }
}
