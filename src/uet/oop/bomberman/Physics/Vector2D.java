package uet.oop.bomberman.Physics;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D() {
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

    public static double getDistance(Vector2D v1, Vector2D v2) {
        return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
    }

}
