package uet.oop.bomberman.States;

public enum State {
    UP, RIGHT, DOWN, LEFT, STOP, DIE, CHAD;

    public int in_num(){
        switch (this){
            case RIGHT: return 1;
            case DOWN: return 2;
            case LEFT: return 3;
            case UP: return 4;

            default: return 0;
        }
    }
}
