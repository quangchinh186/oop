package model;

public enum SKIN {
    BOMBERMAN("model/resources/skinchooser/arab_man.png"),
    MIDDLE_EASTMAN("model/resources/skinchooser/bomberman.png"),
    RICARDO("model/resources/skinchooser/ricardo.png");

    String skinUrl;

    private SKIN(String s) {
        this.skinUrl = s;
    }

    public String getUrl() {
        return skinUrl;
    }
}
