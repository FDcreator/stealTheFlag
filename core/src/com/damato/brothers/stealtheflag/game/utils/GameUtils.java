package com.damato.brothers.stealtheflag.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class GameUtils {
	
	private GameUtils() {}
	
	public static void clearScreen(Color color) {
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public static void clearScreen() {
		clearScreen(Color.BLACK);
	}

}
