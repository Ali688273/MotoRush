package ir.motorush.game.game;

public class PlayerBike {

    private final String id;
    private final String name;

    private float speed;
    private float acceleration;
    private float handling;
    private float braking;

    private int level;

    private float x;
    private float y;

    private float width;
    private float height;

    public PlayerBike(
            String id,
            String name,
            float speed,
            float acceleration,
            float handling,
            float braking
    ) {
        this.id = id;
        this.name = name;

        this.speed = speed;
        this.acceleration = acceleration;
        this.handling = handling;
        this.braking = braking;

        this.level = 1;

        this.width = 46f;
        this.height = 82f;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move(float amount) {
        x += amount;
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getHandling() {
        return handling;
    }

    public float getBraking() {
        return braking;
    }

    public int getLevel() {
        return level;
    }

    public void upgradeSpeed(float amount) {
        speed += amount;
        level++;
    }

    public void upgradeAcceleration(float amount) {
        acceleration += amount;
        level++;
    }

    public void upgradeHandling(float amount) {
        handling += amount;
        level++;
    }

    public void upgradeBraking(float amount) {
        braking += amount;
        level++;
    }
}
