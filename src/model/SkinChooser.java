package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SkinChooser extends VBox {


    private ImageView circleImage;

    private ImageView skinImage;

    private String circleNotChosen = "model/resources/grey_circle.png";
    private String circleChosen = "model/resources/yellow_boxTick.png";

    //private String circleChosen = "model/resources/missile.png";

    private SKIN skin;
    private boolean isCircleChosen;

    public SkinChooser(SKIN skin) {
        circleImage = new ImageView(new Image(circleNotChosen));
        skinImage   = new ImageView(skin.getUrl());
        this.skin = skin;
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(skinImage);
    }

    public SKIN getSkin() {
        return skin;
    }

    public boolean isCircleChosen() {
        return isCircleChosen;
    }

    public void setIsCircleChosen(boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
