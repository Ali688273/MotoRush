package ir.motorush.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ir.motorush.game.MotoRushGame;

public class RaceScreen implements Screen {

    private final MotoRushGame game;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;

    public RaceScreen(MotoRushGame game) {
        this.game = game;

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(
                0.08f,
                0.10f,
                0.12f,
                1f
        );

        Gdx.gl.glClear(
                GL20.GL_COLOR_BUFFER_BIT
        );

        shapes.begin(
                ShapeRenderer.ShapeType.Filled
        );

        shapes.setColor(
                0.10f,
                0.12f,
                0.14f,
                1f
        );

        shapes.rect(
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );

        shapes.end();

        batch.begin();

        font.getData().setScale(2f);

        font.draw(
                batch,
                "RACE",
                50,
                Gdx.graphics.getHeight() - 50
        );

        font.getData().setScale(1.3f);

        font.draw(
                batch,
                "Race system coming next...",
                50,
                Gdx.graphics.getHeight() / 2f
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
        shapes.dispose();
        font.dispose();
    }
}
