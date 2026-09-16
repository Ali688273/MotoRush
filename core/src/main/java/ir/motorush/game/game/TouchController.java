package ir.motorush.game.game;

import com.badlogic.gdx.Gdx;

public class TouchController {

    private boolean left;
    private boolean right;
    private boolean nitro;

    private float screenWidth;
    private float screenHeight;

    public TouchController() {
        updateScreenSize();
    }

    public void updateScreenSize() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    public void update() {

        left = false;
        right = false;
        nitro = false;

        int touchCount = Gdx.input.getMaxPointers();

        for (int pointer = 0; pointer < touchCount; pointer++) {

            if (!Gdx.input.isTouched(pointer)) {
                continue;
            }

            float x = Gdx.input.getX(pointer);
            float y = screenHeight - Gdx.input.getY(pointer);

            if (isLeftButton(x, y)) {
                left = true;
            }

            if (isRightButton(x, y)) {
                right = true;
            }

            if (isNitroButton(x, y)) {
                nitro = true;
            }
        }
    }

    private boolean isLeftButton(float x, float y) {

        return x < screenWidth * 0.30f
                && y < screenHeight * 0.32f;
    }

    private boolean isRightButton(float x, float y) {

        return x > screenWidth * 0.70f
                && y < screenHeight * 0.32f;
    }

    private boolean isNitroButton(float x, float y) {

        return x >= screenWidth * 0.42f
                && x <= screenWidth * 0.58f
                && y < screenHeight * 0.27f;
    }

    public boolean isLeftPressed() {
        return left;
    }

    public boolean isRightPressed() {
        return right;
    }

    public boolean isNitroPressed() {
        return nitro;
    }
}
