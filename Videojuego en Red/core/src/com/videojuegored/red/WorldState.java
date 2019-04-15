package com.videojuegored.red;

import java.util.ArrayList;

import com.videojuegored.personajes.Jugador;
import com.videojuegored.personajes.SoldadoEnemigo;

public class WorldState implements java.io.Serializable{
	
	private ArrayList<SoldadoEnemigoRed> soldadoEnemigoRed = new ArrayList<SoldadoEnemigoRed>();  
	private JugadorRed[] jugadorRed = new JugadorRed[2];
	
	public void setSoldadoEnemigo(ArrayList<SoldadoEnemigo> soldadoEnemigo){ 
		
		for(int i=0; i<soldadoEnemigo.size(); i++){
			
			SoldadoEnemigoRed soldadoEnemigoRed = new SoldadoEnemigoRed();
			soldadoEnemigoRed.id = soldadoEnemigo.get(i).getId();
			soldadoEnemigoRed.estado = soldadoEnemigo.get(i).getEstado();
			soldadoEnemigoRed.tiempoDisparo = soldadoEnemigo.get(i).getTiempoDisparo();
			soldadoEnemigoRed.tiempoEstado	= soldadoEnemigo.get(i).getTiempoEstado();
			soldadoEnemigoRed.vidaActual	= soldadoEnemigo.get(i).getVidaActual();
			soldadoEnemigoRed.estadoAnterior = soldadoEnemigo.get(i).getEstadoAnterior();
			soldadoEnemigoRed.miraDerecha	= soldadoEnemigo.get(i).getMiraDerecha();
			soldadoEnemigoRed.velocidad		= soldadoEnemigo.get(i).getVelocidad();		
			
			this.soldadoEnemigoRed.add(soldadoEnemigoRed);
			
		}
	}
	
	
	public void setJugadorRed(Jugador[] jugador){ 
		
		for(int i=0; i<jugador.length; i++){
			
			JugadorRed jugadorRed = new JugadorRed();
			jugadorRed.estado = jugador[i].getEstado();
			jugadorRed.tiempoDisparo = jugador[i].getTiempoDisparo();
			jugadorRed.tiempoEstado	= jugador[i].getTiempoEstado();
			jugadorRed.vidaActual	= jugador[i].getVidaActual();
			jugadorRed.estadoAnterior = jugador[i].getEstadoAnterior();
			jugadorRed.miraDerecha	= jugador[i].getMiraDerecha();
			jugadorRed.armaActual	= jugador[i].getArmaActual();
			jugadorRed.puedeDisparar = jugador[i].getPuedeDisparar();
			jugadorRed.posicionX = jugador[i].getBody().getPosition().x;
			jugadorRed.posicionY = jugador[i].getBody().getPosition().y;
			
			
			this.jugadorRed[i] = jugadorRed;
			
		}
	}



	
	public JugadorRed[] getJugadorRed(){return jugadorRed;}
	public ArrayList<SoldadoEnemigoRed> getSoldadoEnemigoRed(){return soldadoEnemigoRed;}


	
}
