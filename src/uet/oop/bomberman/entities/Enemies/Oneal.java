package uet.oop.bomberman.entities.Enemies;

import uet.oop.bomberman.map.Node;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.States.State;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.GameMap;

import java.util.ArrayList;
import java.util.List;

public class Oneal extends Enemy {
    public static final double onealSpeed = 0.5;
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        this.s1 = Sprite.oneal_right1;
        this.s2 = Sprite.oneal_right2;
        this.s3 = Sprite.oneal_right3;
        animateTime = Sprite.DEFAULT_SIZE;
        state = State.RIGHT;
        turn = 2;
    }

    @Override
    public void update() {
        super.update();
        animate();
    }
    public void animate(){
        if(turn == 0){
            s1 = Sprite.oneal_right1;
            s2 = Sprite.oneal_right2;
            s3 = Sprite.oneal_right3;
        }
        if(turn == 2){
            s1 = Sprite.oneal_left1;
            s2 = Sprite.oneal_left2;
            s3 = Sprite.oneal_left3;
        }
        if(state == State.DIE){
            s1 = Sprite.mob_dead1;
            s2 = Sprite.mob_dead2;
            s3 = Sprite.mob_dead3;
        }
        if(state == State.DIE && timer % Sprite.DEFAULT_SIZE == 0){
            this.img = null;
        }else{
            timer = timer > Sprite.SCALED_SIZE ? 0 :timer+1;
            this.img = Sprite.movingSprite(s1, s2, s3, this.timer, animateTime).getFxImage();
        }
    }

    public void die(){
        this.timer = 1;
        state = State.DIE;
        animateTime = Sprite.SCALED_SIZE;
        this.img = Sprite.oneal_dead.getFxImage();
    }

    public static void find(Node start, Node end){
        List<Node> close = new ArrayList<>();
        List<Node> open = new ArrayList<>();
        open.add(start);

        while(!open.isEmpty()){
            Node curr = open.get(0);
            for (Node t : open) {
                if(t.f_cost < curr.f_cost){
                    curr = t;
                }
                if(t.f_cost == curr.f_cost && t.h_cost < curr.h_cost){
                    curr = t;
                }
            }

            open.remove(curr);
            close.add(curr);

            if(curr.equals(end)){
                end.parent = curr;
                return;
            }

            List<Node> neighbors = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                Node t = new Node((int)(curr.x + v_x[i]), (int)(curr.y + v_y[i]));
                if(t.walkable){
                    t.parent = curr;
                    t.g_cost = Math.abs(t.x - start.x) + Math.abs(t.y - start.y);
                    t.h_cost = Math.abs(t.x - end.x) + Math.abs(t.y - end.y);
                    t.setF_cost();
                    neighbors.add(t);
                }
            }
            for (Node neighbor: neighbors)
            {
                Node temp = neighbor.isInside(open);
                Node temp2 = neighbor.isInside(close);
                if(temp == null && temp2 == null){
                    open.add(neighbor);
                }
                if(temp != null) {
                    if(neighbor.h_cost < temp.h_cost){
                        temp.h_cost = neighbor.h_cost;
                        temp.g_cost = neighbor.g_cost;
                        temp.setF_cost();
                        temp.parent = curr;
                    }
                }
            }

        }

    }

    public int decideDirection(){
        int stX = (int)(position.x / Sprite.SCALED_SIZE);
        int stY = (int)(position.y / Sprite.SCALED_SIZE);
        int dis = Math.abs(stX - BombermanGame.bomberman.getX()) + Math.abs(stY - BombermanGame.bomberman.getY());
        //Init start node *aka* Oneal position
        Node s = new Node(stX, stY);
        s.g_cost = 0;
        s.h_cost = dis;
        s.setF_cost();
        //Init end node *aka* bomberman
        Node e = new Node(BombermanGame.bomberman.getX(), BombermanGame.bomberman.getY());
        e.g_cost = dis;
        e.h_cost = 0;
        e.setF_cost();
        if(s.equals(e)) return 0;
        //find path using A* algorithm
        find(s, e);
        Node t = e.parent;
        //t is the node connected to end node
        //if t is null means there is no way to get to bomberman
        if(t == null){
            return turn;
        }
        //else we trace back to the node connected to the start node
        else{
            while(t.parent != s){
                t = t.parent;
            }

            int vX = t.x - stX, vY = t.y - stY;
            for(int i = 0; i < 4; i++){
                if(v_x[i] == vX && v_y[i] == vY) return i;
            }
        }
        return turn;
    }
    @Override
    public void move(){
        if(position.x % Sprite.SCALED_SIZE == 0 && position.y % Sprite.SCALED_SIZE == 0){
            turn = decideDirection();
        }
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

        double tX = position.x, tY = position.y;
        if(GameMap.map.get(i).charAt(j) == '#' || GameMap.map.get(i).charAt(j) == '*'
            || GameMap.map.get(i).charAt(j) == 'o'){
            if(GameMap.map.get(i).charAt(j) == 'o'){
                turn = (turn + 2) % 4;
            }
            if(position.x % 32 != 0 && position.y % 32 != 0){
                position.x += v_x[turn] * onealSpeed;
                position.y += v_y[turn] * onealSpeed;
            }
        } else {
            position.x += v_x[turn] * onealSpeed;
            position.y += v_y[turn] * onealSpeed;
        }
        //check stuck
        if(tX == position.x && tY == position.y){
            position.x = ((int)((position.x + 16) / Sprite.SCALED_SIZE)) * Sprite.SCALED_SIZE;
            position.y = ((int)((position.y + 16) / Sprite.SCALED_SIZE)) * Sprite.SCALED_SIZE;
            turn = timer % 4;
        }

    }
}