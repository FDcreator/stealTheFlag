package com.damato.brothers.stealtheflag.game;

import com.badlogic.gdx.Game;
import com.damato.brothers.stealtheflag.game.screens.MenuScreen;

public class GameMain extends Game {

	//covert meter in centimeter
	public static final float PPM = 100;
	public static final int V_WIDTH = 512;
	public static final int V_HEIGHT = 256;

	//for fixtures data, help in colision world
	public static final short PLAYER_BIT = 1;
	public static final short GROUND_BIT = 2;

	@Override
	public void create() {	
		
		setScreen(new MenuScreen());
	}
}
