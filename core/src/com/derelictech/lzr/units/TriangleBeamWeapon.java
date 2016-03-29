package com.derelictech.lzr.units;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.derelictech.lzr.effects.LaserBeam;
import com.derelictech.lzr.util.AbstractLZRActorGroup;
import com.derelictech.lzr.util.UsesResources;

/**
 * Created by Tim on 3/26/2016.
 */
public class TriangleBeamWeapon extends AbstractLZRActorGroup {

    private LaserBeam beam;
    private static float rotationSpeed = 45;

    private boolean selecting = false; // Ignore a click if it was the selecting click

    private ClickListener stageListener = new ClickListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            super.touchDown(event, x, y, pointer, button);

            boolean result = false;
            if(!selecting) {
                switch (button) {
                    case Input.Buttons.LEFT:
                        result = leftClickAction(event, x, y);
                        break;
                    case Input.Buttons.MIDDLE:
                        result = middleClickAction(event, x, y);
                        break;
                    case Input.Buttons.RIGHT:
                        result = rightClickAction(event, x, y);
                        break;
                    default:
                        break;
                }
            } else {
                selecting = false;
                result = true;
            }

            return result;
        }
    };

    private ClickListener selectListener = new ClickListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            switch(button) {
                case Input.Buttons.LEFT:
                    if (isSelected()) {
                        deselect();
                    } else {
                        select();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    public boolean leftClickAction(InputEvent event, float x, float y) {
        if (isSelected()) {
            stopFiring();

            System.out.println(event.getTarget().toString());
            UsesResources actor;
            if(event.getTarget() instanceof UsesResources) {
                actor = (UsesResources) event.getTarget();
                fireAt(actor, x, y);
            }
            else {
                fireAt(null, x, y);
            }
            return true;
        } else return false;
    }

    public boolean middleClickAction(InputEvent event, float x, float y) {
        if(isSelected()) {
            deselect();
            return true;
        }
        else return false;
    }

    public boolean rightClickAction(InputEvent event, float x, float y) {
        if(isSelected()) {
            //TODO: Move to location
            return true;
        }
        else return false;
    }

    private class RotateTo extends Action {
        private Vector2 coords;
        private boolean follow = false;
        private UsesResources followActor;

        public RotateTo() {
            coords = new Vector2();
        }

        public void setCoords(Vector2 v) {
            coords.set(v.x, v.y);
            follow = false;
        }

        public void setCoords(float x, float y) {
            coords.set(x, y);
            follow = false;
        }

        public void setFollowActor(UsesResources followActor) {
            this.followActor = followActor;
            follow = true;
        }

        @Override
        public boolean act(float delta) {
            if(follow) {
                setCoords(followActor.getDestroyPoint());
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

        beam = new LaserBeam(new Vector2(48, 22), new Vector2(48, 24));
        addActor(beam);

        addListener(selectListener);
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

    public void rotateToPoint(float x, float y) {
        rotateTo.setCoords(x, y);
        if(!getActions().contains(rotateTo, true)) {
            this.addAction(rotateTo);
        }
    }

    public void fireAt(UsesResources actor, float x, float y) {
        if(actor != null) {
            rotateTo.setFollowActor(actor);
        }
        else {
            rotateTo.setFollowActor(null);
            rotateTo.setCoords(x, y);
        }

        if(!getActions().contains(rotateTo, true)) {
            this.addAction(rotateTo);
        }
    }

    public void stopFiring() {
        beam.setLength(0);
    }

    @Override
    public void select() {
        super.select();
        getStage().addListener(stageListener);
        selecting = true;
    }

    @Override
    public void deselect() {
        super.deselect();
        getStage().removeListener(stageListener);
    }
}
