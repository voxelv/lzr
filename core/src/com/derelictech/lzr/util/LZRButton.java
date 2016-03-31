package com.derelictech.lzr.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Align;
import com.derelictech.lzr.units.Army;

/**
 * Created by Tim on 3/27/2016.
 */
public class LZRButton extends AbstractLZRActorGroup{

    private TextureRegion up, dn;
    private Action destroyAction;

    public LZRButton(String up, String dn) {
        super(up, Army.COMPUTER);
        this.up = region;
        this.dn = Assets.inst.getRegion(dn);

        setOrigin(Align.center);

        drawChildrenBefore(false);
    }

    @Override
    public void destroy() {
        if (!destroyed) {
            destroyed = true;
            region = dn;
            addAction(destroyAction);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void setDestroyAction(Action action) {
        destroyAction = action;
    }
}
