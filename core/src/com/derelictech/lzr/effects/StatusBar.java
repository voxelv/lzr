package com.derelictech.lzr.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.derelictech.lzr.util.Assets;

/**
 * Created by Tim on 3/28/2016.
 */
public class StatusBar extends Actor {

    private TextureRegion bar, bar_bg;
    private float value, maxValue;

    public StatusBar(Color color, Vector2 pos, float maxValue) {
        this.maxValue = maxValue;
        this.value = maxValue;
        setColor(color);
        this.bar = Assets.inst.getRegion("status_bar");
        this.bar_bg = Assets.inst.getRegion("status_bar_bg");

        setBounds(pos.x, pos.y, bar_bg.getRegionWidth(), bar_bg.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(bar_bg, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                getWidth(), bar_bg.getRegionHeight(), 1.0f, 1.0f, this.getRotation());

        Color c = batch.getColor();
        batch.setColor(getColor());
        batch.draw(bar, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                getWidth() * percentFull(), bar.getRegionHeight(), 1.0f, 1.0f, this.getRotation());
        batch.setColor(c);
    }

    public float getValue() {
        return value;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void decr(float amt) {
        value -= amt;
        if(value < 0) value = 0;
    }
    public void incr(float amt) {
        value += amt;
        if(value > maxValue) value = maxValue;
    }

    private float percentFull() {
        return value/maxValue;
    }

    public float setMaxValue(float amount) {
        maxValue = amount;
        return maxValue;
    }
}
