package com.videojuegored.niveles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;

public class Límite extends ElementoEscenario{
	public Límite(World world, TiledMap tiledMap, Rectangle objeto){
	
		super(world, tiledMap, objeto);
		fixture.setUserData(this);
		setFilter(OtrasConstantes.BIT_LIMITE_ENEMIGO, OtrasConstantes.BIT_ENEMIGO);	
		
	}
	
 
}
