package com.derelictech.lzr.util;

import com.badlogic.gdx.math.Vector2;
import com.derelictech.lzr.units.Army;

/**
 * Created by Tim on 3/28/2016.
 */
public interface UsesResources {
    float getX();
    float getY();
    float getOriginX();
    float getOriginY();

    Army getArmy();

    float setMaxEnergy(float amount);
    float decreaseEnergyBy(float amount);
    float increaseEnergyBy(float amount);
    float getEnergy();

    float setMaxHP(float amount);
    float decreaseHPBy(float amount);
    float increaseHPBy(float amount);
    float getHP();

    float takeDamage(float amount);

    Vector2 getDestroyPoint();
    void destroy();
    boolean isDestroyed();
}
