package com.videojuegored.red;

import java.io.Serializable;

import com.videojuegored.items.Arma;
import com.videojuegored.personajes.Persona.Estado;

public class JugadorRed implements Serializable {

	   public Estado estado;
	   public Estado estadoAnterior; 
	   public float vidaActual;
	   public boolean miraDerecha;
	   public float tiempoEstado;
	   public boolean puedeDisparar;
	   public float tiempoDisparo;
	   public Arma armaActual;
	   public float posicionX;
	   public float posicionY;
	
}
