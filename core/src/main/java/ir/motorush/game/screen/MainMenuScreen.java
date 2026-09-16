package ir.motorush.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;

import ir.motorush.game.MotoRushGame;

public class MainMenuScreen implements Screen {

    private final MotoRushGame game;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;

    private Texture logo;

    private float screenWidth;
    private float screenHeight;

    private float buttonWidth;
    private float buttonHeight;

    private float startY;
    private float garageY;
    private float settingsY;

    public MainMenuScreen(MotoRushGame game) {
        this.game = game;

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();

        logo = game.getLogo();

        calculateLayout();
    }

    private void calculateLayout() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        buttonWidth = Math.min(screenWidth * 0.65f, 650f);
        buttonHeight = Math.max(screenHeight * 0.10f, 80f);

        float centerX = (screenWidth - buttonWidth) / 2f;

        startY = screenHeight * 0.42f;
        garageY = startY - buttonHeight - 25f;
        settingsY = garageY - buttonHeight - 25f;
    }

    @Override
    public void show() {
        calculateLayout();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(
                0.025f,
                0.035f,
                0.055f,
                1f
        );

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        drawBackground();

        drawLogo();

        drawButtons();

        handleTouch();
    }

    private void drawBackground() {

        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(
                0.025f,
                0.035f,
                0.055f,
                1f
        );

        shapes.rect(
                0,
                0,
                screenWidth,
                screenHeight
        );

        shapes.end();
    }

    private void drawLogo() {

        if (logo == null) {
            return;
        }

        batch.begin();

        float maxWidth = Math.min(
                screenWidth * 0.55f,
                500f
        );

        float logoWidth = maxWidth;

        float logoHeight =
                logoWidth *
                logo.getHeight() /
                logo.getWidth();

        float x =
                (screenWidth - logoWidth) / 2f;

        float y =
                screenHeight * 0.68f;

        batch.draw(
                logo,
                x,
                y,
                logoWidth,
                logoHeight
        );

        batch.end();
    }

    private void drawButtons() {

        float x =
                (screenWidth - buttonWidth) / 2f;

        drawButton(
                x,
                startY,
                "START RACE"
        );

        drawButton(
                x,
                garageY,
                "GARAGE"
        );

        drawButton(
                x,
                settingsY,
                "SETTINGS"
        );
    }

    private void drawButton(
            float x,
            float y,
            String text
    ) {

        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(
                0.12f,
                0.15f,
                0.20f,
                1f
        );

        shapes.rect(
                x,
                y,
                buttonWidth,
                buttonHeight
        );

        shapes.end();

        batch.begin();

        font.getData().setScale(
                Math.max(1.5f, screenWidth / 700f)
        );

        float textWidth =
                font.getRegion().getRegionWidth();

        font.draw(
                batch,
                text,
                x + buttonWidth * 0.38f,
                y + buttonHeight * 0.60f
        );

        batch.end();
    }

    private void handleTouch() {

        if (!Gdx.input.justTouched()) {
            return;
        }

        float x = Gdx.input.getX();

        float y =
                screenHeight -
                Gdx.input.getY();

        float buttonX =
                (screenWidth - buttonWidth) / 2f;

        if (isInside(
                x,
                y,
                buttonX,
                startY
        )) {

            game.setScreen(
                    new RaceScreen(game)
            );

            return;
        }

        if (isInside(
                x,
                y,
                buttonX,
                garageY
        )) {

            game.setScreen(
                    new GarageScreen(game)
            );

            return;
        }

        if (isInside(
                x,
                y,
                buttonX,
                settingsY
        )) {

            game.setScreen(
                    new SettingsScreen(game)
            );
        }
    }

    private boolean isInside(
            float touchX,
            float touchY,
            float x,
            float y
    ) {

        return touchX >= x &&
                touchX <= x + buttonWidth &&
                touchY >= y &&
                touchY <= y + buttonHeight;
    }

    @Override
    public void resize(
            int width,
            int height
    ) {
        calculateLayout();
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
    }
}
