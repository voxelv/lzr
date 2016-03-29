package com.derelictech.lzr.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.derelictech.lzr.util.Assets;

/**
 * Created by Tim on 3/28/2016.
 */
public class Shield extends Actor {

    TextureRegion region;

    public Shield() {
        region = Assets.inst.getRegion("shield");

        setBounds(getX(), getY(), region.getRegionWidth(), region.getRegionHeight());
        setOrigin(region.getRegionWidth()/2, region.getRegionHeight()/2);
        setColor(Color.SKY);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = batch.getColor();
        batch.setColor(getColor());
        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                getParent().getWidth(), getParent().getHeight(), 1.0f, 1.0f, this.getRotation());
        batch.setColor(c);
    }
}
