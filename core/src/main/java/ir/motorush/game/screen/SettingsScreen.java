package ir.motorush.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ir.motorush.game.MotoRushGame;

public class SettingsScreen implements Screen {

    private final MotoRushGame game;

    private SpriteBatch batch;
    private BitmapFont font;

    public SettingsScreen(MotoRushGame game) {
        this.game = game;

        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(
                0.04f,
                0.05f,
                0.07f,
                1f
        );

        Gdx.gl.glClear(
                GL20.GL_COLOR_BUFFER_BIT
        );

        batch.begin();

        font.getData().setScale(2f);

        font.draw(
                batch,
                "SETTINGS",
                50,
                Gdx.graphics.getHeight() - 50
        );

        font.getData().setScale(1.2f);

        font.draw(
                batch,
                "Sound: ON",
                50,
                Gdx.graphics.getHeight() / 2f + 40
        );

        font.draw(
                batch,
                "Music: ON",
                50,
                Gdx.graphics.getHeight() / 2f - 20
        );

        batch.end();

        if (Gdx.input.justTouched()) {

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
        font.dispose();
    }
}
