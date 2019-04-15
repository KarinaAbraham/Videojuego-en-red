package com.videojuegored.clienteservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.videojuegored.game.MyGame;
import com.videojuegored.red.WorldState;

public class HiloClienteRecibir  extends Thread{
	private DatagramPacket paqueteDatagramas;
	private DatagramSocket socket;
	private MyGame myGame;
	private boolean juegoSigue;

	public HiloClienteRecibir (DatagramPacket paqueteDatagramas, DatagramSocket socket, MyGame myGame, boolean juegoSigue){
		this.paqueteDatagramas = paqueteDatagramas;
		this.socket	= socket;
		this.myGame	= myGame;
		this.juegoSigue = juegoSigue;
		
	}
	public void run(){
    	try {
			this.socket.setSoTimeout(30000); 
		} catch (SocketException e1) {
			myGame.setConexionCaida(true);
		}
		while(juegoSigue){
				byte[] bytes = new byte[8000];
			    this.paqueteDatagramas.setData(bytes, 0, bytes.length); 
			
			   
			      try {
		
  		    	 if(!this.socket.isClosed()){
				    	this.socket.receive(this.paqueteDatagramas); 
						WorldState worldState = (WorldState) BytesUtils.toObject(this.paqueteDatagramas.getData()); 
						myGame.getWorldState().add(worldState);  
						}	
 			    }  catch (SocketTimeoutException e){
					
					myGame.setConexionCaida(true); 
					
				
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
						
					
			}
			
	}
	
}
	



