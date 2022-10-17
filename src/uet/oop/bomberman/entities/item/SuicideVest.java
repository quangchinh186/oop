package uet.oop.bomberman.entities.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SuicideVest extends Weapon {

    public static Image vestImg = new Image("/textures/suicide_vest.png");
    public SuicideVest(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void doEffect() {
        super.doEffect();
        this.img = vestImg;
    }


    @Override
    public void render(GraphicsContext gc) {
        if(armed) {
            gc.drawImage(this.img, 0, 0, this.img.getWidth(), this.img.getHeight(),
                    position.x ,position.y,this.img.getWidth()/2,this.img.getHeight()/2);
        }
        else {
            gc.drawImage(this.img, position.x, position.y);
        }
    }
}
