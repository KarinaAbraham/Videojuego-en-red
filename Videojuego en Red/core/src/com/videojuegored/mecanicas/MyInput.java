package com.videojuegored.mecanicas;

import java.io.Serializable;

public class MyInput implements Serializable {

	public int numeroJugador; 
	public enum Movimiento{IZQUIERDA, DERECHA, ARRIBA, QUIETO, DISPARAR}; 
	public Movimiento movimiento = Movimiento.QUIETO;
		
}
