package com.videojuegored.red;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;
import com.videojuegored.personajes.Persona.Estado;

public class SoldadoEnemigoRed implements Serializable{

   public int id;		
   public Estado estado;
   public Estado estadoAnterior;
   public float vidaActual;
   public boolean miraDerecha;
   public float tiempoEstado;
   public boolean puedeDisparar;
   public float tiempoDisparo;
   public Vector2 velocidad;

   
}
