package com.videojuegored.clienteservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.LinkedBlockingQueue;

import com.videojuegored.mecanicas.MyInput;

public class HiloClienteEnviar extends Thread{
	private DatagramPacket paqueteDatagramas;
	private DatagramSocket socket;
	private LinkedBlockingQueue<MyInput> entrada;
	private boolean juegoSigue;
	
	public HiloClienteEnviar (DatagramPacket paqueteDatagramas, DatagramSocket socket, LinkedBlockingQueue<MyInput> entrada, boolean juegoSigue){
		this.paqueteDatagramas = paqueteDatagramas;
		this.socket	= socket;
		this.entrada =	 entrada;
		this.juegoSigue = juegoSigue;
	}
	
	@Override
	public void run(){
 
		while(juegoSigue){
 
			if(!this.socket.isClosed()){
					if(!this.entrada.isEmpty())
					{	
						MyInput entrada = (MyInput) this.entrada.poll(); 
						try {
							byte[] bytes = BytesUtils.toByteArray(entrada); 
							
							this.paqueteDatagramas.setData(bytes);
							this.paqueteDatagramas.setLength(bytes.length);
							this.socket.send(this.paqueteDatagramas); 
							
						} catch (IOException e) {
							e.printStackTrace();
							
						}
					}
				}
		}
	
	}
}
	


