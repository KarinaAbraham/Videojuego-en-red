package com.videojuegored.clienteservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import com.videojuegored.game.MyGame;
import com.videojuegored.mecanicas.MyInput;

public class HiloServidorRecibir extends Thread{

	private DatagramPacket paqueteDatagramas;
	private DatagramSocket socket;
	private MyGame myGame;
	private boolean juegoSigue;
	
	public HiloServidorRecibir (DatagramPacket paqueteDatagramas, DatagramSocket socket, MyGame myGame, boolean juegoSigue){
		this.paqueteDatagramas = paqueteDatagramas;
		this.socket	= socket;
		this.myGame = myGame;
		this.juegoSigue	= juegoSigue;
		byte[] bytes = new byte[1024];
	    this.paqueteDatagramas.setData(bytes, 0, bytes.length);				    
	 
	}
	
	@Override
	public void run(){
		
				while(juegoSigue){			
					try {
						
						if(!this.socket.isClosed()){
							this.socket.receive(this.paqueteDatagramas); 
	
							MyInput entrada = (MyInput) BytesUtils.toObject(this.paqueteDatagramas.getData()); 
							entrada.numeroJugador = 1; 
							this.myGame.getEntrada().add(entrada); 

						}
						
					} catch (SocketTimeoutException e){
						
						myGame.setConexionCaida(true); 
						
					} catch (IOException e) {
					
						e.printStackTrace();
					
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
	 			    
					
				}
					
			
	}
		
	
}
	


