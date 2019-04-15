package com.videojuegored.items;

public class Item {

	private String nombreItem;
	private int cantidadCuracion;
	
	public Item(String nombreItem, int cantidadCuracion){
		this.cantidadCuracion=cantidadCuracion;
		this.nombreItem = nombreItem;
	}
	
	public Item clonar(){
		
		Item item = new Item(this.nombreItem, this.cantidadCuracion);
		return item;
	}

	public String getNombreItem(){return nombreItem;}
	public int getCantidadCuracion(){return cantidadCuracion;}

}
