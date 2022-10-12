package uet.oop.bomberman.map;

import java.util.List;

public class Node {
    public int x, y;
    public boolean walkable;
    public int f_cost, h_cost, g_cost;
    public Node parent;
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        if(GameMap.map.get(y).charAt(x) == '#' || GameMap.map.get(y).charAt(x) == '*'){
            walkable = false;
        }else{
            walkable = true;
        }
    }
    public void setF_cost(){
        this.f_cost = this.g_cost + this.h_cost;
    }
    public boolean equals(Node t) {
        return this.x == t.x && this.y == t.y;
    }
    public Node isInside(List<Node> list){
        for (Node n : list)
        {
            if(this.equals(n)) return n;
        }
        return null;
    }

    public void print(){
        System.out.println(x + " " + y);
    }
}
