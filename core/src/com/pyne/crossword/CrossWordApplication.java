package com.pyne.crossword;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.pyne.crossword.board.Board;
import com.pyne.crossword.board.themes.LocalThemeFactory;
import com.pyne.crossword.fonts.SmartFontGenerator;

public class CrossWordApplication extends ApplicationAdapter {

    private SpriteBatch batch;
    private Board board;
    private OrthographicCamera camera;
    public SmartFontGenerator fontGen;

    public static CrossWordApplication instance;

    @Override
    public void create() {
        instance = this;
        fontGen = new SmartFontGenerator();

        batch = new SpriteBatch();

        board = new Board.Builder()
                .withSize(new Vector2(getSmallestGraphicsDimension(), getSmallestGraphicsDimension()))
                .withCols(20)
                .withRows(20)
                .withNumberOfWords(100)
                .withThemeFactory(new LocalThemeFactory())
                .withThemes("Silly")
                .withDifficulty(3)
                .buildBoard();

        Gdx.input.setInputProcessor(new GestureDetector(board));

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f
                , -camera.viewportHeight / 2f + board.getSize().y
                , 0);
        camera.update();

    }

    private int getSmallestGraphicsDimension() {
        if (Gdx.graphics.getWidth() < Gdx.graphics.getHeight())
            return Gdx.graphics.getWidth();
        return Gdx.graphics.getHeight();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        board.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        board.dispose();
    }
}
