package com.damato.brothers.stealtheflag.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damato.brothers.stealtheflag.game.GameMain;
import com.damato.brothers.stealtheflag.game.sprites.Player;

public class Hud {
	
	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private ShapeRenderer renderer;
	private BitmapFont font;
	private GlyphLayout layout;
	
	public Hud(SpriteBatch batch, ShapeRenderer renderer, BitmapFont font, GlyphLayout layout) {
		this.camera = new OrthographicCamera();
		this.viewport = new FitViewport(GameMain.V_WIDTH, GameMain.V_HEIGHT, camera);
		this.batch = batch;
		this.renderer = renderer;
		this.font = font;
		this.layout = layout;
	}
	
	public void resize(int width, int height) {
		this.viewport.update(width, height, true);
	}
	
	public void drawHudPlayer(Player player) {
		
		if ( renderer != null && player != null ) {
			
			float y = GameMain.V_HEIGHT - 100;
			float x = 20;
			float width = 200 * (player.getLife() / 100f);
	    	//Gdx.app.log("PLAYER", "life: " + life);
	    	float height = 20;
			renderer.setProjectionMatrix(camera.combined);
	    	renderer.begin(ShapeType.Line);
	    	renderer.setColor(Color.WHITE);
	    	renderer.rect(x, y, 200, height);
	    	renderer.end();
	    	
	    	renderer.begin(ShapeType.Filled);
	    	renderer.setColor(Color.RED);
	    	renderer.rect(x, y, width, height);
	    	renderer.end();
	    	
	    	String bullets = "[RED][ [WHITE]" + player.getCountBullet() + "[RED]/[WHITE]" + player.getCountRechargeBullet() + "[RED] ]";
	    	
	    	layout.setText(font, bullets);
	    	
	    	batch.setProjectionMatrix(camera.combined);
	    	batch.begin();
	    	font.draw(batch, bullets, x + layout.width, y - (layout.height * 2) );
	    	batch.end();
		}
		
	}

}
