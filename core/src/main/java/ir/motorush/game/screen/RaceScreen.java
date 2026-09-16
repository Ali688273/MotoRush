package ir.motorush.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ir.motorush.game.MotoRushGame;
import ir.motorush.game.game.RaceRenderer;
import ir.motorush.game.game.RaceWorld;
import ir.motorush.game.game.TouchController;

public class RaceScreen implements Screen {

    private final MotoRushGame game;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;

    private RaceWorld world;
    private RaceRenderer renderer;
    private TouchController controller;

    private float screenWidth;
    private float screenHeight;

    private boolean finishedScreen;

    public RaceScreen(
            MotoRushGame game
    ) {

        this.game = game;

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();

        screenWidth =
                Gdx.graphics.getWidth();

        screenHeight =
                Gdx.graphics.getHeight();

        world =
                new RaceWorld(
                        screenWidth,
                        screenHeight
                );

        renderer =
                new RaceRenderer();

        controller =
                new TouchController();
    }

    @Override
    public void show() {
        controller.updateScreenSize();
    }

    @Override
    public void render(
            float delta
    ) {

        Gdx.gl.glClearColor(
                0.02f,
                0.03f,
                0.04f,
                1f
        );

        Gdx.gl.glClear(
                GL20.GL_COLOR_BUFFER_BIT
        );

        screenWidth =
                Gdx.graphics.getWidth();

        screenHeight =
                Gdx.graphics.getHeight();

        controller.update();

        if (!finishedScreen) {

            world.update(
                    Math.min(delta, 0.05f),
                    controller
            );

            if (world.isFinished()) {
                finishedScreen = true;
            }
        }

        drawGame();

        drawHud();

        if (!finishedScreen) {
            drawControls();
        } else {
            drawFinishScreen();
        }

        handleTouch();
    }

    private void drawGame() {

        renderer.renderRoad(
                screenWidth,
                screenHeight,
                world
        );

        renderer.renderCoins(
                world
        );

        renderer.renderEnemies(
                world
        );

        renderer.renderPlayer(
                world.getPlayer()
        );
    }

    private void drawHud() {

        batch.begin();

        font.setColor(Color.WHITE);

        font.getData().setScale(1.25f);

        font.draw(
                batch,
                "SPEED  " +
                        (int) world.getSpeed(),
                25,
                screenHeight - 25
        );

        font.draw(
                batch,
                "SCORE  " +
                        world.getScore(),
                25,
                screenHeight - 65
        );

        font.draw(
                batch,
                "COINS  " +
                        world.getCoins(),
                25,
                screenHeight - 105
        );

        font.draw(
                batch,
                "POS  " +
                        world.getPlayerPosition()
                        + "/"
                        + world.getTotalRacers(),
                screenWidth - 170,
                screenHeight - 25
        );

        font.draw(
                batch,
                "LAP  "
                        + world.getCurrentLap()
                        + "/"
                        + world.getTotalLaps(),
                screenWidth - 170,
                screenHeight - 65
        );

        batch.end();
    }

    private void drawControls() {

        float buttonSize =
                Math.min(
                        screenWidth * 0.16f,
                        150f
                );

        float margin = 30f;

        float y = 30f;

        float leftX = margin;

        float rightX =
                screenWidth
                        - buttonSize
                        - margin;

        float nitroSize =
                Math.min(
                        screenWidth * 0.13f,
                        120f
                );

        shapes.begin(
                ShapeRenderer.ShapeType.Filled
        );

        shapes.setColor(
                new Color(
                        0.08f,
                        0.10f,
                        0.14f,
                        0.88f
                )
        );

        shapes.rect(
                leftX,
                y,
                buttonSize,
                buttonSize
        );

        shapes.rect(
                rightX,
                y,
                buttonSize,
                buttonSize
        );

        shapes.setColor(
                new Color(
                        0.90f,
                        0.20f,
                        0.05f,
                        0.90f
                )
        );

        shapes.circle(
                screenWidth / 2f,
                y + nitroSize / 2f,
                nitroSize / 2f
        );

        shapes.end();

        batch.begin();

        font.setColor(Color.WHITE);

        font.getData().setScale(2.2f);

        font.draw(
                batch,
                "<",
                leftX
                        + buttonSize * 0.40f,
                y
                        + buttonSize * 0.64f
        );

        font.draw(
                batch,
                ">",
                rightX
                        + buttonSize * 0.40f,
                y
                        + buttonSize * 0.64f
        );

        font.getData().setScale(1.1f);

        font.draw(
                batch,
                "NITRO",
                screenWidth / 2f - 30f,
                y
                        + nitroSize / 2f
                        + 5f
        );

        batch.end();
    }

    private void drawFinishScreen() {

        shapes.begin(
                ShapeRenderer.ShapeType.Filled
        );

        shapes.setColor(
                new Color(
                        0f,
                        0f,
                        0f,
                        0.75f
                )
        );

        shapes.rect(
                0,
                0,
                screenWidth,
                screenHeight
        );

        shapes.end();

        batch.begin();

        font.setColor(Color.WHITE);

        font.getData().setScale(2.4f);

        font.draw(
                batch,
                "RACE FINISHED",
                screenWidth * 0.33f,
                screenHeight * 0.70f
        );

        font.getData().setScale(1.5f);

        font.draw(
                batch,
                "POSITION  "
                        + world.getPlayerPosition()
                        + "/"
                        + world.getTotalRacers(),
                screenWidth * 0.35f,
                screenHeight * 0.58f
        );

        font.draw(
                batch,
                "SCORE  "
                        + world.getScore(),
                screenWidth * 0.40f,
                screenHeight * 0.50f
        );

        font.draw(
                batch,
                "COINS  "
                        + world.getCoins(),
                screenWidth * 0.40f,
                screenHeight * 0.43f
        );

        font.getData().setScale(1.2f);

        font.draw(
                batch,
                "TAP TO RETURN",
                screenWidth * 0.40f,
                screenHeight * 0.28f
        );

        batch.end();
    }

    private void handleTouch() {

        if (!Gdx.input.justTouched()) {
            return;
        }

        if (finishedScreen) {

            game.setScreen(
                    new MainMenuScreen(game)
            );
        }
    }

    @Override
    public void resize(
            int width,
            int height
    ) {

        screenWidth = width;
        screenHeight = height;

        controller.updateScreenSize();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

        batch.dispose();
        shapes.dispose();
        font.dispose();

        renderer.dispose();
    }
}
