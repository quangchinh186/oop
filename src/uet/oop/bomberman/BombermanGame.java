package uet.oop.bomberman;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;


import java.util.ArrayList;
import java.util.List;


public class BombermanGame extends Application {


    public static void main(String[] args)  {
        Application.launch(BombermanGame.class);
    }

    @Override

    public void start(Stage primaryStage) {
        try {
            ViewManager manager = new ViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.setTitle("SpaceRunner the Game");
            primaryStage.setResizable(false);
            //primaryStage.setFullScreen(true);

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
