package com.videojuegored.niveles;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.global.OtrasConstantes;
import com.videojuegored.items.Item;

public class CajaItem extends ElementoInteractivo{
	private Item item;
	public CajaItem(int idCaja, World world, TiledMap tiledMap, Rectangle objeto, ArrayList<Item> itemsDisponibles){
		super(idCaja, world, tiledMap, objeto);
		fixture.setUserData(this); 
		setCategoryFilter(OtrasConstantes.BIT_CAJA_POCION); 
		
		item	=	itemsDisponibles.get(random.nextInt(itemsDisponibles.size())).clonar();

	}
	
	public Item getItem(){return item;}

	
	
	
}
 