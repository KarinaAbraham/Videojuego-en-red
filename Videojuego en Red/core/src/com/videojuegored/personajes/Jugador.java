package com.videojuegored.personajes;



import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.videojuegored.global.OtrasConstantes;
import com.videojuegored.items.Arma;
import com.videojuegored.items.Item;

public class Jugador extends Persona{
	
		private Animation animationSaltar = null; 
		private boolean saltando	=	false;
		private boolean llegoFinal = false; 

		public Jugador(int vida, Arma armaInicial, World world, TextureAtlas atlas, String nombreIAtlas){
			super(vida, armaInicial, world, atlas, nombreIAtlas, true); 
			estado = Estado.QUIETO; 
		}
		
		
		public void setBody(int x, int y, int width, int height){ 
			
			 BodyDef bodyDef	= new BodyDef(); 
			 bodyDef.position.set(x  / OtrasConstantes.PPM, y / OtrasConstantes.PPM); 
			 bodyDef.type	= BodyDef.BodyType.DynamicBody; 
			
			 body = world.createBody(bodyDef);	  
			 
			 PolygonShape shape		= new PolygonShape();
			 shape.setAsBox(width / OtrasConstantes.PPM, height / OtrasConstantes.PPM);	  

			 FixtureDef fixtureDef  = new FixtureDef();
			 fixtureDef.shape  = shape; 
			 fixtureDef.filter.categoryBits = OtrasConstantes.BIT_JUGADOR; 
			 fixtureDef.filter.maskBits 	= OtrasConstantes.BIT_SUPERFICIE | OtrasConstantes.BIT_CAJA_ARMA | OtrasConstantes.BIT_CAJA_POCION | OtrasConstantes.BIT_BALA | OtrasConstantes.BIT_PUERTA_FINAL;
		
			 body.createFixture(fixtureDef).setUserData(this); 

			 shape.setAsBox(width / 2 / OtrasConstantes.PPM, height	/ 4 / OtrasConstantes.PPM, new Vector2(0, -.4f), 0);

			 fixtureDef.shape	=	shape; 
			 fixtureDef.isSensor= true; 
			 
			 body.createFixture(fixtureDef).setUserData(this); 
			 
			 
			 setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

			 
		}
		public void inicializar(){ 

			this.vidaActual = this.vidaMaxima;
			this.armaActual = this.armaDefecto;
			
		}
	

		public void setAnimacionSaltar(int xInicial, int yInicial, int width, int height, int numeroFrames){
			Array<TextureRegion> frames = new Array<TextureRegion>();
			for(int i = 0; i<numeroFrames; i++){
						frames.add(new TextureRegion(getTexture(), xInicial + i* width, yInicial, width, height));
			
			}
			animationSaltar = new Animation(0.2f, frames);
			frames.clear();
			 

		}
		
	    public void cambiarArmaPorDefecto (){ 
			armaActual = armaDefecto;
			
		
		}
		
		public void recogerArma(Arma armaNueva) { 
			if(this.armaActual.getNombreArma().equals(armaNueva.getNombreArma())){
				armaActual.setBala(armaNueva.getBalasActuales()); 
			
			}else{
				this.armaActual  = armaNueva;
				
			}				
		}
		
		public void cambiarEstadoSalta(boolean estado){
		
			this.saltando	=	estado;
			if(!this.saltando) setRegion(animationQuieto.getKeyFrame(tiempoEstado, true)); 
		}
		
		public void setLlegoFinal(boolean estado){
			this.llegoFinal = estado;
			
		}
		
		public void update(float dt){
			
			if(!puedeDisparar){ 
				tiempoDisparo+=dt;  
				
				if(tiempoDisparo>=.5f){ 
					puedeDisparar= true; 
					tiempoDisparo = 0;
				}	
			}
			
			
			setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);   
			setRegion(getFrames(dt));
		}
		
		@Override
		protected TextureRegion getFrames(float dt){ 
			TextureRegion region; 
			
			switch(super.estado){
			case CORRER: 
				region	=	animationCorrer.getKeyFrame(tiempoEstado, true); 
					
				break;			
			case SALTAR: 
				region	=   animationSaltar.getKeyFrame(tiempoEstado, false); 
				
				break;	
			default: 
				region	=   animationQuieto.getKeyFrame(tiempoEstado, true);
					
				break;
			}
	
			if(( !miraDerecha && !region.isFlipX()) ||  (miraDerecha && region.isFlipX())){ 
					region.flip(true, false); 
				}
			
			tiempoEstado = estado == estadoAnterior ? tiempoEstado + dt : 0; 
			estadoAnterior = estado;
			return region; 
	}		
		
		public void consumirItem(Item item){ 
			if(vidaActual + item.getCantidadCuracion() < vidaMaxima){
				vidaActual += item.getCantidadCuracion();
				
			}else{
				vidaActual = vidaMaxima;
			}
		}
		
		public void setArma(Arma armaActual){
			this.armaActual = armaActual;
		}
		
		public boolean llegoFinal() {return llegoFinal;}
		public boolean estaSaltando() {return saltando;}

	

}
