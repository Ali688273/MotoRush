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
import ir.motorush.game.game.EconomyConfig;
import ir.motorush.game.game.PlayerProgress;
import ir.motorush.game.game.ProgressManager;
import ir.motorush.game.game.UpgradeType;

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

    private String message = "";
    private float messageTimer = 0f;

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

        if (messageTimer > 0f) {
            messageTimer -= delta;
        }

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
                width * 0.06f,
                height * 0.08f,
                width * 0.88f,
                height * 0.82f
        );

        shapes.end();
    }

    private void drawGarage() {

        BikeData bike =
                bikes.get(selectedIndex);

        String bikeId =
                bike.getId();

        boolean unlocked =
                progress.isBikeUnlocked(
                        bikeId
                );

        int speedLevel =
                progressManager.getUpgradeLevel(
                        bikeId,
                        UpgradeType.SPEED
                );

        int handlingLevel =
                progressManager.getUpgradeLevel(
                        bikeId,
                        UpgradeType.HANDLING
                );

        int nitroLevel =
                progressManager.getUpgradeLevel(
                        bikeId,
                        UpgradeType.NITRO
                );

        batch.begin();

        font.setColor(Color.WHITE);

        font.getData().setScale(2.0f);

        font.draw(
                batch,
                "GARAGE",
                width * 0.38f,
                height * 0.94f
        );

        font.getData().setScale(1.15f);

        font.draw(
                batch,
                "COINS: "
                        + progress.getCoins(),
                width * 0.08f,
                height * 0.87f
        );

        font.getData().setScale(1.65f);

        font.draw(
                batch,
                bike.getName(),
                width * 0.35f,
                height * 0.76f
        );

        font.getData().setScale(1.0f);

        font.draw(
                batch,
                "SPEED       LV "
                        + speedLevel,
                width * 0.16f,
                height * 0.65f
        );

        font.draw(
                batch,
                "HANDLING    LV "
                        + handlingLevel,
                width * 0.16f,
                height * 0.59f
        );

        font.draw(
                batch,
                "NITRO       LV "
                        + nitroLevel,
                width * 0.16f,
                height * 0.53f
        );

        if (!unlocked) {

            font.getData().setScale(1.15f);

            font.draw(
                    batch,
                    "BUY: "
                            + bike.getPrice()
                            + " COINS",
                    width * 0.34f,
                    height * 0.43f
            );

        } else {

            font.getData().setScale(1.0f);

            font.draw(
                    batch,
                    "SPEED UPGRADE: "
                            + getUpgradeCostText(
                            bikeId,
                            UpgradeType.SPEED
                    ),
                    width * 0.12f,
                    height * 0.43f
            );

            font.draw(
                    batch,
                    "HANDLING UPGRADE: "
                            + getUpgradeCostText(
                            bikeId,
                            UpgradeType.HANDLING
                    ),
                    width * 0.12f,
                    height * 0.38f
            );

            font.draw(
                    batch,
                    "NITRO UPGRADE: "
                            + getUpgradeCostText(
                            bikeId,
                            UpgradeType.NITRO
                    ),
                    width * 0.12f,
                    height * 0.33f
            );

            if (
                    bikeId.equals(
                            progress
                                    .getSelectedBikeId()
                    )
            ) {

                font.draw(
                        batch,
                        "SELECTED",
                        width * 0.42f,
                        height * 0.27f
                );

            } else {

                font.draw(
                        batch,
                        "TAP CENTER TO SELECT",
                        width * 0.32f,
                        height * 0.27f
                );
            }
        }

        font.getData().setScale(2f);

        font.draw(
                batch,
                "<",
                width * 0.08f,
                height * 0.27f
        );

        font.draw(
                batch,
                ">",
                width * 0.88f,
                height * 0.27f
        );

        font.getData().setScale(0.95f);

        font.draw(
                batch,
                "Tap bottom to return",
                width * 0.36f,
                height * 0.12f
        );

        if (
                messageTimer > 0f
                        && !message.isEmpty()
        ) {

            font.setColor(
                    Color.YELLOW
            );

            font.getData().setScale(1.15f);

            font.draw(
                    batch,
                    message,
                    width * 0.25f,
                    height * 0.18f
            );

            font.setColor(
                    Color.WHITE
            );
        }

        batch.end();
    }

    private String getUpgradeCostText(
            String bikeId,
            UpgradeType type
    ) {

        int level =
                progressManager.getUpgradeLevel(
                        bikeId,
                        type
                );

        if (
                level
                        >= EconomyConfig.MAX_UPGRADE_LEVEL
        ) {

            return "MAX";
        }

        return String.valueOf(
                EconomyConfig.getUpgradeCost(
                        type,
                        level
                )
        );
    }

    private void handleTouch() {

        if (!Gdx.input.justTouched()) {
            return;
        }

        float x =
                Gdx.input.getX();

        float y =
                height - Gdx.input.getY();

        if (
                y < height * 0.16f
        ) {

            progressManager.save(
                    progress
            );

            game.setScreen(
                    new MainMenuScreen(game)
            );

            return;
        }

        if (
                y > height * 0.20f
                        && y < height * 0.33f
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

            selectOrBuyBike();

            return;
        }

        BikeData bike =
                bikes.get(selectedIndex);

        if (
                !progress.isBikeUnlocked(
                        bike.getId()
                )
        ) {

            buyBike();

            return;
        }

        if (
                y > height * 0.38f
                        && y < height * 0.46f
        ) {

            upgrade(
                    UpgradeType.SPEED
            );

            return;
        }

        if (
                y > height * 0.33f
                        && y <= height * 0.38f
        ) {

            upgrade(
                    UpgradeType.HANDLING
            );

            return;
        }

        if (
                y > height * 0.28f
                        && y <= height * 0.33f
        ) {

            upgrade(
                    UpgradeType.NITRO
            );
        }
    }

    private void selectOrBuyBike() {

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

            showMessage(
                    "BIKE SELECTED"
            );

            return;
        }

        buyBike();
    }

    private void buyBike() {

        BikeData bike =
                bikes.get(selectedIndex);

        int price =
                bike.getPrice();

        if (
                progress.getCoins()
                        < price
        ) {

            showMessage(
                    "NOT ENOUGH COINS"
            );

            return;
        }

        if (
                progress.spendCoins(
                        price
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

            showMessage(
                    "BIKE PURCHASED"
            );
        }
    }

    private void upgrade(
            UpgradeType type
    ) {

        BikeData bike =
                bikes.get(selectedIndex);

        String bikeId =
                bike.getId();

        if (
                !progress.isBikeUnlocked(
                        bikeId
                )
        ) {

            showMessage(
                    "BUY BIKE FIRST"
            );

            return;
        }

        int level =
                progressManager.getUpgradeLevel(
                        bikeId,
                        type
                );

        if (
                level
                        >= EconomyConfig.MAX_UPGRADE_LEVEL
        ) {

            showMessage(
                    "MAX LEVEL"
            );

            return;
        }

        int cost =
                EconomyConfig.getUpgradeCost(
                        type,
                        level
                );

        if (
                progress.getCoins()
                        < cost
        ) {

            showMessage(
                    "NOT ENOUGH COINS"
            );

            return;
        }

        if (
                progress.spendCoins(
                        cost
                )
        ) {

            progressManager.saveUpgradeLevel(
                    bikeId,
                    type,
                    level + 1
            );

            progressManager.save(
                    progress
            );

            showMessage(
                    "UPGRADE COMPLETE"
            );
        }
    }

    private void showMessage(
            String text
    ) {

        message = text;
        messageTimer = 1.5f;
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
