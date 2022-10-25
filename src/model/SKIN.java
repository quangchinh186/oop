package model;

public enum SKIN {
    BOMBERMAN("model/resources/skinchooser/bomberman.png", "/textures/udlf.png"),
    MIDDLE_EASTMAN("model/resources/skinchooser/arab_man.png", "/textures/mid_man.png"),
    RICARDO("model/resources/skinchooser/ricardo.png", "bro.png");

    String skinUrl;
    String sheetUrl;

    private SKIN(String s, String s2) {
        this.skinUrl = s;
        this.sheetUrl = s2;
    }

    public String getSkinUrl() {
        return skinUrl;
    }


    public String getSheetUrl() {
        return sheetUrl;
    }
}
