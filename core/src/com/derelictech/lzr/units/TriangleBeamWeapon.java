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
    private static float rotationSpeed = 100;
    private static float laserDPS = 25;

    private boolean selecting = false; // Ignore a click if it was the selecting click

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
            } else { // That was the selecting click, ignore once
                selecting = false;
                result = true;
            }

            return result;
        }
    };

    public boolean leftClickAction(InputEvent event, float x, float y) {
        if (isSelected()) {
            stopFiring();

            UsesResources actor;
            if(event.getTarget() instanceof UsesResources) {
                actor = (UsesResources) event.getTarget();
                rotateTo(actor, x, y);
            }
            else {
                rotateTo(null, x, y);
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

    private class FireLaser extends Action {
        private UsesResources actor;
        private Vector2 coords;
        public FireLaser(UsesResources actor, Vector2 coords) {
            this.actor = actor;
            this.coords = coords;
        }

        public void setActor(UsesResources actor) {
            this.actor = actor;
        }

        public void setCoords(Vector2 coords) {
            this.coords = coords;
        }

        public void stopFiring() {
            beam.setLength(0);
            this.actor = null;
            this.coords = null;
            removeAction(this);
        }

        @Override
        public boolean act(float delta) {
            if(actor != null && coords != null) {
                beam.setLength(localToStageCoordinates(new Vector2(beam.getX(), beam.getY())).dst(coords));
                actor.takeDamage(delta * laserDPS);
            }
            else if(actor == null && coords != null) {
                beam.setLength(localToStageCoordinates(new Vector2(beam.getX(), beam.getY())).dst(coords));
            }
            return false;
        }
    }
    private FireLaser fireLaser = new FireLaser(null, null);

    private class RotateTo extends Action {
        private Vector2 coords;
        private boolean follow = false;
        private UsesResources followActor;
        private boolean fireAfterRotate;

        public RotateTo() {
            coords = new Vector2();
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
                    if(fireAfterRotate) fireAt(followActor, coords);
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
                    if(fireAfterRotate) fireAt(followActor, coords);
                    return true; // Done
                }

                // Default, rotate cw
                rotateBy(-(delta * rotationSpeed));
                if(getRotation() < -180) setRotation(180 - (getRotation() + 180));
                return false;
            }
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

        public void fireAfterRotate(boolean b) {
            fireAfterRotate = true;
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

    public void rotateTo(UsesResources actor, float x, float y) {
        if(actor != null) {
            rotateTo.setFollowActor(actor);
            rotateTo.fireAfterRotate(true);
        }
        else {
            rotateTo.setFollowActor(null);
            rotateTo.setCoords(x, y);
        }

        if(!getActions().contains(rotateTo, true)) {
            this.addAction(rotateTo);
        }
    }

    public void fireAt(UsesResources actor, Vector2 coords) {
        fireLaser.setActor(actor);
        fireLaser.setCoords(coords);
        addAction(fireLaser);
    }

    public void stopFiring() {
        fireLaser.stopFiring();
    }
}
