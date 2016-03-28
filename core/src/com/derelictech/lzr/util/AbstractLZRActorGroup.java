package com.derelictech.lzr.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by Tim on 3/26/2016.
 */
public abstract class AbstractLZRActorGroup extends Group implements LZRClickHandler {
    TextureRegion region;
    Pixmap selector;

    boolean selected = false;

    public AbstractLZRActorGroup(String name) {
        this.region = Assets.inst.getRegion(name);
        if(this.region == null) {
            throw new NullPointerException("Region doesn't exist");
        }

        setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());

        setTouchable(Touchable.enabled);

        selector = new Pixmap((int) getWidth() + 2, (int) getHeight(), Pixmap.Format.RGBA8888);
        selector.setColor(Color.GREEN);
        selector.drawRectangle(0, 0, selector.getWidth(), selector.getHeight());
        selector.drawRectangle(1, 1, selector.getWidth() - 2, selector.getHeight() - 2);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return super.hit(x, y, touchable);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        drawChildren(batch, parentAlpha);
        resetTransform(batch);
        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
        if(selected) {
            drawSelector(batch, parentAlpha);
        }
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
}
