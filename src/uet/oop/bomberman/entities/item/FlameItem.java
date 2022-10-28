package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomb;


public class FlameItem extends Item {
    public boolean done;
    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
        done = false;
    }
    @Override
    public void update() {
        super.update();
    }

    public void doEffect() {
        if(!done){
            Bomb.power++;
            System.out.println(Bomb.power);
            done = true;
        }
    }
}
