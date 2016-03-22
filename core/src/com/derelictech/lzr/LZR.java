package com.derelictech.lzr;

import com.badlogic.gdx.Game;
import com.derelictech.lzr.screens.WelcomeScreen;

public class LZR extends Game {

	@Override
	public void create() {
		setScreen(new WelcomeScreen(this));
	}
}
