package com.derelictech.lzr.units;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.derelictech.lzr.util.AbstractLZRActorGroup;

/**
 * Created by Tim on 3/26/2016.
 */
public class TriangleBeamWeapon extends AbstractLZRActorGroup {

    private class FireArea extends Actor {
        public Rectangle zone = new Rectangle(this.getX(), this.getY(), 1000, 3);

        public FireArea() {
        }
    }

    Action rotate = new Action() {
        @Override
        public boolean act(float delta) {
            rotateBy(delta * 45.0f);
            return false;
        }
    };

    Action sense = new Action() {
        @Override
        public boolean act(float delta) {
            rotateBy(delta * 45.0f);
            return false;
        }
    };

    public TriangleBeamWeapon(String name) {
        super(name);
        setOrigin(12, 24);

        addActor(new FireArea());

        addAction(this.rotate);
    }
}
