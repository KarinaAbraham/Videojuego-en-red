package com.videojuegored.clienteservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.LinkedBlockingQueue;

import com.videojuegored.red.WorldState;

public class HiloServidorEnviar extends Thread{
	private DatagramPacket paqueteDatagramas, recibirDatagrama;
	private DatagramSocket socket;
	private LinkedBlockingQueue<WorldState> worldState;
	private boolean juegoSigue;
	
	public HiloServidorEnviar (DatagramPacket enviarDatagrama, DatagramPacket recibirDatagrama, DatagramSocket socket, LinkedBlockingQueue<WorldState> worldState, boolean juegoSigue){
		this.paqueteDatagramas = enviarDatagrama;
		this.socket	= socket;
		this.worldState = worldState;
		this.juegoSigue = juegoSigue;
		this.recibirDatagrama = recibirDatagrama;
	}
	
	@Override
	public void run(){
		this.paqueteDatagramas.setAddress(recibirDatagrama.getAddress());
		if(recibirDatagrama.getAddress()!=null){ 

		while(juegoSigue){ 
			try {

				if(!this.socket.isClosed()){

						if(!worldState.isEmpty()){

							byte[] bytes = BytesUtils.toByteArray(worldState.poll()); 
							this.paqueteDatagramas.setData(bytes, 0, bytes.length); 
							this.socket.send(this.paqueteDatagramas); 
		
						}
						
			
				}else{
					
					juegoSigue = false;
				}
					 
				} catch (IOException e) {
					e.printStackTrace();
					
				} catch (Exception e){
					e.printStackTrace();
					
				}
				
		
		}
		}
		
	}
			
	
}

