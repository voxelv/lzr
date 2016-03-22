package com.derelictech.lzr.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by Tim on 3/22/2016.
 */
public class GameScreen extends ScreenAdapter {

    Game game;

    public GameScreen(Game lzrGame) {
        this.game = lzrGame;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
