package com.videojuegored.items;

import java.io.Serializable;

public class Arma implements Serializable{
	
	private String nombreArma;
	private int balasActuales;
	private String texturaBala;
	private boolean balasInfinitas = false;
	private float daño;
	
	public Arma(String nombreArma, int balasTotales, String texturaBala, float daño){
		this.nombreArma    = nombreArma;
		this.balasActuales = balasTotales;
		this.daño		   = daño;
		this.texturaBala   = texturaBala;
 				
	}
	
	public Arma(String nombreArma, String texturaBala, float daño){ 
		this.nombreArma    = nombreArma;
		this.balasInfinitas = true;
		this.daño		   = daño;

		this.texturaBala	=	texturaBala;
	

	}
	

	
	public void perderBala(int balasPerdidas){ 
		
		
		if(balasActuales>0 && !balasInfinitas){
		
			balasActuales-=balasPerdidas;
		
			if(balasActuales<0){
			
				balasActuales = 0;
			}
		}
			
	}

	public void setBala(int nuevasBalas){ 
		balasActuales+=nuevasBalas;
			
	}
	public Arma clonar(){
		Arma arma;
		if(balasInfinitas){

			arma = new Arma(this.nombreArma, this.texturaBala, this.daño);
				
		}else{
			arma = new Arma(this.nombreArma, this.balasActuales, this.texturaBala, this.daño);

		}
		return arma;

	}

	public int getBalasActuales(){ return balasActuales;}	
	public String getTexturaBala(){ return texturaBala; }	
	public float getDaño(){ return daño;}	
	public String getNombreArma(){ return nombreArma; }	
	public boolean tieneBalasInfintias(){ return balasInfinitas; } 	
	
		
	
	

}
