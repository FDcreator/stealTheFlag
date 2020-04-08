package com.damato.brothers.stealtheflag.game;

import com.badlogic.gdx.Game;
import com.damato.brothers.stealtheflag.game.screens.MenuScreen;

public class GameMain extends Game {
	
	@Override
	public void create() {	
		
		setScreen(new MenuScreen());
	}
}
