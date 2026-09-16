package ir.motorush.game.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RaceRenderer {

    private final ShapeRenderer shapes;

    public RaceRenderer() {
        shapes = new ShapeRenderer();
    }

    public void renderRoad(
            float screenWidth,
            float screenHeight,
            RaceWorld world
    ) {

        float roadLeft =
                world.getRoadLeft();

        float roadRight =
                world.getRoadRight();

        shapes.begin(
                ShapeRenderer.ShapeType.Filled
        );

        // Grass
        shapes.setColor(
                new Color(
                        0.04f,
                        0.22f,
                        0.08f,
                        1f
                )
        );

        shapes.rect(
                0,
                0,
                screenWidth,
                screenHeight
        );

        // Road
        shapes.setColor(
                new Color(
                        0.12f,
                        0.12f,
                        0.13f,
                        1f
                )
        );

        shapes.rect(
                roadLeft,
                0,
                world.getRoadWidth(),
                screenHeight
        );

        // Road edges
        shapes.setColor(Color.WHITE);

        shapes.rect(
                roadLeft,
                0,
                8f,
                screenHeight
        );

        shapes.rect(
                roadRight - 8f,
                0,
                8f,
                screenHeight
        );

        // Lane markings
        shapes.setColor(
                new Color(
                        0.85f,
                        0.85f,
                        0.85f,
                        1f
                )
        );

        float laneWidth =
                world.getRoadWidth() / 3f;

        float dashHeight = 70f;
        float gap = 70f;

        float offset =
                world.getRoadOffset()
                        % (dashHeight + gap);

        for (
                int lane = 1;
                lane <= 2;
                lane++
        ) {

            float x =
                    roadLeft
                            + laneWidth * lane
                            - 4f;

            for (
                    float y =
                            -dashHeight + offset;
                    y < screenHeight;
                    y += dashHeight + gap
            ) {

                shapes.rect(
                        x,
                        y,
                        8f,
                        dashHeight
                );
            }
        }

        shapes.end();
    }

    public void renderEnemies(
            RaceWorld world
    ) {

        shapes.begin(
                ShapeRenderer.ShapeType.Filled
        );

        for (
                EnemyBike enemy :
                world.getEnemies()
        ) {

            if (!enemy.isActive()) {
                continue;
            }

            drawEnemy(enemy);
        }

        shapes.end();
    }

    private void drawEnemy(
            EnemyBike enemy
    ) {

        float x =
                enemy.getX();

        float y =
                enemy.getY();

        // Wheels
        shapes.setColor(Color.BLACK);

        shapes.rect(
                x + 12f,
                y - 5f,
                10f,
                25f
        );

        shapes.rect(
                x + 12f,
                y + enemy.getHeight() - 20f,
                10f,
                25f
        );

        // Body
        shapes.setColor(
                new Color(
                        0.10f,
                        0.30f,
                        0.90f,
                        1f
                )
        );

        shapes.triangle(
                x + enemy.getWidth() / 2f,
                y + enemy.getHeight(),
                x,
                y + 15f,
                x + enemy.getWidth(),
                y + 15f
        );

        // Center
        shapes.setColor(
                Color.WHITE
        );

        shapes.rect(
                x + 16f,
                y + 28f,
                14f,
                25f
        );
    }

    public void renderCoins(
            RaceWorld world
    ) {

        shapes.begin(
                ShapeRenderer.ShapeType.Filled
        );

        shapes.setColor(
                new Color(
                        1f,
                        0.72f,
                        0.05f,
                        1f
                )
        );

        for (
                Coin coin :
                world.getCoins()
        ) {

            if (coin.isCollected()) {
                continue;
            }

            shapes.circle(
                    coin.getX(),
                    coin.getY(),
                    coin.getRadius()
            );

            shapes.setColor(
                    new Color(
                            1f,
                            0.90f,
                            0.25f,
                            1f
                    )
            );

            shapes.circle(
                    coin.getX(),
                    coin.getY(),
                    coin.getRadius() * 0.55f
            );

            shapes.setColor(
                    new Color(
                            1f,
                            0.72f,
                            0.05f,
                            1f
                    )
            );
        }

        shapes.end();
    }

    public void renderPlayer(
            PlayerBike bike
    ) {

        float x =
                bike.getX();

        float y =
                bike.getY();

        shapes.begin(
                ShapeRenderer.ShapeType.Filled
        );

        // Shadow
        shapes.setColor(
                new Color(
                        0f,
                        0f,
                        0f,
                        0.35f
                )
        );

        shapes.ellipse(
                x - 6f,
                y - 7f,
                bike.getWidth() + 12f,
                25f
        );

        // Wheels
        shapes.setColor(Color.BLACK);

        shapes.rect(
                x + 12f,
                y - 5f,
                10f,
                25f
        );

        shapes.rect(
                x + 12f,
                y + bike.getHeight() - 20f,
                10f,
                25f
        );

        // Player body
        shapes.setColor(
                new Color(
                        0.85f,
                        0.08f,
                        0.06f,
                        1f
                )
        );

        shapes.triangle(
                x + bike.getWidth() / 2f,
                y + bike.getHeight(),
                x,
                y + 15f,
                x + bike.getWidth(),
                y + 15f
        );

        // Center
        shapes.setColor(Color.WHITE);

        shapes.rect(
                x + 16f,
                y + 28f,
                14f,
                25f
        );

        shapes.end();
    }

    public void dispose() {
        shapes.dispose();
    }
}
