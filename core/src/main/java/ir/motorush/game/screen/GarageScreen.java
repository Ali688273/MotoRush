package ir.motorush.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ir.motorush.game.MotoRushGame;
import ir.motorush.game.game.BikeData;
import ir.motorush.game.game.BikeRepository;
import ir.motorush.game.game.PlayerProgress;
import ir.motorush.game.game.ProgressManager;

import java.util.List;

public class GarageScreen implements Screen {

    private final MotoRushGame game;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;

    private ProgressManager progressManager;
    private PlayerProgress progress;

    private List<BikeData> bikes;

    private int selectedIndex;

    private float width;
    private float height;

    public GarageScreen(
            MotoRushGame game
    ) {

        this.game = game;

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();

        progressManager =
                new ProgressManager();

        progress =
                progressManager.load();

        bikes =
                BikeRepository.getAllBikes();

        selectedIndex = 0;

        for (
                int i = 0;
                i < bikes.size();
                i++
        ) {

            if (
                    bikes.get(i)
                            .getId()
                            .equals(
                                    progress
                                            .getSelectedBikeId()
                            )
            ) {

                selectedIndex = i;
                break;
            }
        }

        width =
                Gdx.graphics.getWidth();

        height =
                Gdx.graphics.getHeight();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(
            float delta
    ) {

        width =
                Gdx.graphics.getWidth();

        height =
                Gdx.graphics.getHeight();

        Gdx.gl.glClearColor(
                0.025f,
                0.035f,
                0.055f,
                1f
        );

        Gdx.gl.glClear(
                GL20.GL_COLOR_BUFFER_BIT
        );

        drawBackground();

        drawGarage();

        handleTouch();
    }

    private void drawBackground() {

        shapes.begin(
                ShapeRenderer.ShapeType.Filled
        );

        shapes.setColor(
                new Color(
                        0.025f,
                        0.035f,
                        0.055f,
                        1f
                )
        );

        shapes.rect(
                0,
                0,
                width,
                height
        );

        shapes.setColor(
                new Color(
                        0.10f,
                        0.12f,
                        0.18f,
                        1f
                )
        );

        shapes.rect(
                width * 0.08f,
                height * 0.12f,
                width * 0.84f,
                height * 0.66f
        );

        shapes.end();
    }

    private void drawGarage() {

        BikeData bike =
                bikes.get(selectedIndex);

        batch.begin();

        font.setColor(Color.WHITE);

        font.getData().setScale(2.2f);

        font.draw(
                batch,
                "GARAGE",
                width * 0.38f,
                height * 0.91f
        );

        font.getData().setScale(1.2f);

        font.draw(
                batch,
                "COINS: "
                        + progress.getCoins(),
                width * 0.08f,
                height * 0.84f
        );

        font.getData().setScale(1.7f);

        font.draw(
                batch,
                bike.getName(),
                width * 0.36f,
                height * 0.70f
        );

        font.getData().setScale(1.15f);

        font.draw(
                batch,
                "SPEED   "
                        + (int) bike.getSpeed(),
                width * 0.20f,
                height * 0.57f
        );

        font.draw(
                batch,
                "HANDLING   "
                        + (int) bike.getHandling(),
                width * 0.20f,
                height * 0.50f
        );

        font.draw(
                batch,
                "PRICE   "
                        + bike.getPrice(),
                width * 0.20f,
                height * 0.43f
        );

        boolean unlocked =
                progress.isBikeUnlocked(
                        bike.getId()
                );

        font.getData().setScale(1.25f);

        if (unlocked) {

            font.draw(
                    batch,
                    bike.getId()
                            .equals(
                                    progress
                                            .getSelectedBikeId()
                            )
                            ? "SELECTED"
                            : "TAP TO SELECT",
                    width * 0.34f,
                    height * 0.29f
            );

        } else {

            font.draw(
                    batch,
                    "TAP TO BUY",
                    width * 0.37f,
                    height * 0.29f
            );
        }

        font.getData().setScale(1f);

        font.draw(
                batch,
                "<",
                width * 0.12f,
                height * 0.30f
        );

        font.draw(
                batch,
                ">",
                width * 0.86f,
                height * 0.30f
        );

        font.draw(
                batch,
                "TAP BOTTOM TO RETURN",
                width * 0.32f,
                height * 0.12f
        );

        batch.end();
    }

    private void handleTouch() {

        if (!Gdx.input.justTouched()) {
            return;
        }

        float x =
                Gdx.input.getX();

        float y =
                height
                        - Gdx.input.getY();

        if (
                y > height * 0.20f
                        && y < height * 0.40f
        ) {

            if (
                    x < width * 0.30f
            ) {

                selectedIndex--;

                if (selectedIndex < 0) {

                    selectedIndex =
                            bikes.size() - 1;
                }

                return;
            }

            if (
                    x > width * 0.70f
            ) {

                selectedIndex++;

                if (
                        selectedIndex
                                >= bikes.size()
                ) {

                    selectedIndex = 0;
                }

                return;
            }

            buyOrSelect();

            return;
        }

        if (
                y < height * 0.18f
        ) {

            progressManager.save(
                    progress
            );

            game.setScreen(
                    new MainMenuScreen(game)
            );
        }
    }

    private void buyOrSelect() {

        BikeData bike =
                bikes.get(selectedIndex);

        if (
                progress.isBikeUnlocked(
                        bike.getId()
                )
        ) {

            progress.setSelectedBikeId(
                    bike.getId()
            );

            progressManager.save(
                    progress
            );

            return;
        }

        if (
                progress.spendCoins(
                        bike.getPrice()
                )
        ) {

            progress.unlockBike(
                    bike.getId()
            );

            progress.setSelectedBikeId(
                    bike.getId()
            );

            progressManager.save(
                    progress
            );
        }
    }

    @Override
    public void resize(
            int width,
            int height
    ) {

        this.width = width;
        this.height = height;
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
