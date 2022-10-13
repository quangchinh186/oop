package uet.oop.bomberman.map;

import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemies.Balloon;
import uet.oop.bomberman.entities.Oneal;
import uet.oop.bomberman.entities.item.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static uet.oop.bomberman.entities.Bomber.weapons;
import static uet.oop.bomberman.entities.Bomber.weaponsSet;

public class GameMap extends BombermanGame {
    public static ArrayList <String> map = new ArrayList<>();

    public static void updateMap(int x, int y){
        stillObjects.clear();
        String temp = map.get(y).substring(0, x) + ' ' + map.get(y).substring(x+1);
        map.set(y, temp);

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                Entity object = new Grass(j, i, Sprite.grass.getFxImage());
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
                    default:
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                }
                stillObjects.add(object);
            }
        }

    }

    public static void createMap(int level) {
        String path = "map" + (level) + ".txt";
        getMap(path);
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                Entity object = new Grass(j, i, Sprite.grass.getFxImage());
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
                    case 'G':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Gun gun = new Gun(j, i, Sprite.player_chad.getFxImage());
                        weapons.add(gun);
                        //weaponsSet.add("G");
                        break;
                    case 'T':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        FlameThrower flameThrower = new FlameThrower(j, i, Sprite.brick_exploded2.getFxImage());
                        weapons.add(flameThrower);
                        //weaponsSet.add("G");
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
        double posX = rect.getX();
        double posY = rect.getY();

        int startX = Math.max((int)(rect.getX()/Sprite.SCALED_SIZE) - 1, 0);
        int startY = Math.max((int)(rect.getY()/Sprite.SCALED_SIZE) - 1, 0);

        for (int i = startY; i < startY + 3; i++) {
            for (int j = startX; j < startX + 3; j++) {
                if(map.get(i).charAt(j) == '#'
                || map.get(i).charAt(j) == '*') {
                    if(rect.intersects(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                        //determine xem la left or right. dua vao velocity.

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void destroy(int _x, int _y) {
        switch (GameMap.map.get(_y).charAt(_x)){
            case '*' :
                Brick temp = (Brick) BombermanGame.stillObjects.get(_y*31 + _x);
                temp.setExploded(true);
                break;

            default:
                break;
        }
    }

}
