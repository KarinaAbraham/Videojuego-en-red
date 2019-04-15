package com.videojuegored.items;

import java.io.Serializable;

public class Arma implements Serializable{
	
	private String nombreArma;
	private int balasActuales;
	private String texturaBala;
	private boolean balasInfinitas = false;
	private float da�o;
	
	public Arma(String nombreArma, int balasTotales, String texturaBala, float da�o){
		this.nombreArma    = nombreArma;
		this.balasActuales = balasTotales;
		this.da�o		   = da�o;
		this.texturaBala   = texturaBala;
 				
	}
	
	public Arma(String nombreArma, String texturaBala, float da�o){ 
		this.nombreArma    = nombreArma;
		this.balasInfinitas = true;
		this.da�o		   = da�o;

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

			arma = new Arma(this.nombreArma, this.texturaBala, this.da�o);
				
		}else{
			arma = new Arma(this.nombreArma, this.balasActuales, this.texturaBala, this.da�o);

		}
		return arma;

	}

	public int getBalasActuales(){ return balasActuales;}	
	public String getTexturaBala(){ return texturaBala; }	
	public float getDa�o(){ return da�o;}	
	public String getNombreArma(){ return nombreArma; }	
	public boolean tieneBalasInfintias(){ return balasInfinitas; } 	
	
		
	
	

}
