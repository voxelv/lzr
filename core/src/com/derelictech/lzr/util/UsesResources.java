package com.derelictech.lzr.util;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tim on 3/28/2016.
 */
public interface UsesResources {
    long decreaseEnergyBy(long amount);
    long increaseEnergyBy(long amount);
    long getEnergy();

    long decreaseHPBy(long amount);
    long increaseHPBy(long amount);
    long getHP();

    float getX();
    float getY();
    float getOriginX();
    float getOriginY();

    Vector2 getDestroyPoint();

    long takeDamage(long amount);

    void destroy();
}
