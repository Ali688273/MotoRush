package ir.motorush.game.game;

import com.badlogic.gdx.math.MathUtils;

public class RaceWorld {

    private final PlayerBike player;

    private final float roadWidth;
    private final float roadLeft;
    private final float roadRight;

    private float roadOffset;
    private float distance;

    private float speed;
    private float normalSpeed;
    private float nitroSpeed;

    private int score;
    private int coins;

    private boolean crashed;
    private float crashTimer;

    public RaceWorld(float screenWidth, float screenHeight) {

        roadWidth = screenWidth * 0.68f;

        roadLeft =
                (screenWidth - roadWidth) / 2f;

        roadRight =
                roadLeft + roadWidth;

        player = new PlayerBike(
                "starter",
                "Street Rider",
                160f,
                30f,
                5f,
                5f
        );

        player.setPosition(
                screenWidth / 2f - player.getWidth() / 2f,
                screenHeight * 0.20f
        );

        normalSpeed = 260f;
        nitroSpeed = 440f;

        speed = normalSpeed;
    }

    public void update(
            float delta,
            TouchController controller
    ) {

        if (crashed) {

            crashTimer += delta;

            if (crashTimer >= 1.5f) {
                resetAfterCrash();
            }

            return;
        }

        if (controller.isNitroPressed()) {
            speed = MathUtils.lerp(
                    speed,
                    nitroSpeed,
                    delta * 6f
            );
        } else {
            speed = MathUtils.lerp(
                    speed,
                    normalSpeed,
                    delta * 5f
            );
        }

        float steeringSpeed =
                360f + player.getHandling() * 25f;

        if (controller.isLeftPressed()) {
            player.move(
                    -steeringSpeed * delta
            );
        }

        if (controller.isRightPressed()) {
            player.move(
                    steeringSpeed * delta
            );
        }

        keepPlayerInsideRoad();

        roadOffset += speed * delta;

        distance += speed * delta * 0.01f;

        score += (int) (speed * delta * 0.1f);

        if (MathUtils.randomBoolean(
                Math.min(0.015f * delta * 60f, 0.5f)
        )) {
            coins++;
        }
    }

    private void keepPlayerInsideRoad() {

        float minX =
                roadLeft + 20f;

        float maxX =
                roadRight -
                player.getWidth() -
                20f;

        if (player.getX() < minX) {
            player.setPosition(
                    minX,
                    player.getY()
            );
        }

        if (player.getX() > maxX) {
            player.setPosition(
                    maxX,
                    player.getY()
            );
        }
    }

    public void crash() {

        if (!crashed) {
            crashed = true;
            crashTimer = 0f;
            speed = 0f;
        }
    }

    private void resetAfterCrash() {

        crashed = false;
        crashTimer = 0f;
        speed = normalSpeed;
    }

    public PlayerBike getPlayer() {
        return player;
    }

    public float getRoadWidth() {
        return roadWidth;
    }

    public float getRoadLeft() {
        return roadLeft;
    }

    public float getRoadRight() {
        return roadRight;
    }

    public float getRoadOffset() {
        return roadOffset;
    }

    public float getDistance() {
        return distance;
    }

    public float getSpeed() {
        return speed;
    }

    public int getScore() {
        return score;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isCrashed() {
        return crashed;
    }
}
