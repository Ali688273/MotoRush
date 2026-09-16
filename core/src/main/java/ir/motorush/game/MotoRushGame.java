package ir.motorush.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

import ir.motorush.game.screen.MainMenuScreen;

public class MotoRushGame extends Game {

    private Texture logo;

    @Override
    public void create() {
        logo = new Texture("logo.png");
        setScreen(new MainMenuScreen(this));
    }

    public Texture getLogo() {
        return logo;
    }

    @Override
    public void dispose() {
        super.dispose();

        if (logo != null) {
            logo.dispose();
        }
    }
}
