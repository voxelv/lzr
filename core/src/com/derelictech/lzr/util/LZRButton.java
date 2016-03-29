package com.derelictech.lzr.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.derelictech.lzr.effects.Shield;

/**
 * Created by Tim on 3/27/2016.
 */
public class LZRButton extends Button implements UsesResources {

    private long maxenergy = 1000;
    private long energy;
    private long maxhp = 100;
    private long hp;

    private Shield shield;

    public LZRButton(Drawable up, Drawable down) {
        super(up, down);
        removeListener(getClickListener());

        energy = maxenergy;
        hp = maxhp;

        setOrigin(Align.center);
        shield = new Shield();
        addActor(shield);
    }


    @Override
    public long decreaseEnergyBy(long amount) {
        energy -= amount;
        return energy;
    }

    @Override
    public long increaseEnergyBy(long amount) {
        energy += amount;
        return energy;
    }

    @Override
    public long getEnergy() {
        return energy;
    }

    @Override
    public long decreaseHPBy(long amount) {
        hp -= amount;
        return hp;
    }

    @Override
    public long increaseHPBy(long amount) {
        hp += amount;
        return hp;
    }

    @Override
    public long getHP() {
        return hp;
    }

    @Override
    public Vector2 getDestroyPoint() {
        return localToStageCoordinates(new Vector2(getOriginX(), getOriginY()));
    }

    @Override
    public long takeDamage(long damage) {
        long amount = damage;
        if(amount < energy) {
            decreaseEnergyBy(amount);
        }
        else {
            decreaseEnergyBy(energy);
            amount -= energy;
            if(amount < hp) {
                decreaseHPBy(amount);
            }
            else destroy();
        }
        return damage;
    }

    @Override
    public void destroy() {
        if (isDisabled()) return;
        setChecked(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
