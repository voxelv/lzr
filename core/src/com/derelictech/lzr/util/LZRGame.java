package com.derelictech.lzr.util;

import com.badlogic.gdx.Game;
import com.derelictech.lzr.screens.GameScreen;

/**
 * Created by Tim on 3/22/2016.
 */
public class LZRGame extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}
