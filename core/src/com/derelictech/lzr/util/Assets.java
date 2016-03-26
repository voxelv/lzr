package com.derelictech.lzr.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Tim on 3/22/2016.
 */
public class Assets implements Disposable{

    public static Assets inst = new Assets();

    TextureAtlas atlas;

    public Assets() {}

    public void init() {
        atlas = new TextureAtlas("../packs/pack.atlas");
    }

    public TextureRegion getRegion(String name) {
        for(TextureAtlas.AtlasRegion r : atlas.getRegions()) {
            if(r.name.equals(name)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}
