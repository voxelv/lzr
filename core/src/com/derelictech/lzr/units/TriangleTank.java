package com.derelictech.lzr.units;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.derelictech.lzr.effects.LaserBeam;
import com.derelictech.lzr.util.AbstractLZRActorGroup;
import com.derelictech.lzr.util.UsesResources;

/**
 * Created by Tim on 3/26/2016.
 */
public class TriangleTank extends AbstractLZRActorGroup {

    private LaserBeam beam;
    private static float rotationSpeed = 100;
    private static float moveSpeed = 400;
    private static float laserDPS = 25;
    private UsesResources targetActor;
    private Vector2 targetPos = new Vector2();

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

            if(event.getTarget() instanceof UsesResources) {
                targetActor = (UsesResources) event.getTarget();
                targetPos.set(targetActor.getDestroyPoint());
                rotateAndFire();
            }
            else {
                targetActor = null;
                targetPos.set(x, y);
                rotateAndFire();
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
        if (isSelected()) {
            //TODO: Move to location
            targetPos.set(x, y);
            stopFiring();
            rotateAndMove();
            return true;
        } else return false;
    }

    private class MoveTo extends Action {

        @Override
        public boolean act(float delta) {
            return false;
        }
    }
    private MoveToAction moveTo = new MoveToAction();

    private class FireLaser extends Action {

        public void stopFiring() {
            beam.setLength(0);
            removeAction(this);
        }

        @Override
        public boolean act(float delta) {
            if(targetActor != null && targetPos != null) {
                beam.setLength(localToStageCoordinates(new Vector2(beam.getX(), beam.getY())).dst(targetPos));
                targetActor.takeDamage(delta * laserDPS);
                if(targetActor.isDestroyed()) stopFiring();
            }
            else if(targetActor == null && targetPos != null) {
                beam.setLength(localToStageCoordinates(new Vector2(beam.getX(), beam.getY())).dst(targetPos));
            }
            return false;
        }
    }
    private FireLaser fireLaser = new FireLaser();

    private class RotateTo extends Action {
        private boolean follow = false;
        private Action nextAction;

        @Override
        public boolean act(float delta) {
            if(follow) {
                setCoords(targetActor.getDestroyPoint());
            }

            float angle = (float) (MathUtils.radiansToDegrees * Math.atan2(targetPos.y - getY() - getOriginY(),
                    targetPos.x - getX() - getOriginX())); // angle between -180 and 180

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
                    lookAt(targetPos);
                    if(nextAction != null) addAction(nextAction);
                    return true;

                }

                // Default, rotate ccw
                rotateBy(delta * rotationSpeed);
                if (getRotation() > 180) setRotation(-180 + (getRotation() - 180));
                return false;
            }
            else {
                // End condition
                if (getRotation() - (delta * rotationSpeed) < angle && angle < getRotation()) {
                    lookAt(targetPos);
                    if(nextAction != null) addAction(nextAction);
                    return true;
                }

                // Default, rotate cw
                rotateBy(-(delta * rotationSpeed));
                if(getRotation() < -180) setRotation(180 - (getRotation() + 180));
                return false;
            }
        }

        public void setCoords(Vector2 v) {
            targetPos.set(v.x, v.y);
            follow = false;
        }

        public void setCoords(float x, float y) {
            targetPos.set(x, y);
            follow = false;
        }

        public void setNextAction(Action action) {
            nextAction = action;
        }
    }
    private RotateTo rotateTo = new RotateTo();

    public TriangleTank() {
        super("triangle");
        shield.setVisible(false);

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

    public void rotateAndFire() {
        stopFiring();
        rotateTo.setNextAction(fireLaser);

        if(!getActions().contains(rotateTo, true)) {
            this.addAction(rotateTo);
        }
    }

    public void rotateAndMove() {
        stopFiring();
        removeAction(moveTo);
        moveTo.reset();
        moveTo.setPosition(targetPos.x - getOriginX(), targetPos.y - getOriginY());
        moveTo.setDuration(targetPos.dst(getX(), getY()) / moveSpeed);
        rotateTo.setNextAction(moveTo);

        if(!getActions().contains(rotateTo, true)) {
            this.addAction(rotateTo);
        }
    }

    public void stopFiring() {
        fireLaser.stopFiring();
    }
}
