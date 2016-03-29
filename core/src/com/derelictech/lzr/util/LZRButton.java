package com.derelictech.lzr.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.derelictech.lzr.effects.Shield;

/**
 * Created by Tim on 3/27/2016.
 */
public class LZRButton extends AbstractLZRActorGroup implements UsesResources {

    private TextureRegion up, dn;

    private float maxenergy = 25;
    private float energy;
    private float maxhp = 50;
    private float hp;
    private boolean destroyed = false;

    private Shield shield;

    public LZRButton(String up, String dn) {
        super(up);
        this.up = region;
        this.dn = Assets.inst.getRegion(dn);

        energy = maxenergy;
        hp = maxhp;

        setOrigin(Align.center);

        drawChildrenBefore(false);
        shield = new Shield();
        addActor(shield);
    }


    @Override
    public float decreaseEnergyBy(float amount) {
        energy -= amount;
        return energy;
    }

    @Override
    public float increaseEnergyBy(float amount) {
        energy += amount;
        return energy;
    }

    @Override
    public float getEnergy() {
        return energy;
    }

    @Override
    public float decreaseHPBy(float amount) {
        hp -= amount;
        return hp;
    }

    @Override
    public float increaseHPBy(float amount) {
        hp += amount;
        return hp;
    }

    @Override
    public float getHP() {
        return hp;
    }

    @Override
    public Vector2 getDestroyPoint() {
        return localToStageCoordinates(new Vector2(getOriginX(), getOriginY()));
    }

    @Override
    public float takeDamage(float damage) {
        if(destroyed) return -1;
        float amount = damage;
        if(amount < energy) {
            decreaseEnergyBy(amount);
        }
        else {
            decreaseEnergyBy(energy);
            shield.setVisible(false);
            amount -= energy;
            if(amount < hp) {
                decreaseHPBy(amount);
            }
            else destroy();
        }
        System.out.println(energy+hp);
        return damage;
    }

    @Override
    public void destroy() {
        if (!destroyed) {
            destroyed = true;
            region = dn;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
