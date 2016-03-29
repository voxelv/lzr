package com.derelictech.lzr.util;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tim on 3/28/2016.
 */
public interface UsesResources {
    float decreaseEnergyBy(float amount);
    float increaseEnergyBy(float amount);
    float getEnergy();

    float decreaseHPBy(float amount);
    float increaseHPBy(float amount);
    float getHP();

    float getX();
    float getY();
    float getOriginX();
    float getOriginY();

    Vector2 getDestroyPoint();

    float takeDamage(float amount);

    void destroy();
}
