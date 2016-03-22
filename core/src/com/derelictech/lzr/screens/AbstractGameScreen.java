package com.derelictech.lzr.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by Tim on 3/22/2016.
 */
public abstract class AbstractGameScreen extends ScreenAdapter {

    Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }
}
