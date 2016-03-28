package com.derelictech.lzr.util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * Created by Tim on 3/27/2016.
 */
public interface LZRClickHandler {
    void select();
    void deselect();
    boolean isSelected();

    boolean leftClickAction(InputEvent event, float x, float y, int pointer, int button);
    boolean rightClickAction(InputEvent event, float x, float y, int pointer, int button);
}
