package com.videojuegored.niveles;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;

public abstract class ElementoInteractivo extends ElementoEscenario{
	protected Random random 	=	new Random();
	protected int id;
	public ElementoInteractivo(int id, World world, TiledMap tiledMap, Rectangle objeto){
		super(world, tiledMap, objeto);
	
		this.id=id;
	}
	 
	public void interactua() { 
		setCategoryFilter(OtrasConstantes.BIT_NO_COLISIONABLE); 

		
		getCell(0).setTile(null); 
		getCell(-2).setTile(null);		
		getCell(-1).setTile(null);
		getCell(1).setTile(null);
		getCell(2).setTile(null); 		

	}
	public TiledMapTileLayer.Cell getCell(int i){
		TiledMapTileLayer layer	=	(TiledMapTileLayer) tiledMap.getLayers().get("Cajas"); 
		return layer.getCell((int) (body.getPosition().x * OtrasConstantes.PPM / 16) + i, (int) (body.getPosition().y * OtrasConstantes.PPM  / 16)); 
		  																																	
		
	}
	
	public int getId(){return id;}
}
