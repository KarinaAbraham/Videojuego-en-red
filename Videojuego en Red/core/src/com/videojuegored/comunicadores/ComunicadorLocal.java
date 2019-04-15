package com.videojuegored.comunicadores;

import java.util.Queue;

import com.videojuegored.mecanicas.MyInput;

public class ComunicadorLocal extends Comunicador {
	private	Queue<MyInput> entrada;
	public ComunicadorLocal(Queue<MyInput> entrada){
		this.entrada = entrada;
	}
	
	@Override
	public void enviar(MyInput input){
		entrada.add(input); 
	}
	
	
}
 