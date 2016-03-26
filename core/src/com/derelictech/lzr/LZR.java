package com.derelictech.lzr;

import com.badlogic.gdx.Game;
import com.derelictech.lzr.screens.WelcomeScreen;
import com.derelictech.lzr.util.Assets;

public class LZR extends Game {

	@Override
	public void create() {
		Assets.inst.init();
		setScreen(new WelcomeScreen(this));
	}
}
