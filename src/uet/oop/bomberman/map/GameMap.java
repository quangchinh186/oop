package uet.oop.bomberman.map;

import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemies.Balloon;
import uet.oop.bomberman.entities.item.BombPowerUp;
import uet.oop.bomberman.entities.Enemies.Oneal;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMap extends BombermanGame {
    public static List <String> map = new ArrayList<>();

    public static void updateMap(int x, int y){
        String temp = map.get(y).substring(0, x) + ' ' + map.get(y).substring(x+1);
        map.set(y, temp);
        Entity t = new Grass(x, y, Sprite.grass.getFxImage());
        BombermanGame.stillObjects.set(y*31 + x, t);
    }
    public static void createMap(int level) {
        BombermanGame.stillObjects.clear();
        String path = "src/uet/oop/bomberman/map/map" + (level) + ".txt";
        getMap(path);
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                Entity object;
                switch (map.get(i).charAt(j)) {
                    case '1' :
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Entity balloon = new Balloon(j, i, Sprite.balloom_left3.getFxImage());
                        entities.add(balloon);
                        break;
                    case '2':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Entity oneal = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                        entities.add(oneal);
                        break;
                    case '#':
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        break;
                    case '*':
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        break;
                    case 'x':
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                        break;
                    case 'S':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Item speedItem = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        items.add(speedItem);
                        break;
                    case 'F':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Item flameItem = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        items.add(flameItem);
                        break;
                    case 'B':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Item bombPowerUp = new BombPowerUp(j, i, Sprite.powerup_bombs.getFxImage());
                        items.add(bombPowerUp);
                        break;
                    default:
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                }
                stillObjects.add(object);
                //items.add(object);
            }
        }
    }

    public static void getMap(String file) {
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            int row = myReader.nextInt();
            myReader.nextLine();
            for(int i = 0; i < row; i++){
                map.add(myReader.nextLine());
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static boolean checkCollision(Rectangle rect) {
        int startX = Math.max((int)(rect.getX()/Sprite.SCALED_SIZE) - 1, 0);
        int startY = Math.max((int)(rect.getY()/Sprite.SCALED_SIZE) - 1, 0);

        for (int i = startY; i < startY + 3; i++) {
            for (int j = startX; j < startX + 3; j++) {
                if(map.get(i).charAt(j) == '#'
                || map.get(i).charAt(j) == '*') {
                    if(rect.intersects((j * Sprite.SCALED_SIZE ) + 1, (i * Sprite.SCALED_SIZE) + 1, Sprite.SCALED_SIZE - 2, Sprite.SCALED_SIZE - 2)) {
                        return true;
                    }
                }
            }
        }



        return false;
    }
}
