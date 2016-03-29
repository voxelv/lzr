package com.derelictech.lzr.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.derelictech.lzr.effects.Shield;
import com.derelictech.lzr.effects.StatusBar;

/**
 * Created by Tim on 3/26/2016.
 */
public abstract class AbstractLZRActorGroup extends Group implements UsesResources{
    protected TextureRegion region;
    private Pixmap selector;
    private Array<UsesResources> firingAtMe;

    protected Shield shield;
    protected float maxEnergy = 25;
    protected float maxHP = 50;
    protected boolean destroyed = false;

    StatusBar energyBar, hpBar;

    boolean selected = false;
    private boolean drawChildrenBefore = false;

    public AbstractLZRActorGroup(String name) {
        shield = new Shield();
        addActor(shield);

        firingAtMe = new Array<UsesResources>();

        this.region = Assets.inst.getRegion(name);
        if(this.region == null) {
            throw new NullPointerException("Region doesn't exist");
        }

        setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());

        energyBar = new StatusBar(Color.CYAN, new Vector2(this.getX(), this.getY()), maxEnergy);
        hpBar = new StatusBar(Color.RED, new Vector2(this.getX(), this.getY() + energyBar.getHeight()), maxHP);
        energyBar.setWidth(getWidth()/5);
        hpBar.setWidth(getWidth()/5);
        addActor(energyBar);
        addActor(hpBar);

        setTouchable(Touchable.enabled);

        selector = new Pixmap((int) getWidth() + 2, (int) getHeight(), Pixmap.Format.RGBA8888);
        selector.setColor(Color.GREEN);
        selector.drawRectangle(0, 0, selector.getWidth(), selector.getHeight());
        selector.drawRectangle(1, 1, selector.getWidth() - 2, selector.getHeight() - 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(drawChildrenBefore) {
            applyTransform(batch, computeTransform());
            drawChildren(batch, parentAlpha);
            resetTransform(batch);
        }

        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());

        if(!drawChildrenBefore) {
            applyTransform(batch, computeTransform());
            drawChildren(batch, parentAlpha);
            resetTransform(batch);
        }

        if(selected) {
            drawSelector(batch, parentAlpha);
        }
    }

    public void drawChildrenBefore(boolean b) {
        drawChildrenBefore = b;
    }

    private void drawSelector(Batch batch, float parentAlpha) {
        batch.draw(new TextureRegion(new Texture(selector)), this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                selector.getWidth(), selector.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public boolean isSelected(){
        return selected;
    }


    @Override
    public float setMaxEnergy(float amount) {
        maxEnergy = amount;
        energyBar.setMaxValue(amount);
        return maxEnergy;
    }

    @Override
    public float setMaxHP(float amount) {
        maxHP = amount;
        hpBar.setMaxValue(amount);
        return maxHP;
    }

    @Override
    public float decreaseEnergyBy(float amount) {
        energyBar.decr(amount);
        return energyBar.getValue();
    }

    @Override
    public float increaseEnergyBy(float amount) {
        energyBar.incr(amount);
        return energyBar.getValue();
    }

    @Override
    public float getEnergy() {
        return energyBar.getValue();
    }

    @Override
    public float decreaseHPBy(float amount) {
        hpBar.decr(amount);
        return hpBar.getValue();
    }

    @Override
    public float increaseHPBy(float amount) {
        hpBar.incr(amount);
        return hpBar.getValue();
    }

    @Override
    public float getHP() {
        return hpBar.getValue();
    }

    @Override
    public Vector2 getDestroyPoint() {
        return localToStageCoordinates(new Vector2(getOriginX(), getOriginY()));
    }

    @Override
    public float takeDamage(float damage) {
        if(destroyed) return -1;
        float amount = damage;
        if(amount < energyBar.getValue()) {
            decreaseEnergyBy(amount);
        }
        else {
            decreaseEnergyBy(energyBar.getValue());
            shield.setVisible(false);
            amount -= energyBar.getValue();
            if(amount < hpBar.getValue()) {
                decreaseHPBy(amount);
            }
            else destroy();
        }
        return damage;
    }

    @Override
    public void destroy() {
        if (!destroyed) {
            destroyed = true;
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
