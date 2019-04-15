package com.videojuegored.niveles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;

public class Pared extends ElementoEscenario {
	public Pared(World world, TiledMap tiledMap, Rectangle objeto){
		super(world, tiledMap, objeto);
		fixture.setUserData("Pared");
		setCategoryFilter(OtrasConstantes.BIT_PARED);

	}
	
}
 