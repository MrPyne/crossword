package com.pyne.crossword;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.pyne.crossword.board.Board;
import com.pyne.crossword.board.themes.LocalThemeFactory;

public class CrossWordApplication extends ApplicationAdapter {

    public static CrossWordApplication instance;
    SpriteBatch batch;
    Texture img;
    Board board;

    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

        board = new Board.BoardBuilder()
            .withSize(new Vector2(500,500))
            .withNumberOfWords(10)
            .withThemeFactory(new LocalThemeFactory())
            .withThemes("Silly")
            .buildBoard();


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        board.draw(batch);
        batch.end();
    }
}
