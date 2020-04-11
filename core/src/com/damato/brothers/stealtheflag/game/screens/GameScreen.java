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
import com.damato.brothers.stealtheflag.game.sprites.Player;
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

	private Player player;

	private SpriteBatch spriteBatch;

	public GameScreen(GameMain gameMain) {
		gameMain = this.gameMain;
		spriteBatch = new SpriteBatch();

		gamecam = new OrthographicCamera();
		gamecam.setToOrtho(false);
		gamePort = new FitViewport(gameMain.V_WIDTH / gameMain.PPM,
				gameMain.V_HEIGHT / gameMain.PPM, gamecam);
		gamecam.position.set(gamePort.getWorldWidth(),
				gamePort.getWorldHeight(), 0);

		wmpm = new WorldMapManagement(1);
		map = wmpm.getWorldMap();
		renderer = new OrthogonalTiledMapRenderer(map, 1 / gameMain.PPM);

		world = new World(new Vector2(0, -10), false);
		b2dr = new Box2DDebugRenderer();
		creator = new B2dWorldCreator(this);

		world.setContactListener(new WorldContactListener());

		player = new Player(this);
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
		player.dispose();
	}

	public TiledMap getMap () {
		return map;
	}
	public World getWorld () {
		return world;
	}

	public void update(float dt){
		handleInput(dt);
		//takes 1 step in the physics simulation(60 times per second)
		world.step(1 / 60f, 6, 2);
		player.update(dt);
		gamecam.update();
		renderer.setView(gamecam);
	}
	public void handleInput(float dt){

		if (!player.isDead()){

			if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
				player.fire();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
					Gdx.input.isKeyJustPressed(Input.Keys.UP)){
				player.jump(dt);
			}else if (Gdx.input.isKeyPressed(Input.Keys.UP) && player.getState() == Player.State.JUMPING) {
				player.jump(dt);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				player.b2body.setTransform(player.b2body.getPosition().x + 0.05f + dt,
						player.b2body.getPosition().y,0);
				player.setWalkDirection(true,false);
				System.out.println("walkingR: " + player.getDirectionR());
			}else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
				player.b2body.setTransform(player.b2body.getPosition().x -(0.05f + dt),
						player.b2body.getPosition().y,0);
				player.setWalkDirection(false,true);
				System.out.println("walkingR: " + player.getDirectionR());
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)){
			gamecam.zoom +=1/GameMain.PPM;
		}else if (Gdx.input.isKeyPressed(Input.Keys.E)){
			gamecam.zoom -=1/GameMain.PPM;
		}
		gamecam.position.x = player.b2body.getPosition().x;
		gamecam.position.y = player.b2body.getPosition().y;
	}
}
