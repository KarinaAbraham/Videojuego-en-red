package com.videojuegored.mecanicas;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.videojuegored.comunicadores.Comunicador;
import com.videojuegored.mecanicas.MyInput.Movimiento;

public class MyInputListener extends InputAdapter{ // hereda de MyInputListener, así que siempre que se suelta o apreta una tecla, se llama a algunos de sus metodos
	
	private MyInput input = new MyInput(); // se crea un myInput para poder guardar los datos
	private Comunicador comunicador;
	public MyInputListener(Comunicador comunicador){ // se le pasa un comunicador (comunicadorLocal, en caso de servidor y singleplayer, comunicadorCliente para el cliente)
		this.comunicador	=	comunicador;
	}
	
	// Cada vez que se ingresa una tecla:
	@Override
	public boolean keyDown(int keycode){ // se pasa el numero de la tecla correspondiente
		input.numeroJugador = 0; // por defecto se dice que quien ingreso la tecla es el jugador 0 (en caso de que no, después se cambia el el hilo de recibir del servidor)
		switch(keycode){
		case Input.Keys.A:
		case Input.Keys.LEFT:
			input.movimiento = Movimiento.IZQUIERDA; // guarda el movimiento, dependiendo cual haya sido, en este caso izquierda 
			comunicador.enviar(input); // lo envia al comunicador
			return true;
	
		case Input.Keys.W:
		case Input.Keys.UP:
			input.movimiento = Movimiento.ARRIBA;
			comunicador.enviar(input);
			return true;
			
		case Input.Keys.D:
		case Input.Keys.RIGHT:
			input.movimiento = Movimiento.DERECHA;						
			comunicador.enviar(input);
			return true;
			
		case Input.Keys.SPACE:
			input.movimiento = Movimiento.DISPARAR;							
			comunicador.enviar(input);
			return true;
			
		default: 
			return false;
	
		
		}
	}
	// cada vez que se suelta una tecla
		@Override
		public boolean keyUp(int keycode) {
			input.numeroJugador = 0;
			switch(keycode){

			case Input.Keys.A:
			case Input.Keys.LEFT:
			case Input.Keys.W:
			case Input.Keys.UP:
			case Input.Keys.D:
			case Input.Keys.RIGHT:

				input.movimiento = Movimiento.QUIETO;					 // si se suelta una tecla de las anteriores, significa que debe estar quieto		
				comunicador.enviar(input); // se envia al comunicador
				return true;	
				
			default: 
				return false;
			
			}		
		}
}
