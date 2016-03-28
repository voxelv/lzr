package com.derelictech.lzr.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        GlyphLayout g = font.draw(batch, text, 0, 0);
        batch.end();
        batch.dispose();

        this.setWidth(g.width);
        this.setHeight(g.height);

        generator.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, this.text, getX(), getY());
    }
}
