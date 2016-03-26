package com.derelictech.lzr.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.derelictech.lzr.LZR;

public class DesktopLauncher {

	private static boolean repackTextures = true;

	public static void main (String[] arg) {

        if(repackTextures) {
            TexturePacker.process(".", "../packs", "pack");
        }

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new LZR(), config);
	}
}
