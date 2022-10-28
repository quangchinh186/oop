package uet.oop.bomberman;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;



public class BombermanGame extends Application {


    public static void main(String[] args)  {
        Application.launch(BombermanGame.class);
    }

    @Override

    public void start(Stage primaryStage) {
        try {
            ViewManager manager = new ViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.setTitle("Middle East Man");
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
