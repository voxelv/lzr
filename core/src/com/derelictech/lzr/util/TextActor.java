package com.derelictech.lzr.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Tim on 3/22/2016.
 */


public class TextActor extends Actor {

    public String text;
    public BitmapFont font;
    public FreeTypeFontGenerator generator;
    public FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public TextActor(String text, int size) {
        init(text, size);
    }

    private void init(String text, int size){
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Exo-Bold.otf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        font = generator.generateFont(parameter);
        this.text = text;
        generator.dispose();
    }

    private void init(String text) {
        init(text, 12);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, this.text, getX(), getY());
    }
}
