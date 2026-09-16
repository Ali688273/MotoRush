package ir.motorush.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
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

    private boolean paused;

    public RaceScreen(MotoRushGame game) {

        this.game = game;

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();

        screenWidth =
                Gdx.graphics.getWidth();

        screenHeight =
                Gdx.graphics.getHeight();

        world = new RaceWorld(
                screenWidth,
                screenHeight
        );

        renderer = new RaceRenderer();

        controller = new TouchController();
    }

    @Override
    public void show() {
        controller.updateScreenSize();
    }

    @Override
    public void render(float delta) {

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

        if (!paused) {
            world.update(
                    Math.min(delta, 0.05f),
                    controller
            );
        }

        drawGame();

        drawHud();

        drawControls();

        handleBackButton();
    }

    private void drawGame() {

        renderer.renderRoad(
                screenWidth,
                screenHeight,
                world
        );

        renderer.renderPlayer(
                world.getPlayer()
        );
    }

    private void drawHud() {

        batch.begin();

        font.setColor(Color.WHITE);

        font.getData().setScale(1.35f);

        font.draw(
                batch,
                "SPEED  " +
                        (int) world.getSpeed(),
                30,
                screenHeight - 30
        );

        font.draw(
                batch,
                "SCORE  " +
                        world.getScore(),
                30,
                screenHeight - 70
        );

        font.draw(
                batch,
                "COINS  " +
                        world.getCoins(),
                30,
                screenHeight - 110
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
                screenWidth -
                buttonSize -
                margin;

        float nitroSize =
                Math.min(
                        screenWidth * 0.13f,
                        120f
                );

        float nitroX =
                (screenWidth - nitroSize) / 2f;

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
                leftX + buttonSize * 0.40f,
                y + buttonSize * 0.64f
        );

        font.draw(
                batch,
                ">",
                rightX + buttonSize * 0.40f,
                y + buttonSize * 0.64f
        );

        font.getData().setScale(1.2f);

        font.draw(
                batch,
                "NITRO",
                screenWidth / 2f - 32f,
                y + nitroSize / 2f + 5f
        );

        batch.end();
    }

    private void handleBackButton() {

        if (!Gdx.input.justTouched()) {
            return;
        }

        float x =
                Gdx.input.getX();

        float y =
                screenHeight -
                Gdx.input.getY();

        // بالا سمت چپ = Pause / Menu
        if (
                x < 150f &&
                y > screenHeight - 150f
        ) {

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
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
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
