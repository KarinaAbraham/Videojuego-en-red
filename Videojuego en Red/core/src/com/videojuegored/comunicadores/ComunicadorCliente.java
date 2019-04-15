package com.videojuegored.comunicadores;


import com.videojuegored.clienteservidor.Cliente;

import com.videojuegored.mecanicas.MyInput;
public class ComunicadorCliente extends Comunicador{
	private Cliente cliente;
	public ComunicadorCliente(Cliente cliente){
		this.cliente	=	cliente;
	}
 
	public void enviar(MyInput input){ 
		cliente.agregar(input); 
		}
	
	
}
