package ir.motorush.game.game;

public class EnemyBike {

    private final float width;
    private final float height;

    private float x;
    private float y;

    private float speed;
    private float lane;

    private boolean active;

    public EnemyBike(
            float x,
            float y,
            float speed
    ) {
        this.x = x;
        this.y = y;

        this.speed = speed;

        this.width = 46f;
        this.height = 82f;

        this.lane = 0f;
        this.active = true;
    }

    public void update(
            float delta,
            float playerSpeed
    ) {
        float relativeSpeed =
                playerSpeed - speed;

        y -= relativeSpeed * delta;

        if (y < -height - 100f) {
            active = false;
        }

        if (y > 2000f) {
            active = false;
        }
    }

    public void setLane(float lane) {
        this.lane = lane;
    }

    public float getLane() {
        return lane;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
