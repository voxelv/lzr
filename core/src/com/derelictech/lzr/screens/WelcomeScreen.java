package com.derelictech.lzr.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;
import com.derelictech.lzr.units.Army;
import com.derelictech.lzr.units.TriangleTank;
import com.derelictech.lzr.util.*;


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

        TextActor instruct1 = new TextActor("1. Left Click a Triangle Tank to select it.", 20);
        TextActor instruct2 = new TextActor("2. Left Click to fire, shoot the buttons for practice.", 20);
        TextActor instruct3 = new TextActor("3. Right Click while selected to move it.", 20);
        TextActor instruct4 = new TextActor("4. Middle Click to deselect.", 20);
        instruct1.setPosition(lzrText.getX(), lzrText.getY() - instruct1.getHeight() - 125);
        instruct2.setPosition(lzrText.getX(), instruct1.getY() - instruct2.getHeight() - 5);
        instruct3.setPosition(lzrText.getX(), instruct2.getY() - instruct3.getHeight() - 5);
        instruct4.setPosition(lzrText.getX(), instruct3.getY() - instruct4.getHeight() - 5);
        instruct1.font.setColor(Color.GREEN);
        instruct2.font.setColor(Color.GREEN);
        instruct3.font.setColor(Color.GREEN);
        instruct4.font.setColor(Color.GREEN);
        stage.addActor(instruct1);
        stage.addActor(instruct2);
        stage.addActor(instruct3);
        stage.addActor(instruct4);

        play_btn = new LZRButton("play_btn_up","play_btn_dn");
        play_btn.setPosition(lzrText.getX(), lzrText.getY() - 300);
        play_btn.setDestroyAction(new Action() {
            float countDown = 0.75f;
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
            float countDown = 0.75f;
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

        TriangleTank tri1 = new TriangleTank(Army.PLAYER);
        tri1.setPosition(play_btn.getX() + play_btn.getWidth() + 200, play_btn.getY() - tri1.getOriginY() - 5);
        stage.addActor(tri1);

        TriangleTank tri2 = new TriangleTank(Army.PLAYER);
        tri2.setPosition(play_btn.getX() + play_btn.getWidth() + 200, play_btn.getY() - tri2.getOriginY() - 5);
        stage.addActor(tri2);
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
