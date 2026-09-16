package ir.motorush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MotoRushGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture logo;

    @Override
    public void create() {
        batch = new SpriteBatch();

        logo = new Texture(Gdx.files.internal("logo.png"));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.04f, 0.05f, 0.07f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float logoWidth = Math.min(width * 0.65f, 700f);
        float logoHeight = logoWidth * logo.getHeight() / logo.getWidth();

        batch.draw(
                logo,
                (width - logoWidth) / 2f,
                (height - logoHeight) / 2f,
                logoWidth,
                logoHeight
        );

        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }

        if (logo != null) {
            logo.dispose();
        }
    }
}
