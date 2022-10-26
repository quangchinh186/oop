package uet.oop.bomberman.map;

import javafx.scene.shape.Rectangle;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemies.*;
import uet.oop.bomberman.entities.item.*;
import uet.oop.bomberman.entities.item.powerup.BombPowerUp;
import uet.oop.bomberman.entities.item.powerup.FlameItem;
import uet.oop.bomberman.entities.item.powerup.SpeedItem;
import uet.oop.bomberman.entities.item.weapon.FlameThrower;
import uet.oop.bomberman.entities.item.weapon.Gun;
import uet.oop.bomberman.entities.item.weapon.SuicideVest;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;
import view.GameViewManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import uet.oop.bomberman.physics.Vector2D;

import static uet.oop.bomberman.entities.Bomber.weapons;
import static view.GameViewManager.*;

public class GameMap extends GameViewManager {
    public static ArrayList <String> map = new ArrayList<>();

    public static List<Vector2D> nextLevel = new ArrayList<>();

    public static void updateMap(int x, int y){
        String temp = map.get(y).substring(0, x) + ' ' + map.get(y).substring(x+1);
        map.set(y, temp);
        Entity t = new Grass(x, y, Sprite.grass.getFxImage());
        GameViewManager.stillObjects.set(y*31 + x, t);
    }
    public static void occupyBlock(int x, int y) {
        String temp = map.get(y).substring(0, x) + 'o' + map.get(y).substring(x+1);
        map.set(y, temp);
    }
    public static void createMap(int level) {
        map.clear();
        nextLevel.clear();
        GameViewManager.stillObjects.clear();
        String path = "src/uet/oop/bomberman/map/map" + (level) + ".txt";
        getMap(path);
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                Entity object;
                switch (map.get(i).charAt(j)) {
                    /*
                    case 'p' :
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        bomberman = new Bomber(j, i, Sprite.player_right.getFxImage());
                        break;
                     */
                    case '1' :
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Enemy balloon = new Balloon(j, i, Sprite.balloom_left3.getFxImage());
                        enemies.add(balloon);
                        break;
                    case '2':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Enemy oneal = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                        enemies.add(oneal);
                        break;
                    case '3':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Enemy big = new BigBalloon(j, i, Sprite.doll_left1.getFxImage());
                        enemies.add(big);
                        break;
                    case '4':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Enemy creeper = new Creeper(j, i, Sprite.creeper_right1.getFxImage());
                        enemies.add(creeper);
                        break;
                    case '5':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Enemy buggy = new Buggy(j, i, Sprite.white_left1.getFxImage());
                        enemies.add(buggy);
                        break;
                    case '6':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Enemy evilBomber = new EvilBomber(j, i, Sprite.player_right.getFxImage());
                        enemies.add(evilBomber);
                    case 'P':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        Enemy turret = new Turret(j, i, Sprite.player_up_2.getFxImage());
                        enemies.add(turret);
                        //weaponsSet.add("G");
                        break;
                    case '#':
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        break;
                    case '*':
                        object = new Brick(j, i, Sprite.brick.getFxImage(), false);
                        break;
                    case 'x':
                        String temp = map.get(i).substring(0, j) + '*' + map.get(i).substring(j+1);
                        map.set(i, temp);
                        object = new Brick(j, i, Sprite.brick.getFxImage(), true);
                        uet.oop.bomberman.physics.Vector2D v = new uet.oop.bomberman.physics.Vector2D(j, i);
                        nextLevel.add(v);
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
                    case 'V':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        SuicideVest vest = new SuicideVest(j, i, Sprite.bomb_2.getFxImage());
                        weapons.add(vest);
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
        int startX = Math.max((int)(rect.getX()/Sprite.SCALED_SIZE) - 1, 0);
        int startY = Math.max((int)(rect.getY()/Sprite.SCALED_SIZE) - 1, 0);

        for (int i = startY; i < startY + 3; i++) {
            for (int j = startX; j < startX + 3; j++) {
                if(map.get(i).charAt(j) == '#' || map.get(i).charAt(j) == 'o'
                        || map.get(i).charAt(j) == '*') {
                    if(rect.intersects((j * Sprite.SCALED_SIZE ) + 1, (i * Sprite.SCALED_SIZE) + 1, Sprite.SCALED_SIZE - 2, Sprite.SCALED_SIZE - 2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static void print(){
        for(int i = 0 ; i < map.size(); i++) {
            System.out.println(map.get(i));
        }
    }



    public static void destroy(int _x, int _y) {
        switch (GameMap.map.get(_y).charAt(_x)){
            case '*' :
                Brick temp = (Brick) GameViewManager.stillObjects.get(_y*31 + _x);
                temp.setExploded(true);
                break;

            default:
                break;
        }
    }

}
