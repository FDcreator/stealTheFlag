package com.damato.brothers.stealtheflag.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.utils.GameUtils;

public class MenuScreen implements Screen {

	private GameMain game;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private SpriteBatch batch;
	private Stage stage;
	private Skin skin;
	
	public MenuScreen(GameMain game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		
		this.camera = new OrthographicCamera();
		this.viewport = new FitViewport(GameMain.V_WIDTH, GameMain.V_HEIGHT);
		
		
	}

	@Override
	public void render(float delta) {
		GameUtils.clearScreen(Color.SKY);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		
		
	}

	
}
