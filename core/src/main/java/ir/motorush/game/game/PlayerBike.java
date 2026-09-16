package ir.motorush.game.game;

public class PlayerBike {

    private final String id;
    private final String name;

    private final float width;
    private final float height;

    private final float baseSpeed;
    private final float baseHandling;
    private final float baseNitro;

    private float x;
    private float y;

    private int speedLevel;
    private int handlingLevel;
    private int nitroLevel;

    public PlayerBike(
            String id,
            String name,
            float width,
            float height,
            float speed,
            float handling
    ) {

        this.id = id;
        this.name = name;

        this.width = width;
        this.height = height;

        this.baseSpeed = speed;
        this.baseHandling = handling;
        this.baseNitro = 5f;

        this.speedLevel = 1;
        this.handlingLevel = 1;
        this.nitroLevel = 1;
    }

    public void setUpgradeLevels(
            int speedLevel,
            int handlingLevel,
            int nitroLevel
    ) {

        this.speedLevel =
                Math.max(1, speedLevel);

        this.handlingLevel =
                Math.max(1, handlingLevel);

        this.nitroLevel =
                Math.max(1, nitroLevel);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPosition(
            float x,
            float y
    ) {

        this.x = x;
        this.y = y;
    }

    public void move(
            float amount
    ) {

        x += amount;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {

        return baseSpeed
                + (speedLevel - 1) * 22f;
    }

    public float getHandling() {

        return baseHandling
                + (handlingLevel - 1) * 1.5f;
    }

    public float getNitro() {

        return baseNitro
                + (nitroLevel - 1) * 18f;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public int getHandlingLevel() {
        return handlingLevel;
    }

    public int getNitroLevel() {
        return nitroLevel;
    }

    public int getUpgradeCost(
            int currentLevel
    ) {

        return 100
                + (currentLevel - 1) * 100;
    }
}
