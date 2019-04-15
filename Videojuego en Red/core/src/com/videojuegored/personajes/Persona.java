package com.videojuegored.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.videojuegored.global.OtrasConstantes;
import com.videojuegored.items.Arma;
import com.videojuegored.items.Bala;
import com.videojuegored.recursos.Recursos;

public abstract class Persona  extends Sprite {

	protected float vidaMaxima, vidaActual; 
	protected Arma armaDefecto, armaActual;
	protected Animation animationCorrer	 = null; 
	protected Animation animationQuieto	=	null;
	protected World world; 
	protected Body body;
	protected Fixture fixture; 
	public enum Estado	{SALTAR, CORRER, QUIETO};
	protected Estado estado; 
	protected Estado estadoAnterior	=	 Estado.QUIETO;  
	protected boolean miraDerecha	  =	 true; 
	protected float tiempoEstado = 0; 
	protected boolean puedeDisparar = true; 
	protected float tiempoDisparo =0; 
	protected TextureAtlas atlas;
	protected boolean jugador; 

	
	public Persona(float vida, Arma armaInicial, World world, TextureAtlas atlas, String nombreIAtlas, boolean jugador){
		super(atlas.findRegion(nombreIAtlas)); 
		this.jugador = jugador;
		this.world	=	world;
		this.vidaMaxima		=	 vida;
		this.vidaActual 	=	 vida;
		this.armaDefecto	=	 armaInicial;
		this.armaActual		=	 armaDefecto;
		
		setBounds(0,0, 100  / OtrasConstantes.PPM, 100  / OtrasConstantes.PPM); 

		
	}
	
		
	
	public void setAnimacionCorrer(int xInicial, int yInicial, int width, int height, int numeroFrames){

		Array<TextureRegion> frames = new Array<TextureRegion>(); 
		for(int i = 0; i<numeroFrames; i++){
			frames.add(new TextureRegion(getTexture(), xInicial + i* width, yInicial, width, height)); 

		}
	
		this.animationCorrer = new Animation(0.2f, frames); 
	
		frames.clear(); 

	}
	

	public void setAnimacionQuieto(int xInicial, int yInicial, int width, int height, int numeroFrames){		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 0; i<numeroFrames; i++){
				frames.add(new TextureRegion(getTexture(), xInicial + i* width, yInicial, width, height));
		
		}
		this.animationQuieto = new Animation(0.2f, frames);
		frames.clear();
		 
		setRegion(animationQuieto.getKeyFrame(0, true)); 

	}
	
	public void recibirDisparo(Bala bala){
		vidaActual -= bala.getDaño(); 
	
	}
	public Bala disparar(){
		Bala bala;
		if(puedeDisparar){ 
			puedeDisparar=false; 
			
			Texture texturaBala = new Texture(Recursos.TEXTURA_BALA);  
			bala = new Bala(texturaBala, world, jugador, this.armaActual.getDaño());
			
			if(miraDerecha){ 

				bala.setBody((int)(body.getPosition().x * OtrasConstantes.PPM+ 50),	
						(int)( body.getPosition().y * OtrasConstantes.PPM+ 20),
						20,20);  

				bala.getBody().applyLinearImpulse(new Vector2(30f,0f),	bala.getBody().getWorldCenter(),true); 

			

			}else{ 
				bala.setBody((int)(body.getPosition().x * OtrasConstantes.PPM),	
						(int)( body.getPosition().y * OtrasConstantes.PPM+ 20),
						20,20); 
			
				bala.getBody().applyLinearImpulse( new Vector2(-30f,0f),bala.getBody().getWorldCenter(),true); 

			}

			if(armaActual.getBalasActuales()!=0 && !armaActual.tieneBalasInfintias()){
				armaActual.perderBala(1); 

				if(armaActual.getBalasActuales()<=0){ 

					armaActual = armaDefecto; 
				}
			}	
		}else{
			bala=null;
		   }
		
		return bala;

	}

	protected abstract TextureRegion getFrames(float dt);

	public void setEstado(Estado estado){ 
		this.estado	= estado;

	}	
	
	public void setPuedeDisparar(boolean puedeDisparar){
		this.puedeDisparar	=	puedeDisparar;
	}
	public void setTiempoEstado(float tiempoEstado){
		this.tiempoEstado = tiempoEstado;
	}
	
	public void setTiempoDisparo(float tiempoDisparo){
		this.tiempoDisparo = tiempoDisparo;
	}
	public void setEstadoAnterior(Estado estado){
		this.estadoAnterior = estado;
	}
	
	public void setVidaActual(float vidaActual){
		this.vidaActual = vidaActual;
	}
	
	public void miraDerecha(boolean derecha){ 

		miraDerecha = derecha;
	}
	public Estado getEstado(){return estado;}	
	public Body getBody (){ return body;}
	public float getVidaMaxima(){ return vidaMaxima;}	
	public float getPorcentajeVidaActual(){ return (vidaActual * 100  / vidaMaxima);}
	public Arma getArmaActual() {return armaActual;}	
	public Estado getEstadoAnterior() {return estadoAnterior;}	
	public boolean getMiraDerecha(){return miraDerecha;}
	public float getTiempoEstado(){return tiempoEstado;}
	public float getTiempoDisparo(){return tiempoDisparo;}
	public boolean getPuedeDisparar(){return puedeDisparar;}
	public float  getVidaActual(){return vidaActual;}
	
}
