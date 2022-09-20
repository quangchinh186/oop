package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;

import java.util.HashSet;

public class Bomber extends Entity {

    static HashSet<String> currentlyActiveKeys;
    static HashSet<String> releasedKey;
    private int velX;
    private int velY;

    public int health;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        prepareActionHandlers();
        velX = 0;
        velY = 0;
    }

    @Override
    public void update() {
        //System.out.println(this.x);
        //this.x++;
        actionHandler();
        x += velX;
        y += velY;
    }

    public void actionHandler () {
        if(currentlyActiveKeys.contains("LEFT")) {
            velX = -1;
        }
        if (currentlyActiveKeys.contains("RIGHT")){
            velX = 1;
        }
        if (currentlyActiveKeys.contains("UP")){
            velY = -1;
        }
        if (currentlyActiveKeys.contains("DOWN")){
            velY = 1;
        }

        //on released
        if(releasedKey.contains("LEFT") || releasedKey.contains("RIGHT")) {
            velX = 0;
        }

        if (releasedKey.contains("UP") || releasedKey.contains("DOWN")){
            velY = 0;
        }
    }
    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    private static void prepareActionHandlers()
    {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        releasedKey = new HashSet<String>();
        BombermanGame.scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
                releasedKey.clear();
            }

        });
        BombermanGame.scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());
                releasedKey.add(event.getCode().toString());
            }
        });
    }
}
