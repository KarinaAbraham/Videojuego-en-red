package com.videojuegored.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.videojuegored.game.MyGame;
import com.videojuegored.global.Configuracion;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title 	 	 = Configuracion.TITULO;
		config.width 	 	 = Configuracion.ANCHO; 
		config.height 	 	 = Configuracion.ALTO;
		config.resizable 	 = Configuracion.REDIMENSIONABLE;
		config.backgroundFPS = Configuracion.BACKGROUNDFPS;
		config.foregroundFPS = Configuracion.FOREGROUNDFPS;		
		
		new LwjglApplication(new MyGame(), config); 
	}
}
