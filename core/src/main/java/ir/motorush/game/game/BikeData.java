package ir.motorush.game.game;

public class BikeData {

    private final String id;
    private final String name;

    private final float speed;
    private final float handling;

    private final int price;

    public BikeData(
            String id,
            String name,
            float speed,
            float handling,
            int price
    ) {

        this.id = id;
        this.name = name;
        this.speed = speed;
        this.handling = handling;
        this.price = price;
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

    public float getHandling() {
        return handling;
    }

    public int getPrice() {
        return price;
    }
}
