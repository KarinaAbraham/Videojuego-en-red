package com.videojuegored.niveles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;

public class PuertaFinal extends ElementoEscenario{
	public PuertaFinal(World world, TiledMap tiledMap, Rectangle objeto){
		super(world, tiledMap, objeto);
		fixture.setUserData(this);
		setCategoryFilter(OtrasConstantes.BIT_PUERTA_FINAL);

	}
}
 