package com.damato.brothers.stealtheflag.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.utils.AssetPath;
import com.damato.brothers.stealtheflag.game.utils.GameUtils;

public class MenuScreen implements Screen {

	private GameMain game;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private SpriteBatch batch;
	private Stage stage;
	private Skin skin;
	
	private boolean callGameScreen = false; 
	
	public MenuScreen(GameMain game) {
		this.game = game;
		
	}
	
	// *== Public Methods ==*
	
	@Override
	public void show() {
		
		this.camera = new OrthographicCamera();
		this.viewport = new FitViewport(GameMain.V_WIDTH, GameMain.V_HEIGHT, camera);
		
		this.batch = new SpriteBatch();
		this.stage = new Stage(this.viewport);
		this.skin = new Skin(Gdx.files.internal(AssetPath.SKIN_MENU_PATH));
		
		createTextField();
		createButton().addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				if ( button == Input.Buttons.LEFT ) {
					isCallGameScreen();
				}
				
				return true;
			}
		});
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		GameUtils.clearScreen(Color.SKY);
		updateStage(delta);

		if ( callGameScreen ) {
			game.setScreen(new GameScreen(game));
		}
			
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		this.viewport.update(width, height, true);
		
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
		stage.dispose();
		skin.dispose();
	}
	
	// *== Private Methods ==
	
	private TextField createTextField() {
		
		TextField textField = new TextField("aajhjh", skin, "user_name");
		textField.setSize(150, 50);
		textField.setPosition(( GameMain.V_WIDTH - textField.getWidth() ) / 2f, 200);
		textField.setMaxLength(10);
		
		stage.addActor(textField);
		
		return textField;
	}
	
	private TextButton createButton() {
		TextButton bnt = new TextButton("sign in", skin, "sign_in");
		
		bnt.setSize(50, 50);
		bnt.setPosition(( GameMain.V_WIDTH - bnt.getWidth() ) / 2f, 100);
		
		stage.addActor(bnt);
		
		return bnt;
	}
	
	private void updateStage(float delta) {
		stage.act(delta);
		stage.draw();
	}
	
	public void isCallGameScreen() {
		callGameScreen = true;
	}
	
}
