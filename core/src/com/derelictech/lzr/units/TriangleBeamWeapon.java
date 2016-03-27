package com.derelictech.lzr.units;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.derelictech.lzr.effects.LaserBeam;
import com.derelictech.lzr.util.AbstractLZRActorGroup;

/**
 * Created by Tim on 3/26/2016.
 */
public class TriangleBeamWeapon extends AbstractLZRActorGroup {

    private LaserBeam beam;

    Action rotate = new Action() {
        @Override
        public boolean act(float delta) {
            rotateBy(delta * 45.0f);
            return false;
        }
    };

    public TriangleBeamWeapon(String name) {
        super(name);
        setOrigin(12.5f, 24.5f);

        beam = new LaserBeam(new Vector2(48, 22), new Vector2(1000, 24));
        addActor(beam);

        addAction(this.rotate);
    }
}
