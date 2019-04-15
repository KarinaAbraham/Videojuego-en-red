package com.videojuegored.niveles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.videojuegored.items.Arma;
import com.videojuegored.items.Bala;
import com.videojuegored.items.Item;
import com.videojuegored.personajes.Jugador;
import com.videojuegored.personajes.SoldadoEnemigo;
import com.videojuegored.recursos.Recursos;

public class Nivel {
	private String nombreNivel;
	private ArrayList<SoldadoEnemigo> soldadoEnemigo = new ArrayList<SoldadoEnemigo>();
	private ArrayList<ElementoEscenario> elementoEscenario = new ArrayList<ElementoEscenario>();
	private ArrayList<Item> item = new ArrayList<Item>();
	private ArrayList<Bala> bala = new ArrayList<Bala>();
	private ArrayList<Arma> armaDisponible = new ArrayList<Arma>();
	private TiledMap tiledMap;
	private World world;
	private Jugador[] jugador = new Jugador[2];
	
	public Nivel (String nombreNivel, World world){ 
		this.nombreNivel = nombreNivel;
		this.world	= 	world;
	}
 

	public void setSoldadoEnemigo(String soldado){ 
		this.soldadoEnemigo.clear(); 
		MapLayer layer = this.tiledMap.getLayers().get(soldado); 

		TextureAtlas atlasPersona = new TextureAtlas(Recursos.ATLAS_PERSONA); 
		
		int i = 0;
		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){ 
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			
			SoldadoEnemigo soldadoEnemigo	=	new SoldadoEnemigo(i, 50, new Arma("Arma enemigo", Recursos.TEXTURA_BALA, 10),world, atlasPersona , "Enemigo");
			soldadoEnemigo.setAnimacionCorrer(0, 370, 76, 120, 6); 
			soldadoEnemigo.setAnimacionQuieto(0, 636, 90, 120, 1);  
			soldadoEnemigo.setBody(rect);
			this.soldadoEnemigo.add(soldadoEnemigo); 
			i++;
			
		}			
	}	

	
	
	public void setMapa(TiledMap tiledMap){ 
		this.tiledMap = tiledMap;	
		
	}
	

	
	public void setItem(Item item){
		this.item.add(item);
		
	}
	
	public void setArmaDisponible(Arma armaDisponible){
		this.armaDisponible.add(armaDisponible);
		
	}

	public void setPuertaFinal(String puertaFinal){
		MapLayer layer = this.tiledMap.getLayers().get(puertaFinal);

		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			this.elementoEscenario.add(new PuertaFinal(world, tiledMap, rect));
	
		}		
		

	}

	public void setLimitesEnemigos(String limite){
		MapLayer layer = this.tiledMap.getLayers().get(limite);
	
		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			this.elementoEscenario.add(new Límite(world, tiledMap, rect));
	
		}	
		
	}
	public void setSuelo(String suelo){
		MapLayer layer = this.tiledMap.getLayers().get(suelo);
	
		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			this.elementoEscenario.add(new Suelo(world, tiledMap, rect));
	
		}	
		
	}
	
	public void setPlataforma(String plataforma){
		MapLayer layer = this.tiledMap.getLayers().get(plataforma);
	
		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			this.elementoEscenario.add(new Plataforma(world, tiledMap, rect));
	
		}	
		
	}
	
	public void setPared(String pared){
		MapLayer layer = this.tiledMap.getLayers().get(pared);
	
		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			this.elementoEscenario.add(new Suelo(world, tiledMap, rect));
	
		}	
		
	}
	
	public void setTecho(String techo){
		MapLayer layer = this.tiledMap.getLayers().get(techo);
	
		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			this.elementoEscenario.add(new Suelo(world, tiledMap, rect));
	
		}	
		
	}
	
	public void setCajaItem(String item){
		MapLayer layer = this.tiledMap.getLayers().get(item);
		
		int i = 0;
		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			this.elementoEscenario.add(new CajaItem(i, world, tiledMap, rect, this.item));
			i++;
		}	
		
	}
	

	public void setCajaArma(String arma){
		MapLayer layer = this.tiledMap.getLayers().get(arma);
		int i = 0;
		for(MapObject mo : layer.getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) mo).getRectangle(); 		
			this.elementoEscenario.add(new CajaArma(i, world, tiledMap, rect, armaDisponible));
			i++;
	
		}	
		
	}
	
	public void setBala(Bala bala){ 
		if(bala!=null)
		this.bala.add(bala);
	}
	
	public void setJugadores(Jugador[] jugador){
		for(int i=0; i<jugador.length; i++){
				this.jugador[i] = jugador[i];
			
		}
	}
	

	public ArrayList<Bala> getBala(){return bala; }
	public ArrayList<Item> getItem(){return item; }
	public ArrayList<ElementoEscenario> getElementoEscenario(){return elementoEscenario; }
	public ArrayList<Arma> getArmaDisponible(){return armaDisponible; }
	public ArrayList<SoldadoEnemigo> getSoldadoEnemigo(){return soldadoEnemigo; }
	public TiledMap getMapa(){return tiledMap; }
	public String getNombreNivel(){return nombreNivel; }
	public Jugador[] getJugador(){return jugador;}
	
	
	
	
}
