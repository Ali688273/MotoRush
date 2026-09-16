package ir.motorush.game.game;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RaceWorld {

    private final PlayerBike player;

    private final float roadWidth;
    private final float roadLeft;
    private final float roadRight;

    private final List<EnemyBike> enemies;
    private final List<Coin> coins;

    private final ProgressManager progressManager;

    private float roadOffset;
    private float distance;

    private float speed;
    private float normalSpeed;
    private float nitroSpeed;

    private int score;
    private int coinCount;

    private boolean crashed;
    private float crashTimer;

    private boolean finished;

    private float raceTime;

    private int currentLap;
    private final int totalLaps;

    private int playerPosition;
    private final int totalRacers;

    private float enemySpawnTimer;
    private float coinSpawnTimer;

    public RaceWorld(
            float screenWidth,
            float screenHeight
    ) {

        progressManager =
                new ProgressManager();

        PlayerProgress progress =
                progressManager.load();

        BikeData selectedBike =
                BikeRepository.findById(
                        progress.getSelectedBikeId()
                );

        player =
                new PlayerBike(
                        selectedBike.getId(),
                        selectedBike.getName(),
                        160f,
                        30f,
                        selectedBike.getSpeed(),
                        selectedBike.getHandling()
                );

        player.setUpgradeLevels(
                progressManager.getUpgradeLevel(
                        selectedBike.getId(),
                        UpgradeType.SPEED
                ),
                progressManager.getUpgradeLevel(
                        selectedBike.getId(),
                        UpgradeType.HANDLING
                ),
                progressManager.getUpgradeLevel(
                        selectedBike.getId(),
                        UpgradeType.NITRO
                )
        );

        roadWidth =
                screenWidth * 0.68f;

        roadLeft =
                (screenWidth - roadWidth) / 2f;

        roadRight =
                roadLeft + roadWidth;

        player.setPosition(
                screenWidth / 2f
                        - player.getWidth() / 2f,
                screenHeight * 0.20f
        );

        normalSpeed =
                player.getSpeed() * 1.55f;

        nitroSpeed =
                normalSpeed
                        + player.getNitro() * 8f;

        speed =
                normalSpeed;

        enemies =
                new ArrayList<>();

        coins =
                new ArrayList<>();

        currentLap = 1;
        totalLaps = 3;

        totalRacers = 5;
        playerPosition = 1;

        roadOffset = 0f;
        distance = 0f;

        score = 0;
        coinCount = 0;

        crashed = false;
        crashTimer = 0f;

        finished = false;

        raceTime = 0f;

        enemySpawnTimer = 0f;
        coinSpawnTimer = 0f;
    }

    public void update(
            float delta,
            TouchController controller
    ) {

        if (finished) {
            return;
        }

        if (crashed) {

            crashTimer += delta;

            if (crashTimer >= 1.5f) {
                resetAfterCrash();
            }

            return;
        }

        raceTime += delta;

        updateSpeed(
                delta,
                controller
        );

        updatePlayer(
                delta,
                controller
        );

        updateWorld(delta);

        updateEnemies(delta);

        updateCoins(delta);

        checkEnemyCollisions();

        checkCoinCollections();

        updatePosition();

        checkLapProgress();
    }

    private void updateSpeed(
            float delta,
            TouchController controller
    ) {

        float targetSpeed =
                controller.isNitroPressed()
                        ? nitroSpeed
                        : normalSpeed;

        speed =
                MathUtils.lerp(
                        speed,
                        targetSpeed,
                        delta * 6f
                );
    }

    private void updatePlayer(
            float delta,
            TouchController controller
    ) {

        float steeringSpeed =
                360f
                        + player.getHandling() * 25f;

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
    }

    private void updateWorld(
            float delta
    ) {

        roadOffset +=
                speed * delta;

        distance +=
                speed * delta * 0.01f;

        score +=
                (int) (
                        speed
                                * delta
                                * 0.1f
                );

        enemySpawnTimer += delta;
        coinSpawnTimer += delta;

        if (
                enemySpawnTimer >= 1.2f
        ) {

            spawnEnemy();

            enemySpawnTimer = 0f;
        }

        if (
                coinSpawnTimer >= 0.8f
        ) {

            spawnCoin();

            coinSpawnTimer = 0f;
        }
    }

    private void updateEnemies(
            float delta
    ) {

        Iterator<EnemyBike> iterator =
                enemies.iterator();

        while (iterator.hasNext()) {

            EnemyBike enemy =
                    iterator.next();

            enemy.update(
                    delta,
                    speed
            );

            if (!enemy.isActive()) {
                iterator.remove();
            }
        }
    }

    private void updateCoins(
            float delta
    ) {

        Iterator<Coin> iterator =
                coins.iterator();

        while (iterator.hasNext()) {

            Coin coin =
                    iterator.next();

            coin.update(
                    delta,
                    speed
            );

            if (
                    coin.isCollected()
                            || coin.getY() < -100f
            ) {

                iterator.remove();
            }
        }
    }

    private void spawnEnemy() {

        if (enemies.size() >= 6) {
            return;
        }

        float laneWidth =
                roadWidth / 3f;

        int lane =
                MathUtils.random(
                        0,
                        2
                );

        float x =
                roadLeft
                        + laneWidth * lane
                        + laneWidth / 2f
                        - 23f;

        float y =
                MathUtils.random(
                        700f,
                        1200f
                );

        float enemySpeed =
                MathUtils.random(
                        190f,
                        250f
                );

        EnemyBike enemy =
                new EnemyBike(
                        x,
                        y,
                        enemySpeed
                );

        enemy.setLane(lane);

        enemies.add(enemy);
    }

    private void spawnCoin() {

        if (coins.size() >= 8) {
            return;
        }

        float laneWidth =
                roadWidth / 3f;

        int lane =
                MathUtils.random(
                        0,
                        2
                );

        float x =
                roadLeft
                        + laneWidth * lane
                        + laneWidth / 2f;

        float y =
                MathUtils.random(
                        650f,
                        1100f
                );

        coins.add(
                new Coin(
                        x,
                        y
                )
        );
    }

    private void checkEnemyCollisions() {

        float playerX =
                player.getX();

        float playerY =
                player.getY();

        for (
                EnemyBike enemy : enemies
        ) {

            if (
                    overlaps(
                            playerX,
                            playerY,
                            player.getWidth(),
                            player.getHeight(),
                            enemy.getX(),
                            enemy.getY(),
                            enemy.getWidth(),
                            enemy.getHeight()
                    )
            ) {

                enemy.deactivate();

                crash();

                return;
            }
        }
    }

    private void checkCoinCollections() {

        float playerCenterX =
                player.getX()
                        + player.getWidth() / 2f;

        float playerCenterY =
                player.getY()
                        + player.getHeight() / 2f;

        for (
                Coin coin : coins
        ) {

            if (coin.isCollected()) {
                continue;
            }

            float dx =
                    playerCenterX
                            - coin.getX();

            float dy =
                    playerCenterY
                            - coin.getY();

            float distanceSquared =
                    dx * dx
                            + dy * dy;

            float collectDistance =
                    coin.getRadius()
                            + player.getWidth()
                            * 0.45f;

            if (
                    distanceSquared
                            <= collectDistance
                            * collectDistance
            ) {

                coin.collect();

                coinCount++;

                score += 50;
            }
        }
    }

    private boolean overlaps(
            float x1,
            float y1,
            float width1,
            float height1,
            float x2,
            float y2,
            float width2,
            float height2
    ) {

        return x1 < x2 + width2
                && x1 + width1 > x2
                && y1 < y2 + height2
                && y1 + height1 > y2;
    }

    private void updatePosition() {

        int passedEnemies = 0;

        for (
                EnemyBike enemy : enemies
        ) {

            if (
                    enemy.getY()
                            < player.getY()
            ) {

                passedEnemies++;
            }
        }

        playerPosition =
                MathUtils.clamp(
                        1 + passedEnemies,
                        1,
                        totalRacers
                );
    }

    private void checkLapProgress() {

        float lapDistance =
                1000f;

        int calculatedLap =
                (int)
                        (distance / lapDistance)
                        + 1;

        if (
                calculatedLap
                        > currentLap
        ) {

            currentLap =
                    Math.min(
                            calculatedLap,
                            totalLaps
                    );
        }

        if (
                distance
                        >= lapDistance
                        * totalLaps
        ) {

            finished = true;
        }
    }

    public void crash() {

        if (
                !crashed
                        && !finished
        ) {

            crashed = true;

            crashTimer = 0f;

            speed = 0f;

            score =
                    Math.max(
                            0,
                            score - 100
                    );
        }
    }

    private void resetAfterCrash() {

        crashed = false;

        crashTimer = 0f;

        speed = normalSpeed;

        player.setPosition(
                MathUtils.clamp(
                        player.getX(),
                        roadLeft + 20f,
                        roadRight
                                - player.getWidth()
                                - 20f
                ),
                player.getY()
        );
    }

    private void keepPlayerInsideRoad() {

        float minX =
                roadLeft + 20f;

        float maxX =
                roadRight
                        - player.getWidth()
                        - 20f;

        if (
                player.getX() < minX
        ) {

            player.setPosition(
                    minX,
                    player.getY()
            );
        }

        if (
                player.getX() > maxX
        ) {

            player.setPosition(
                    maxX,
                    player.getY()
            );
        }
    }

    public PlayerBike getPlayer() {
        return player;
    }

    public List<EnemyBike> getEnemies() {
        return enemies;
    }

    public List<Coin> getCoins() {
        return coins;
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

    public int getCoinsCollected() {
        return coinCount;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public boolean isFinished() {
        return finished;
    }

    public float getRaceTime() {
        return raceTime;
    }

    public int getCurrentLap() {
        return currentLap;
    }

    public int getTotalLaps() {
        return totalLaps;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public int getTotalRacers() {
        return totalRacers;
    }

    public RaceReward getRaceReward() {

        return new RaceReward(
                playerPosition,
                coinCount
        );
    }
}
