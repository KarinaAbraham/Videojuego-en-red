package com.videojuegored.clienteservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import com.videojuegored.game.MyGame;

public class Servidor {

	private DatagramPacket enviarDatagrama, recibirDatagrama;
	private static DatagramSocket socket;
	private static boolean juegoSigue = true;
	private HiloServidorRecibir hiloServidorRecibir;
	private HiloServidorEnviar hiloServidorEnviar;
	private static boolean hilosIniciados = false;
	
	public Servidor(int port,  MyGame myGame){
		try {
			
			byte[] array = new byte[1024];
			enviarDatagrama = new DatagramPacket(array, array.length); 
			recibirDatagrama = new DatagramPacket(array, array.length);
			 if(socket==null){
				 socket = new DatagramSocket(null);
				 socket.setReuseAddress(true);
				 socket.bind(new InetSocketAddress(port)); 

			 }
			 hiloServidorEnviar = new  HiloServidorEnviar(enviarDatagrama, recibirDatagrama, socket, myGame.getWorldState(), juegoSigue);	
			 hiloServidorRecibir = new  HiloServidorRecibir(recibirDatagrama, socket, myGame, juegoSigue);	

		} catch (SocketException e) {
			e.printStackTrace();
		} 
		

				
	}
	
	public boolean seContectó(){ 
		if(recibirDatagrama.getAddress()!=null){
			return true;
		}else{
			return false;
		}
		
	}
	public void cerrar(){
		socket.close();
		juegoSigue = false;
		hilosIniciados = false;
		
	}
	
	public void iniciar(){
		try { 		
			if(!hilosIniciados){
			socket.receive(this.recibirDatagrama); //el socket espera recibir algo 
			if((BytesUtils.toObject(this.recibirDatagrama.getData()).equals("Hola"))){ // si recibe un hola

						byte[] bytes = new byte [1024];
						bytes = BytesUtils.toByteArray("Que onda"); // guarda en un array de bytes el que onda que va a mandar para entablar la comunicacion
				
						this.enviarDatagrama.setPort(this.recibirDatagrama.getPort()); // le asigna a enviar el mismo que puerto que el datagrama de recibir
						this.enviarDatagrama.setData(bytes); // le asigna el array de btes al datagrama de enviar
						this.enviarDatagrama.setLength(bytes.length);
						this.enviarDatagrama.setAddress(this.recibirDatagrama.getAddress()); // le asigna la direccion
		
						socket.send(this.enviarDatagrama); // envia el paquete de datagramas (con el que onda en un array de Bytes)
						
						//inicia los hilos
						hiloServidorEnviar.start(); 
						hiloServidorRecibir.start();
						hilosIniciados = true;
			}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hilosIniciados(){return hilosIniciados;}
	

}
