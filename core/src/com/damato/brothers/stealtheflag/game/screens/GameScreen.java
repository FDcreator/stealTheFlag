package com.damato.brothers.stealtheflag.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.utils.GameUtils;
import com.damato.brothers.stealtheflag.game.world.B2dWorldCreator;
import com.damato.brothers.stealtheflag.game.world.WorldContactListener;
import com.damato.brothers.stealtheflag.game.world.WorldMapManagement;

public class GameScreen implements Screen{

	private GameMain gameMain;
	//basic camera settings
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	//Tiled map variables
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private WorldMapManagement wmpm;
	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;
	private B2dWorldCreator creator;

	private SpriteBatch spriteBatch;

	public GameScreen(GameMain gameMain) {
		gameMain = this.gameMain;
		spriteBatch = new SpriteBatch();

		gamecam = new OrthographicCamera();
		gamecam.setToOrtho(false);
		gamePort = new FitViewport(gameMain.V_WIDTH / gameMain.PPM,
				gameMain.V_HEIGHT / gameMain.PPM);
		gamecam.position.set(gamePort.getWorldWidth(),
				gamePort.getWorldHeight(), 0);

		wmpm = new WorldMapManagement(1);
		map = wmpm.getWorldMap();
		renderer = new OrthogonalTiledMapRenderer(map, 1 / gameMain.PPM);

		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		creator = new B2dWorldCreator(this);

		world.setContactListener(new WorldContactListener());
	}
	@Override
	public void show () {
		// TODO Auto-generated method stub

	}

	@Override
	public void render ( float delta){
		// TODO Auto-generated method stub
		GameUtils.clearScreen();
		update(delta);
		renderer.render();
		b2dr.render(world,gamecam.combined);
		spriteBatch.setProjectionMatrix(gamecam.combined);

	}

	@Override
	public void resize ( int width, int height){
		// TODO Auto-generated method stub
		gamePort.update(width,height, true);
	}

	@Override
	public void pause () {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume () {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide () {
		dispose();
	}

	@Override
	public void dispose () {
		// TODO Auto-generated method stub
		spriteBatch.dispose();
		map.dispose();
		world.dispose();
		b2dr.dispose();
	}

	public TiledMap getMap () {
		return map;
	}
	public World getWorld () {
		return world;
	}

	public void update(float dt){
		handleInput(dt);
		gamecam.update();
		renderer.setView(gamecam);
	}
	public void handleInput(float dt){
		if (Gdx.input.isKeyPressed(Input.Keys.Q)){
			gamecam.zoom +=1/GameMain.PPM;
		}else if (Gdx.input.isKeyPressed(Input.Keys.E)){
			gamecam.zoom -=1/GameMain.PPM;
		}
	}
}
