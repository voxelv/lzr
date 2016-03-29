package com.derelictech.lzr.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;
import com.derelictech.lzr.util.*;
import com.derelictech.lzr.units.TriangleBeamWeapon;


/**
 * Created by Tim on 3/22/2016.
 */
public class WelcomeScreen extends AbstractGameScreen{

    public static Viewport viewport;
    private Camera camera;
    private Stage stage;

    TextActor welcomeText;
    TextActor lzrText;
    LZRButton play_btn;
    LZRButton quit_btn;

    TriangleBeamWeapon tri;

    public WelcomeScreen(Game game) {
        super(game);

        camera = new OrthographicCamera();
        viewport = new FitViewport(Const.WELCOME_VIEWPORT_WIDTH, Const.WELCOME_VIEWPORT_HEIGHT, camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        welcomeText = new TextActor(Const.WELCOME_TEXT, 80);
        welcomeText.setPosition(100, viewport.getScreenHeight() - welcomeText.getHeight() + 50);
        welcomeText.font.setColor(1, 0, 0, 1);
        stage.addActor(welcomeText);

        lzrText = new TextActor(Const.GAME_NAME + ".", 150);
        lzrText.setPosition(welcomeText.getX(), welcomeText.getY() - 100);
        lzrText.font.setColor(0, 0.8f, 0.8f, 1);
        stage.addActor(lzrText);

        play_btn = new LZRButton("play_btn_up","play_btn_dn");
        play_btn.setPosition(lzrText.getX(), lzrText.getY() - 250);
        play_btn.setDestroyAction(new Action() {
            float countDown = 1.5f;
            @Override
            public boolean act(float delta) {
                countDown -= delta;
                if(countDown <= 0) {
                    // TODO GAME TRANSITION
                    System.out.println("PLAY GAME");
                    return true;
                }
                else return false;
            }
        });
        stage.addActor(play_btn);

        quit_btn = new LZRButton("quit_btn_up", "quit_btn_dn");
        quit_btn.setPosition(play_btn.getX(), play_btn.getY() - 70);
        quit_btn.setDestroyAction(new Action() {
            float countDown = 1.5f;
            @Override
            public boolean act(float delta) {
                countDown -= delta;
                if(countDown <= 0) {
                    Gdx.app.exit();
                    return true;
                }
                else return false;
            }
        });
        stage.addActor(quit_btn);

        tri = new TriangleBeamWeapon();
        tri.setPosition(play_btn.getX() + play_btn.getWidth() + 200, play_btn.getY() - tri.getOriginY() - 5);
        stage.addActor(tri);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }
}
