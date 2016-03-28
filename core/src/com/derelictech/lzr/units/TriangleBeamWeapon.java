package com.derelictech.lzr.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.derelictech.lzr.effects.LaserBeam;
import com.derelictech.lzr.screens.WelcomeScreen;
import com.derelictech.lzr.util.AbstractLZRActorGroup;

/**
 * Created by Tim on 3/26/2016.
 */
public class TriangleBeamWeapon extends AbstractLZRActorGroup {

    private LaserBeam beam;
    private static float rotationSpeed = 180;

    private boolean firing = false;

    private Action rotate = new Action() {
        @Override
        public boolean act(float delta) {
            Vector2 mouse = WelcomeScreen.viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            lookAt(mouse);
            return false;
        }
    };

    private class RotateTo extends Action {
        private Vector2 coords;
        private boolean follow = false;
        private Actor followActor;

        public RotateTo() {
            coords = new Vector2();
        }
        public RotateTo(Vector2 v) {
            super();
            coords = new Vector2(v);
        }
        public RotateTo(float x, float y) {
            coords = new Vector2(x, y);
        }

        public void setCoords(float x, float y) {
            coords.set(x, y);
        }

        public void setFollowActor(Actor followActor) {
            this.followActor = followActor;
        }

        @Override
        public boolean act(float delta) {
            if(follow) {
                setCoords(followActor.getX(), followActor.getY());
            }

            float angle = (float) (MathUtils.radiansToDegrees * Math.atan2(coords.y - getY() - getOriginY(),
                    coords.x - getX() - getOriginX())); // angle between -180 and 180

            boolean ccw;
            if(getRotation() - angle > 0) {
                ccw = Math.abs(getRotation() - angle) >= 180;
            }
            else {
                ccw = Math.abs(getRotation() - angle) <= 180;
            }

            if(ccw) {
                // End condition
                if (getRotation() + (delta * rotationSpeed) > angle && angle > getRotation()) {
                    lookAt(coords);
                    beam.setLength(localToStageCoordinates(new Vector2 (beam.getX(), beam.getY())).dst(coords));
                    firing = true;
                    removeAction(rotateTo);
                    return true; // Done
                }

                // Default, rotate ccw
                rotateBy(delta * rotationSpeed);
                if (getRotation() > 180) setRotation(-180 + (getRotation() - 180));
                return false;
            }
            else {
                // End condition
                if (getRotation() - (delta * rotationSpeed) < angle && angle < getRotation()) {
                    lookAt(coords);
                    beam.setLength(localToStageCoordinates(new Vector2 (beam.getX(), beam.getY())).dst(coords));
                    firing = true;
                    removeAction(rotateTo);
                    return true; // Done
                }

                // Default, rotate cw
                rotateBy(-(delta * rotationSpeed));
                if(getRotation() < -180) setRotation(180 - (getRotation() + 180));
                return false;
            }
        }
    }
    private RotateTo rotateTo = new RotateTo();

    public TriangleBeamWeapon() {
        super("triangle");
        setOrigin(12.5f, 24.5f);

        beam = new LaserBeam(new Vector2(48, 22), new Vector2(200, 24));
        addActor(beam);
    }

    public void lookAt(Vector2 v) {
        setRotation(
                (float) (
                        (180f / Math.PI) *
                                Math.atan2(v.y - getY() - getOriginY(),
                                        v.x - getX() - getOriginX())
                )
        );
    }

    public void rotateToPoint(Vector2 v) {
        rotateTo.setCoords(v.x, v.y);
        if(!getActions().contains(rotateTo, true)) {
            this.addAction(rotateTo);
        }
    }

    public void rotateToPoint(float x, float y) {
        rotateTo.setCoords(x, y);
        if(!getActions().contains(rotateTo, true)) {
            this.addAction(rotateTo);
        }
    }

    public void followActor(Actor a) {
        rotateTo.setFollowActor(a);
        if(!getActions().contains(rotateTo, true)) {
            this.addAction(rotateTo);
        }
    }

    public void fireAt(float x, float y) {
        stopFiring();
        rotateToPoint(x, y);
    }

    public void stopFiring() {
        beam.setLength(0);
        firing = false;
    }
}
