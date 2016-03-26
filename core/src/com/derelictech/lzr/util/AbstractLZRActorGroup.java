package com.derelictech.lzr.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by Tim on 3/26/2016.
 */
public abstract class AbstractLZRActorGroup extends Group {
    TextureRegion region;

    public AbstractLZRActorGroup(String name) {
        this.region = Assets.inst.getRegion(name);
        if(this.region == null) {
            throw new NullPointerException("Region doesn't exist");
        }

        this.setWidth(region.getRegionWidth());
        this.setHeight(region.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
    }
}
