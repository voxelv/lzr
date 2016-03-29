package com.derelictech.lzr.units;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Tim on 3/29/2016.
 */
public class Army extends Color {
    public static final Army PLAYER = new Army(CYAN);
    public static final Army COMPUTER = new Army(RED);

    public Army(Color color) {
        super(color);
    }
}
