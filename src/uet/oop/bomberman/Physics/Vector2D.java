package uet.oop.bomberman.Physics;

public class Vector2D {
    public double x;
    public double y;

    Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + x , this.y + y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - x , this.y - y);
    }

    public Vector2D multiply(Vector2D other) {
        return new Vector2D(this.x * x , this.y * y);
    }

    public Vector2D divide(Vector2D other) {
        return new Vector2D(this.x / x , this.y / y);
    }

}
