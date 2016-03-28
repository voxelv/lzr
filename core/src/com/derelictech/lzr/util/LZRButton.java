package com.derelictech.lzr.util;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Tim on 3/27/2016.
 */
public class LZRButton extends Button implements UsesResources {

    private long energy = 1000;
    private long hp = 100;

    public LZRButton(Drawable up, Drawable down) {
        super(up, down);
        removeListener(this.getClickListener());
        addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return false;
            }
        });
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

    }
}
