package com.derelictech.lzr.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;
import com.derelictech.lzr.util.Const;
import com.derelictech.lzr.units.TriangleBeamWeapon;
import com.derelictech.lzr.util.TextActor;


/**
 * Created by Tim on 3/22/2016.
 */
public class WelcomeScreen extends AbstractGameScreen{

    private Viewport viewport;
    private Camera camera;
    private Stage stage;

    TextActor welcomeText;
    TextActor lzrText;

    public WelcomeScreen(Game game) {
        super(game);

        camera = new OrthographicCamera(Const.WELCOME_VIEWPORT_WIDTH, Const.WELCOME_VIEWPORT_HEIGHT);
        viewport = new ExtendViewport(Const.WELCOME_VIEWPORT_WIDTH, Const.WELCOME_VIEWPORT_HEIGHT, camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        welcomeText = new TextActor(Const.WELCOME_TEXT, 50);
        welcomeText.setPosition(100, Gdx.graphics.getHeight() - welcomeText.getHeight());
        welcomeText.font.setColor(1, 0, 0, 1);
        stage.addActor(welcomeText);

        lzrText = new TextActor(Const.GAME_NAME + ".", 100);
        lzrText.setPosition(Gdx.graphics.getWidth() - lzrText.getWidth(), welcomeText.getY() - 100);
        lzrText.font.setColor(0, 0.8f, 0.8f, 1);
        stage.addActor(lzrText);

        TriangleBeamWeapon tri = new TriangleBeamWeapon("triangle");
        tri.setPosition(100, 100);
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
        viewport.update(width, height);
        camera.update();
    }
}
