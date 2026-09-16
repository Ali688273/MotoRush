package ir.motorush.game.game;

public class Coin {

    private float x;
    private float y;

    private final float radius;

    private boolean collected;

    public Coin(
            float x,
            float y
    ) {
        this.x = x;
        this.y = y;

        this.radius = 16f;
        this.collected = false;
    }

    public void update(
            float delta,
            float speed
    ) {
        y -= speed * delta;
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }
}
