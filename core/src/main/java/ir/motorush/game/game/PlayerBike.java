package ir.motorush.game.game;

public class PlayerBike {

    private String id;
    private String name;

    private float speed;
    private float acceleration;
    private float handling;
    private float braking;

    private int level;

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
