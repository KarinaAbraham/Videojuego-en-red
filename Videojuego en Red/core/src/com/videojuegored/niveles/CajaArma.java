package com.videojuegored.niveles;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;
import com.videojuegored.items.Arma;

public class CajaArma extends ElementoInteractivo{
	private Arma arma;
	public CajaArma(int idCaja, World world, TiledMap tiledMap, Rectangle objeto, ArrayList<Arma> armasDisponibles){
		super(idCaja, world, tiledMap, objeto);
		
		fixture.setUserData(this);
		setCategoryFilter(OtrasConstantes.BIT_CAJA_ARMA); 

		arma 	=	armasDisponibles.get(random.nextInt(armasDisponibles.size())).clonar(); 
	}


	public Arma getArma(){return arma;}
	
}
 
