package com.derelictech.lzr.util;

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

    long takeDamage(long amount);

    void destroy();
}
