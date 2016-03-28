package com.derelictech.lzr.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.derelictech.lzr.util.Assets;

/**
 * Created by Tim on 3/26/2016.
 */
public class LaserBeam extends Actor {
    private Color color;

    private Vector2 start;
    private Vector2 end;

    private TextureRegion region;

    public LaserBeam(Vector2 start, Vector2 end) {
        this(Color.CYAN, start, end);
    }

    public LaserBeam(Color color, Vector2 start, Vector2 end) {
        this.region = Assets.inst.getRegion("beam");

        setOrigin(0, 2);

        this.color = color;
        this.start = start;
        this.end = end;

        setPosition(start.x, start.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = batch.getColor();
        batch.setColor(color);
        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                start.dst(end), region.getRegionHeight(), 1.0f, 1.0f, this.getRotation());
        batch.setColor(c);
    }

    public void setLength(float len) {
        end.x = len + getX();
    }
}
