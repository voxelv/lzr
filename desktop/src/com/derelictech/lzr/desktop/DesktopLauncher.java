package com.derelictech.lzr.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.derelictech.lzr.LZR;

public class DesktopLauncher {

	private static boolean repackTextures = false;

	public static void main (String[] arg) {

        if(repackTextures) {
            TexturePacker.process(".", "packs", "pack");
        }

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 768;
		config.height = 480;
		config.x = 0;
		config.y = 0;
		new LwjglApplication(new LZR(), config);
	}
}
