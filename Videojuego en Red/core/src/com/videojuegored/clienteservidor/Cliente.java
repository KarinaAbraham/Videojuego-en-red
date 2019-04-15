package com.videojuegored.clienteservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.LinkedBlockingQueue;

import com.videojuegored.game.MyGame;
import com.videojuegored.mecanicas.MyInput;

public class Cliente {
	private DatagramPacket enviarDatagrama, recibirDatagrama;
	private static DatagramSocket socket;
	private MyGame myGame;
	private static boolean juegoSigue = true;
	private static boolean hilosIniciados = false;
	private LinkedBlockingQueue<MyInput> entrada = new LinkedBlockingQueue<MyInput>(); 

	public Cliente(InetAddress ip, int port, MyGame myGame){ 

		byte[] array = new byte[1024];
		this.myGame = myGame;
		enviarDatagrama = new DatagramPacket(array, array.length); 
		enviarDatagrama.setAddress(ip);
		enviarDatagrama.setPort(port);
		recibirDatagrama = new DatagramPacket(array, array.length);
		recibirDatagrama.setAddress(ip);
		recibirDatagrama.setPort(port);

		try {
			if(socket==null){

				 socket = new DatagramSocket(null);
				 socket.setReuseAddress(true); 
				 socket.setSoTimeout(30000);
			
			}
		}catch (SocketException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void iniciar(){
		if(!hilosIniciados){
		try {
			byte[] bytes = new byte [1024];

			bytes =	BytesUtils.toByteArray("Hola");
			
			this.enviarDatagrama.setData(bytes, 0, bytes.length);
			socket.send(this.enviarDatagrama); 
			socket.setSoTimeout(10000);  
			
			socket.receive(recibirDatagrama); 

			if((BytesUtils.toObject(recibirDatagrama.getData())).equals("Que onda")){ 
				myGame.setConectado(true);
			
				HiloClienteEnviar hiloClienteEnviar = new  HiloClienteEnviar(enviarDatagrama, socket, entrada, juegoSigue);	
				hiloClienteEnviar.start();
				HiloClienteRecibir hiloClienteRecibir = new HiloClienteRecibir(recibirDatagrama, socket,  myGame, juegoSigue);
				hiloClienteRecibir.start();
				hilosIniciados = true;
			
			}
		} catch (SocketTimeoutException e){
			e.printStackTrace();
			myGame.setConexionCaida(true); 
			
		}catch (IOException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	}
	public void agregar(MyInput entrada){
		this.entrada.add(entrada); 
		
	}
	
	public void cerrar(){
		juegoSigue = false;
		hilosIniciados = false;
		socket.close();
	}
	public boolean hilosIniciados(){return hilosIniciados;}

	

}
